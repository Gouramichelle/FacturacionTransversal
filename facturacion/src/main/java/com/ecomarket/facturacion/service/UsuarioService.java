package com.ecomarket.facturacion.service;

import com.ecomarket.facturacion.model.UsuarioDTO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UsuarioService {
    private RestTemplate restTemplate;
    @Value("{services.usuario.url}")
    private String usuarioUrl;
    public UsuarioService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UsuarioDTO obtenerUsuarioId(Integer id) {
    String url = this.usuarioUrl + "/" + id;
        return restTemplate.getForObject(url, UsuarioDTO.class);
    }




}
