package com.ecomarket.facturacion.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class VentaDTO {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("nombreCliente")
    private String nombreCliente;
    @JsonProperty("fechaVenta")
    private LocalDateTime fechaVenta;
    @JsonProperty("totalVenta")
    private Double totalVenta;
    @JsonProperty("estadoVenta")
    private String estadoVenta;
    @JsonProperty("cantidadVenta")
    private Integer cantidadVenta;
    @JsonProperty("tipoEnvio")
    private String tipoEnvio;
    @JsonProperty("descripcionVenta")
    private String descripcionVenta;
    @JsonProperty("idProducto")
    private Integer idProducto;
}
