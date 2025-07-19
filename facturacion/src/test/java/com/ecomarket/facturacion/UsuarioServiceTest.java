package com.ecomarket.facturacion;

import com.ecomarket.facturacion.model.UsuarioDTO;
import com.ecomarket.facturacion.service.UsuarioService;
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
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @MockitoBean
    private RestTemplate restTemplate;

    private UsuarioDTO usuarioMock;

    @BeforeEach
    void setUp() {
        usuarioMock = new UsuarioDTO();
        usuarioMock.setId(1);
        usuarioMock.setNombreCompleto("Camila Soto");
        usuarioMock.setCorreo("camila@example.com");
        usuarioMock.setTelefono("987654321");
    }

    @Test
    void obtenerUsuarioId_ok() {
        String urlEsperada = "http://localhost:8086/api/v1/usuarios/1"; // Reemplaza por la URL correcta de pruebas
        when(restTemplate.getForObject(urlEsperada, UsuarioDTO.class))
                .thenReturn(usuarioMock);

        UsuarioDTO resultado = usuarioService.obtenerUsuarioId(1);

        assertNotNull(resultado);
        assertEquals("Camila Soto", resultado.getNombreCompleto());
        assertEquals("camila@example.com", resultado.getCorreo());
    }

    @Test
    void obtenerUsuarioId_noExiste() {
        String urlEsperada = "http://localhost:8080/api/v1/usuarios/999";
        when(restTemplate.getForObject(urlEsperada, UsuarioDTO.class))
                .thenReturn(null);

        UsuarioDTO resultado = usuarioService.obtenerUsuarioId(999);

        assertNull(resultado);
    }
}