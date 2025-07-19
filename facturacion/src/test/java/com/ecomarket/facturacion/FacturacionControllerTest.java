package com.ecomarket.facturacion;

import com.ecomarket.facturacion.assemblers.FacturacionModelAssembler;
import com.ecomarket.facturacion.controller.FacturacionController;
import com.ecomarket.facturacion.exception.ResourceNotFoundException;
import com.ecomarket.facturacion.model.FacturacionEntity;
import com.ecomarket.facturacion.service.FacturacionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacturacionController.class)
public class FacturacionControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @MockitoBean
    FacturacionService facturacionService;

    @MockitoBean
    FacturacionModelAssembler assembler;

    private FacturacionEntity facturacion;

    @BeforeEach
    void setUp() {
        facturacion = new FacturacionEntity();
        facturacion.setIdFacturacion(1);
        facturacion.setIdUsuario(1);
        facturacion.setIdVenta(1);
        facturacion.setFechaEmision(LocalDate.of(2025, 7, 1));
        facturacion.setTipoDocumento("Boleta");
    }

    @Test
    void shouldReturnAllFacturas_HAL() throws Exception {
        when(facturacionService.obtenerAllFacturacion()).thenReturn(List.of(facturacion));
        when(assembler.toModel(any())).thenReturn(EntityModel.of(facturacion));

        mvc.perform(get("/facturacion").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$._embedded.facturacionEntityList[0].idFacturacion").value(1));
    }

    @Test
    void shouldReturnFacturaById() throws Exception {
        when(facturacionService.obtenerFacturacionById(1)).thenReturn(facturacion);
        when(assembler.toModel(facturacion)).thenReturn(EntityModel.of(facturacion));

        mvc.perform(get("/facturacion/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idFacturacion").value(1));
    }

    @Test
    void shouldReturn404IfFacturaNotFound() throws Exception {
        when(facturacionService.obtenerFacturacionById(99)).thenThrow(new ResourceNotFoundException("No existe"));

        mvc.perform(get("/facturacion/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateFactura() throws Exception {
        when(facturacionService.crearFacturacion(any())).thenReturn(facturacion);
        when(assembler.toModel(facturacion)).thenReturn(EntityModel.of(facturacion));

        mvc.perform(post("/facturacion")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(facturacion)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/facturacion/1"));
    }

    @Test
    void shouldUpdateFactura() throws Exception {
        when(facturacionService.updateFacturacion(eq(1), any())).thenReturn(facturacion);
        when(assembler.toModel(facturacion)).thenReturn(EntityModel.of(facturacion));

        mvc.perform(put("/facturacion/1")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(facturacion)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idFacturacion").value(1));
    }

    @Test
    void shouldDeleteFactura() throws Exception {
        doNothing().when(facturacionService).deleteFacturacion(1);

        mvc.perform(delete("/facturacion/1"))
                .andExpect(status().isNoContent());

        verify(facturacionService).deleteFacturacion(1);
    }
}
