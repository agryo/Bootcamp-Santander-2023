package com.guia.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guia.domain.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    boolean existsByTelefonesNumero(String numero);
}