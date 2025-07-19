package com.ecomarket.facturacion.service;

import com.ecomarket.facturacion.exception.ResourceNotFoundException;
import com.ecomarket.facturacion.model.FacturacionEntity;
import com.ecomarket.facturacion.model.LogisticaDTO;
import com.ecomarket.facturacion.model.UsuarioDTO;
import com.ecomarket.facturacion.model.VentaDTO;
import com.ecomarket.facturacion.repository.FacturacionRespository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

@Service
public class FacturacionService {
    @Autowired
    private FacturacionRespository facturacionRespository;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private VentaService ventaService;
    @Autowired
    private LogisticaService logisticaService;


    public List<FacturacionEntity> obtenerAllFacturacion(){
        return facturacionRespository.findAll();
    }


    public FacturacionEntity obtenerFacturacionById(Integer id){
        return facturacionRespository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro factura con el ID: "+id));
    }


    @Transactional
    public FacturacionEntity crearFacturacion(FacturacionEntity facturacion) {
        VentaDTO ventadto = ventaService.obtenerVentaoPorId(facturacion.getIdVenta());
        if (ventadto == null) {
            throw new ResourceNotFoundException("No existe esta venta");
        }
        UsuarioDTO usuarioDTO = usuarioService.obtenerUsuarioId(facturacion.getIdUsuario());
        if (usuarioDTO == null) {
            throw new ResourceNotFoundException("No existe esta usuario");
        }
        facturacionRespository.save(facturacion);
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                try {
                    LogisticaDTO logisticaDTO = new LogisticaDTO();
                    logisticaDTO.setId(facturacion.getIdUsuario());
                    logisticaDTO.setIdFactura(facturacion.getIdFacturacion());
                    logisticaDTO.setEstado("En Proceso");

                    logisticaService.crearLogistica(logisticaDTO);
                    System.out.println("✅ Registro en logística creado exitosamente.");
                } catch (Exception e) {
                    System.err.println("❌ Error al crear registro en logística post-commit: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        return facturacion;


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




