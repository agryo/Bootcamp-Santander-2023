package com.bootcamp.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "tb_coordenadas")
public class Coordenadas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double latitude;
    private Double longitude;

    public Long getId() { return this.id; }

    public void setId(Long id) { this.id = id; }

    public Double getLatitude() { return this.latitude; }

    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return this.longitude; }

    public void setLongitude(Double longitude) { this.longitude = longitude; }
}
