package com.ecomarket.facturacion.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="facturacion")
public class FacturacionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_facturacion")
    private Integer idFacturacion;
    @Column(name = "tipo_documento")
    private String tipoDocumento;
    @Column(name= "id_venta")
    private Integer idVenta;
    @Column(name = "id_usuario")
    @JsonProperty("id")
    private Integer idUsuario;
    @Column(name="fecha_emision")
    private LocalDate fechaEmision;
}
