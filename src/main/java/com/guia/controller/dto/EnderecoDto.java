package com.guia.controller.dto;

import com.guia.domain.model.Coordenadas;
import com.guia.domain.model.Endereco;

public record EnderecoDto(
        Long id,
        String logradouro,
        String numero,
        String bairro,
        String cidade,
        String uf,
        String cep,
        Coordenadas coordenadas) {
    public EnderecoDto(Endereco modelo) {
        this(
                modelo.getId(),
                modelo.getLogradouro(),
                modelo.getNumero(),
                modelo.getBairro(),
                modelo.getCidade(),
                modelo.getUf(),
                modelo.getCep(),
                modelo.getCoordenadas()
            );
    }

    public Endereco toModel() {
        Endereco modelo = new Endereco();
        modelo.setId(this.id);
        modelo.setLogradouro(this.logradouro);
        modelo.setNumero(this.numero);
        modelo.setBairro(this.bairro);
        modelo.setCidade(this.cidade);
        modelo.setUf(this.uf);
        modelo.setCep(this.cep);
        modelo.setCoordenadas(this.coordenadas);
        return modelo;
    }
}