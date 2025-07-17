package com.ecomarket.facturacion.controller;

import com.ecomarket.facturacion.assemblers.FacturacionModelAssembler;
import com.ecomarket.facturacion.model.FacturacionEntity;
import com.ecomarket.facturacion.service.FacturacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/facturacion")
@Tag(name = "Facturación", description = "APIs del microservicio de Facturación")
public class FacturacionController {

    @Autowired
    private FacturacionService facturacionService;

    @Autowired
    private FacturacionModelAssembler assembler;
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todas las facturas", description = "Devuelve una lista de todas las facturas registradas")
    public CollectionModel<EntityModel<FacturacionEntity>> getAllFacturas() {
        List<EntityModel<FacturacionEntity>> facturas = facturacionService.obtenerAllFacturacion()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(facturas,
                linkTo(methodOn(FacturacionController.class).getAllFacturas()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener una factura por ID", description = "Busca y devuelve una factura por su ID")
    public EntityModel<FacturacionEntity> getFacturaById(@PathVariable Integer id) {
        FacturacionEntity factura = facturacionService.obtenerFacturacionById(id);
        return assembler.toModel(factura);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear una nueva factura", description = "Registra una nueva factura")
    public ResponseEntity<EntityModel<FacturacionEntity>> createFactura(@RequestBody FacturacionEntity facturacion) {
        FacturacionEntity nuevaFactura = facturacionService.crearFacturacion(facturacion);
        return ResponseEntity
                .created(linkTo(methodOn(FacturacionController.class).getFacturaById(nuevaFactura.getIdFacturacion())).toUri())
                .body(assembler.toModel(nuevaFactura));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar una factura", description = "Modifica los datos de una factura existente")
    public ResponseEntity<EntityModel<FacturacionEntity>> updateFactura(
            @PathVariable Integer id, @RequestBody FacturacionEntity facturacion) {
        FacturacionEntity actualizada = facturacionService.updateFacturacion(id, facturacion);
        return ResponseEntity.ok(assembler.toModel(actualizada));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar una factura", description = "Elimina una factura por su ID")
    public ResponseEntity<?> deleteFactura(@PathVariable Integer id) {
        facturacionService.deleteFacturacion(id);
        return ResponseEntity.noContent().build();
    }

}
