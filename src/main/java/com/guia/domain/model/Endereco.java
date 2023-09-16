package com.guia.domain.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "tb_endereco")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Endereco implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Coordenadas coordenadas;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco = (Endereco) o;
        return Objects.equals(logradouro, endereco.logradouro) &&
               Objects.equals(numero, endereco.numero) &&
               Objects.equals(bairro, endereco.bairro) &&
               Objects.equals(cidade, endereco.cidade) &&
               Objects.equals(uf, endereco.uf) &&
               Objects.equals(cep, endereco.cep) &&
               Objects.equals(coordenadas, endereco.coordenadas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logradouro, numero, bairro, cidade, uf, cep, coordenadas);
    }
}
