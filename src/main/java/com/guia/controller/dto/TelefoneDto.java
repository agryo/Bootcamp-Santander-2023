package com.guia.controller.dto;

import com.guia.domain.model.Telefone;

public record TelefoneDto(Long id, String numero) {
    public TelefoneDto(Telefone model) {
        this(model.getId(), model.getNumero());
    }

    public Telefone toModel() {
        Telefone modelo = new Telefone();
        modelo.setId(this.id);
        modelo.setNumero(this.numero);
        return modelo;
    }
}