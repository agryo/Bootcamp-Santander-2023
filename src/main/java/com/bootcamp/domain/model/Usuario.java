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

@Entity(name = "tb_usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private Long cpf;

    @Column(unique = true)
    private String email;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Telefone> telefones;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Endereco> endereco;

    public Long getId() { return this.id; }

    public void setId(Long id) { this.id = id; }

    public String getNome() { return this.nome; }

    public void setNome(String nome) { this.nome = nome; }

    public Long getCpf() { return this.cpf; }

    public void setCpf(Long cpf) { this.cpf = cpf; }

    public String getEmail() { return this.email; }

    public void setEmail(String email) { this.email = email; }

    public List<Telefone> getTelefones() { return this.telefones; }

    public void setTelefones(List<Telefone> telefones) { this.telefones = telefones; }

    public List<Endereco> getEndereco() { return this.endereco; }

    public void setEndereco(List<Endereco> endereco) { this.endereco = endereco; }
}
