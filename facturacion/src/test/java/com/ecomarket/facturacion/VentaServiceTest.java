package com.ecomarket.facturacion;

import com.ecomarket.facturacion.model.VentaDTO;
import com.ecomarket.facturacion.service.VentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
public class VentaServiceTest {

    @Autowired
    private VentaService ventaService;

    @MockitoBean
    private RestTemplate restTemplate;

    private VentaDTO ventaMock;

    @BeforeEach
    void setUp() {
        ventaMock = new VentaDTO();
        ventaMock.setId(1);
        ventaMock.setId(10);
        ventaMock.setTotalVenta(10000.0);
    }

    @Test
    void obtenerVentaPorId_ok() {
        String urlEsperada = "http://localhost:8082/api/v1/ventas/id/1"; // Asegúrate que coincida con application-test
        when(restTemplate.getForObject(urlEsperada, VentaDTO.class)).thenReturn(ventaMock);

        VentaDTO resultado = ventaService.obtenerVentaoPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals(10000.0, resultado.getTotalVenta());
    }

    @Test
    void obtenerVentaPorId_noExiste() {
        String urlEsperada = "http://localhost:8080/api/v1/ventas/id/999";
        when(restTemplate.getForObject(urlEsperada, VentaDTO.class)).thenReturn(null);

        VentaDTO resultado = ventaService.obtenerVentaoPorId(999);

        assertNull(resultado);
    }
}
