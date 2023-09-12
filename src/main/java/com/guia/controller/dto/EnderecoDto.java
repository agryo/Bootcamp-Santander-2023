package com.guia.controller.dto;

import com.guia.domain.model.Coordenadas;
import com.guia.domain.model.Endereco;

public record EnderecoDto(
        Long id,
        String rua,
        String numero,
        String bairro,
        String cidade,
        String estado,
        String cep,
        Coordenadas coordenadas) {
    public EnderecoDto(Endereco modelo) {
        this(
                modelo.getId(),
                modelo.getRua(),
                modelo.getNumero(),
                modelo.getBairro(),
                modelo.getCidade(),
                modelo.getEstado(),
                modelo.getCep(),
                modelo.getCoordenadas()
            );
    }

    public Endereco toModel() {
        Endereco modelo = new Endereco();
        modelo.setId(this.id);
        modelo.setRua(this.rua);
        modelo.setNumero(this.numero);
        modelo.setBairro(this.bairro);
        modelo.setCidade(this.cidade);
        modelo.setEstado(this.estado);
        modelo.setCep(this.cep);
        modelo.setCoordenadas(this.coordenadas);
        return modelo;
    }
}
