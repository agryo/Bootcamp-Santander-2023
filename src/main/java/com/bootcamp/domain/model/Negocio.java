package com.bootcamp.domain.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity(name = "tb_negocio")
public class Negocio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;

    private String descricao;
    
    private Endereco endereco;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Telefone> telefones;

    public Long getId() { return this.id; }

    public void setId(Long id) { this.id = id; }

    public String getNome() { return this.nome; }

    public void setNome(String nomeNegocio) { this.nome = nomeNegocio; }

    public String getDescricao() { return this.descricao; }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Endereco getEndereco() { return this.endereco; }

    public void setEndereco(Endereco endereco) { this.endereco = endereco; }

    public List<Telefone> getTelefones() { return this.telefones; }

    public void setTelefones(List<Telefone> telefones) { this.telefones = telefones; }
}
