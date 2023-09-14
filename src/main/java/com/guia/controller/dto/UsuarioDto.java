package com.guia.controller.dto;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

import com.guia.domain.model.Usuario;

public record UsuarioDto(
        Long id,
        String nome,
        String cpf,
        String email,
        List<EnderecoDto> enderecos,
        List<TelefoneDto> telefones,
        List<NegocioDto> negocios) {
    public UsuarioDto(Usuario modelo) {
        this(
                modelo.getId(),
                modelo.getNome(),
                modelo.getCpf(),
                modelo.getEmail(),
                ofNullable(modelo.getEnderecos()).orElse(emptyList()).stream().map(EnderecoDto::new).collect(toList()),
                ofNullable(modelo.getTelefones()).orElse(emptyList()).stream().map(TelefoneDto::new).collect(toList()),
                ofNullable(modelo.getNegocios()).orElse(emptyList()).stream().map(NegocioDto::new).collect(toList()));
    }

    public Usuario toModel() {
        Usuario modelo = new Usuario();
        modelo.setId(this.id);
        modelo.setNome(this.nome);
        modelo.setCpf(this.cpf);
        modelo.setEmail(this.email);
        modelo.setEnderecos(
                ofNullable(this.enderecos).orElse(emptyList()).stream().map(EnderecoDto::toModel).collect(toList()));
        modelo.setTelefones(
                ofNullable(this.telefones).orElse(emptyList()).stream().map(TelefoneDto::toModel).collect(toList()));
        modelo.setNegocios(
                ofNullable(this.negocios).orElse(emptyList()).stream().map(NegocioDto::toModel).collect(toList()));
        return modelo;
    }
}