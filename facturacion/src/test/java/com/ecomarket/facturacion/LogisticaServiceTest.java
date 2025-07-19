package com.ecomarket.facturacion;


import com.ecomarket.facturacion.model.LogisticaDTO;
import com.ecomarket.facturacion.service.LogisticaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
public class LogisticaServiceTest {

    @Autowired
    private LogisticaService logisticaService;

    @MockitoBean
    private RestTemplate restTemplate;

    private LogisticaDTO mockRequest;
    private LogisticaDTO mockResponse;

    @BeforeEach
    void setUp() {
        // Simular datos
        mockRequest = new LogisticaDTO();
        mockRequest.setId(1);
        mockRequest.setIdFactura(100);
        mockRequest.setEstado("En camino");

        mockResponse = new LogisticaDTO();
        mockResponse.setId(1);
        mockResponse.setIdFactura(100);
        mockResponse.setEstado("En camino");

        // Simular inyección de URL
        logisticaService.logisticaUrl = "http://localhost:8084";
    }

    @Test
    void crearLogistica_debeRetornarRespuestaValida() {
        String urlEsperada = "http://localhost:8084/logistica";

        when(restTemplate.postForEntity(urlEsperada, mockRequest, LogisticaDTO.class))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.CREATED));

        LogisticaDTO resultado = logisticaService.crearLogistica(mockRequest);

        assertNotNull(resultado);
        assertEquals(100, resultado.getIdFactura());
        assertEquals("En camino", resultado.getEstado());
        verify(restTemplate, times(1)).postForEntity(urlEsperada, mockRequest, LogisticaDTO.class);
    }
}