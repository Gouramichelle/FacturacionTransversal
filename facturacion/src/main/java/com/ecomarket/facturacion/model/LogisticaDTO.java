package com.ecomarket.facturacion.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogisticaDTO {

    @JsonProperty("idLogistica")
    private Integer idLogistica;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty ("idFactura")
    private Integer idFactura;

    @JsonProperty ("estado")
    private String estado;


}

