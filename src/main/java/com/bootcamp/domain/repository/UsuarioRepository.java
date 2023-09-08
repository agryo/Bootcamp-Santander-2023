package com.bootcamp.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bootcamp.domain.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
}
