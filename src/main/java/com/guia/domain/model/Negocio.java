package com.guia.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "tb_negocio")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Negocio implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;
    
    private String descricao;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "negocio")
    private List<Endereco> enderecos = new ArrayList<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "negocio")
    private List<Telefone> telefones = new ArrayList<>();
}