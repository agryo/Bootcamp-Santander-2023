package com.bootcamp.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "tb_endereco")
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String uf;
    private int cep;
    private Coordenadas coordenadas;

    public Long getId() { return this.id; }

    public void setId(Long id) { this.id = id; }

    public String getLogradouro() { return this.logradouro; }

    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }

    public String getNumero() { return this.numero; }

    public void setNumero(String numero) { this.numero = numero; }

    public String getBairro() { return this.bairro; }

    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getCidade() { return this.cidade; }

    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getUf() { return this.uf; }

    public void setUf(String uf) { this.uf = uf; }

    public int getCep() { return this.cep; }

    public void setCep(int cep) { this.cep = cep; }

    public Coordenadas getCoordenadas() { return this.coordenadas; }

    public void setCoordenadas(Coordenadas coordenadas) { this.coordenadas = coordenadas; }
}
