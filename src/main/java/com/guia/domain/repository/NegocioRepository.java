package com.guia.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.guia.domain.model.Negocio;
import com.guia.domain.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NegocioRepository extends JpaRepository<Negocio, Long> {
    boolean existsByNome(String nome);
    boolean existsByTelefonesNumero(String numero);
    boolean existsByTelefonesNumeroAndUsuarioNot(String numero, Usuario usuario);
    @Query("SELECT n FROM tb_negocio n WHERE LOWER(n.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Negocio> findByNome(@Param("nome") String nome);
}