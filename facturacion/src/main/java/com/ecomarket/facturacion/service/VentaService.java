package com.ecomarket.facturacion.service;

import com.ecomarket.facturacion.model.VentaDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class VentaService {
    private RestTemplate restTemplate;
    @Value("{services.venta.url}")
    private String ventaUrl;
    public VentaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public VentaDTO obtenerVentaoPorId(Integer idVenta) {
        String url = ventaUrl + "/id/" + idVenta;
        return restTemplate.getForObject(url, VentaDTO.class);
    }

}
