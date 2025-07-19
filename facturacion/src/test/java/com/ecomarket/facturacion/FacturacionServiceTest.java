package com.ecomarket.facturacion;

import com.ecomarket.facturacion.exception.ResourceNotFoundException;
import com.ecomarket.facturacion.model.FacturacionEntity;
import com.ecomarket.facturacion.model.UsuarioDTO;
import com.ecomarket.facturacion.model.VentaDTO;
import com.ecomarket.facturacion.repository.FacturacionRespository;
import com.ecomarket.facturacion.service.FacturacionService;
import com.ecomarket.facturacion.service.UsuarioService;
import com.ecomarket.facturacion.service.VentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
public class FacturacionServiceTest {

    @Autowired
    private FacturacionService facturacionService;

    @MockitoBean
    private FacturacionRespository facturacionRespository;

    @MockitoBean
    private UsuarioService usuarioService;

    @MockitoBean
    private VentaService ventaService;

    private FacturacionEntity facturacion;

    @BeforeEach
    void setUp() {
        facturacion = new FacturacionEntity();
        facturacion.setIdFacturacion(1);
        facturacion.setIdUsuario(1);
        facturacion.setIdVenta(1);
        facturacion.setFechaEmision(LocalDate.now());
        facturacion.setTipoDocumento("Boleta");
    }

    @Test
    void obtenerAllFacturacion_ok() {
        when(facturacionRespository.findAll()).thenReturn(List.of(facturacion));

        List<FacturacionEntity> resultado = facturacionService.obtenerAllFacturacion();

        assertThat(resultado).hasSize(1);
        assertEquals("Boleta", resultado.get(0).getTipoDocumento());
    }

    @Test
    void obtenerFacturacionById_existente() {
        when(facturacionRespository.findById(1)).thenReturn(Optional.of(facturacion));

        FacturacionEntity resultado = facturacionService.obtenerFacturacionById(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdUsuario());
    }

    @Test
    void obtenerFacturacionById_noExiste() {
        when(facturacionRespository.findById(2)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> facturacionService.obtenerFacturacionById(2));
    }

    @Test
    void crearFacturacion_ok() {
        when(ventaService.obtenerVentaoPorId(facturacion.getIdVenta())).thenReturn(new VentaDTO());
        when(usuarioService.obtenerUsuarioId(facturacion.getIdUsuario())).thenReturn(new UsuarioDTO());
        when(facturacionRespository.save(any())).thenReturn(facturacion);

        FacturacionEntity resultado = facturacionService.crearFacturacion(facturacion);

        assertNotNull(resultado);
        assertEquals("Boleta", resultado.getTipoDocumento());
        verify(facturacionRespository).save(facturacion);
    }

    @Test
    void crearFacturacion_ventaNoExiste() {
        when(ventaService.obtenerVentaoPorId(anyInt())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> facturacionService.crearFacturacion(facturacion));
    }

    @Test
    void crearFacturacion_usuarioNoExiste() {
        when(ventaService.obtenerVentaoPorId(anyInt())).thenReturn(new VentaDTO());
        when(usuarioService.obtenerUsuarioId(anyInt())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> facturacionService.crearFacturacion(facturacion));
    }

    @Test
    void updateFacturacion_ok() {
        FacturacionEntity nuevosDatos = new FacturacionEntity();
        nuevosDatos.setIdVenta(2);
        nuevosDatos.setIdUsuario(2);
        nuevosDatos.setFechaEmision(LocalDate.of(2025, 7, 1));
        nuevosDatos.setTipoDocumento("Factura");

        when(facturacionRespository.findById(1)).thenReturn(Optional.of(facturacion));
        when(ventaService.obtenerVentaoPorId(2)).thenReturn(new VentaDTO());
        when(usuarioService.obtenerUsuarioId(2)).thenReturn(new UsuarioDTO());
        when(facturacionRespository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        FacturacionEntity actualizado = facturacionService.updateFacturacion(1, nuevosDatos);

        assertEquals("Factura", actualizado.getTipoDocumento());
        assertEquals(2, actualizado.getIdUsuario());
    }

    @Test
    void updateFacturacion_noExiste() {
        when(facturacionRespository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> facturacionService.updateFacturacion(999, facturacion));
    }

    @Test
    void updateFacturacion_ventaNoExiste() {
        when(facturacionRespository.findById(1)).thenReturn(Optional.of(facturacion));
        when(ventaService.obtenerVentaoPorId(anyInt())).thenReturn(null);

        FacturacionEntity nueva = new FacturacionEntity();
        nueva.setIdVenta(99);
        nueva.setIdUsuario(1);

        assertThrows(ResourceNotFoundException.class,
                () -> facturacionService.updateFacturacion(1, nueva));
    }

    @Test
    void updateFacturacion_usuarioNoExiste() {
        when(facturacionRespository.findById(1)).thenReturn(Optional.of(facturacion));
        when(ventaService.obtenerVentaoPorId(anyInt())).thenReturn(new VentaDTO());
        when(usuarioService.obtenerUsuarioId(anyInt())).thenReturn(null);

        FacturacionEntity nueva = new FacturacionEntity();
        nueva.setIdVenta(1);
        nueva.setIdUsuario(999);

        assertThrows(ResourceNotFoundException.class,
                () -> facturacionService.updateFacturacion(1, nueva));
    }

    @Test
    void deleteFacturacion_ok() {
        when(facturacionRespository.findById(1)).thenReturn(Optional.of(facturacion));
        doNothing().when(facturacionRespository).delete(facturacion);

        facturacionService.deleteFacturacion(1);

        verify(facturacionRespository).delete(facturacion);
    }

    @Test
    void deleteFacturacion_noExiste() {
        when(facturacionRespository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> facturacionService.deleteFacturacion(999));
    }
}
