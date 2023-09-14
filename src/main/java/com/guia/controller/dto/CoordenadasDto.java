package com.guia.controller.dto;

import com.guia.domain.model.Coordenadas;

public record CoordenadasDto(Long id, Double latitude, Double longitude) {
    public CoordenadasDto(Coordenadas model) {
        this(model.getId(), model.getLatitude(), model.getLongitude());
    }

    public Coordenadas toModel() {
        Coordenadas modelo = new Coordenadas();
        modelo.setId(this.id);
        modelo.setLatitude(this.latitude);
        modelo.setLongitude(this.longitude);
        return modelo;
    }
}