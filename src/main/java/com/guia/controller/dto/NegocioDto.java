package com.guia.controller.dto;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

import com.guia.domain.model.Negocio;

public record NegocioDto(
        Long id,
        String nome,
        String logomarca,
        String descricao,
        EnderecoDto endereco,
        List<TelefoneDto> telefones) {
    public NegocioDto(Negocio modelo) {
        this(
                modelo.getId(),
                modelo.getNome(),
                modelo.getLogomarca(),
                modelo.getDescricao(),
                ofNullable(modelo.getEndereco()).map(EnderecoDto::new).orElse(null),
                ofNullable(modelo.getTelefones()).orElse(emptyList()).stream().map(TelefoneDto::new).collect(toList())
            );
    }

    public Negocio toModel() {
        Negocio modelo = new Negocio();
        modelo.setId(this.id);
        modelo.setNome(this.nome);
        modelo.setLogomarca(this.logomarca);
        modelo.setDescricao(this.descricao);
        modelo.setEndereco(ofNullable(this.endereco).map(EnderecoDto::toModel).orElse(null));
        modelo.setTelefones(ofNullable(this.telefones).orElse(emptyList()).stream().map(TelefoneDto::toModel).collect(toList()));
        return modelo;
    }
}