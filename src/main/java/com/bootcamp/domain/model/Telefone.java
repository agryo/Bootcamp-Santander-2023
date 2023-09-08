package com.bootcamp.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "tb_telefone")
public class Telefone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long numero;

    public Long getId() { return this.id; }

    public void setId(Long id) { this.id = id; }

    public Long getNumero() { return this.numero; }

    public void setNumero(Long numero) { this.numero = numero; }
}
