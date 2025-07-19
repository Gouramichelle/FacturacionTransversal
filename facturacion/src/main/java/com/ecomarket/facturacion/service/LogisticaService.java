package com.ecomarket.facturacion.service;

import com.ecomarket.facturacion.model.LogisticaDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Service
public class LogisticaService {

    private final RestTemplate restTemplate;

    @Value("${services.logistica.url}")
    public String logisticaUrl;

    public LogisticaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public LogisticaDTO crearLogistica(LogisticaDTO logisticaDTO) {
        String url = logisticaUrl;
        ResponseEntity<LogisticaDTO> response = restTemplate.postForEntity(url, logisticaDTO, LogisticaDTO.class);
        return response.getBody();
    }
}
