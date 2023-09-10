package com.guia.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guia.domain.model.Negocio;

public interface NegocioRepository extends JpaRepository<Negocio, Long> {
    
}
