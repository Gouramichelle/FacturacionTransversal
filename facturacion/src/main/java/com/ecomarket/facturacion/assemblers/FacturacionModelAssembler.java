package com.ecomarket.facturacion.assemblers;

import com.ecomarket.facturacion.controller.FacturacionController;
import com.ecomarket.facturacion.model.FacturacionEntity;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FacturacionModelAssembler implements RepresentationModelAssembler<FacturacionEntity, EntityModel<FacturacionEntity>> {
    @Override
    public EntityModel<FacturacionEntity> toModel(FacturacionEntity entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(FacturacionController.class).getFacturaById(entity.getIdFacturacion())).withSelfRel(),
                linkTo(methodOn(FacturacionController.class).getAllFacturas()).withRel("Ventas"));
    }
}

