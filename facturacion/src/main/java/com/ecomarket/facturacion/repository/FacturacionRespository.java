package com.ecomarket.facturacion.repository;

import com.ecomarket.facturacion.model.FacturacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturacionRespository extends JpaRepository<FacturacionEntity, Integer> {
}
