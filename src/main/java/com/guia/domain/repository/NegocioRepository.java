package com.guia.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guia.domain.model.Negocio;
import com.guia.domain.model.Usuario;

public interface NegocioRepository extends JpaRepository<Negocio, Long> {
    boolean existsByNome(String nome);
    boolean existsByTelefonesNumero(String numero);
    boolean existsByTelefonesNumeroAndUsuarioNot(String numero, Usuario usuario);
}