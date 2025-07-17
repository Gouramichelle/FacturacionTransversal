package com.ecomarket.facturacion.service;

import com.ecomarket.facturacion.exception.ResourceNotFoundException;
import com.ecomarket.facturacion.model.FacturacionEntity;
import com.ecomarket.facturacion.model.UsuarioDTO;
import com.ecomarket.facturacion.model.VentaDTO;
import com.ecomarket.facturacion.repository.FacturacionRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacturacionService {
    @Autowired
    private FacturacionRespository facturacionRespository;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private VentaService ventaService;
    public List<FacturacionEntity> obtenerAllFacturacion(){
        return facturacionRespository.findAll();
    }
    public FacturacionEntity obtenerFacturacionById(Integer id){
        return facturacionRespository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro factura con el ID: "+id));
    }
    public FacturacionEntity updateFacturacion(Integer id, FacturacionEntity nuevaFacturacion) {
        FacturacionEntity facturacionExistente = facturacionRespository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe esta facturación"));

        VentaDTO ventadto = ventaService.obtenerVentaoPorId(nuevaFacturacion.getIdVenta());
        if (ventadto == null) {
            throw new ResourceNotFoundException("No existe esta venta");
        }

        UsuarioDTO usuarioDTO = usuarioService.obtenerUsuarioId(nuevaFacturacion.getIdUsuario());
        if (usuarioDTO == null) {
            throw new ResourceNotFoundException("No existe este usuario");
        }


        facturacionExistente.setIdVenta(nuevaFacturacion.getIdVenta());
        facturacionExistente.setIdUsuario(nuevaFacturacion.getIdUsuario());
        facturacionExistente.setFechaEmision(nuevaFacturacion.getFechaEmision());
        facturacionExistente.setTipoDocumento(nuevaFacturacion.getTipoDocumento());


        return facturacionRespository.save(facturacionExistente);
    }
    public void deleteFacturacion(Integer id) {
        FacturacionEntity facturacion = facturacionRespository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe esta facturación"));

        facturacionRespository.delete(facturacion);
    }


}




