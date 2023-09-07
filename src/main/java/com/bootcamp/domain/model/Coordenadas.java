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

    private Double latitide;
    private Double longitude;


    public Long getId() { return this.id; }

    public void setId(Long id) { this.id = id; }

    public Double getLatitide() { return this.latitide; }

    public void setLatitide(Double latitide) { this.latitide = latitide; }

    public Double getLongitude() { return this.longitude; }

    public void setLongitude(Double longitude) { this.longitude = longitude; }
}
