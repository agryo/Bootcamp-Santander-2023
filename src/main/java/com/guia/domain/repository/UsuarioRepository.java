package com.guia.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.guia.domain.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    boolean existsByTelefonesNumero(String numero);
    // Verifica se existe algum usuário com o mesmo CPF, excluindo o usuário com o ID especificado
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM tb_usuario u WHERE u.cpf = :cpf AND u.id <> :id")
    boolean existsByCpfAndIdNot(@Param("cpf") String cpf, @Param("id") Long id);
    // Verifica se existe algum usuário com o mesmo e-mail, excluindo o usuário com o ID especificado
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM tb_usuario u WHERE u.email = :email AND u.id <> :id")
    boolean existsByEmailAndIdNot(@Param("email") String email, @Param("id") Long id);
    @Query("SELECT COUNT(u) > 0 FROM tb_usuario u JOIN u.telefones t WHERE t.numero = :numero AND u.id != :id")
    boolean existsByTelefoneAndIdNot(@Param("numero") String numero, @Param("id") Long id);
}