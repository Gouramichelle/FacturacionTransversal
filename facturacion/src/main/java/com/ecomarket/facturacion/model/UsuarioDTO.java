package com.ecomarket.facturacion.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UsuarioDTO {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("nombreCompleto")
    private String nombreCompleto;
    @JsonProperty("run")
    private String run;
    @JsonProperty("correo")
    private String correo;
    @JsonProperty("direccion")
    private String direccion;
    @JsonProperty("telefono")
    private String telefono;
    @JsonProperty("giro")
    private String giro;

}
