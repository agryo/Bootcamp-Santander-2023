package com.guia.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "tb_usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario implements Serializable {
    @Id
    @Column(name = "id")
    private UUID id;

    private String nome;

    @Column(unique = true)
    private String cpf;

    @Column(unique = true)
    private String email;

    @Builder.Default
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "tb_usuario_enderecos",
            joinColumns = @JoinColumn(name = "tb_usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "enderecos_id"))
    private List<Endereco> enderecos = new ArrayList<>();

    @Builder.Default
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "tb_usuario_telefones",
            joinColumns = @JoinColumn(name = "tb_usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "telefones_id"))
    private List<Telefone> telefones = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Negocio> negocios;

    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
    }
}