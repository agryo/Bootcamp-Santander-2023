package com.guia.service;

import java.util.List;
import java.util.Optional;

import com.guia.domain.model.Negocio;

/*
 * Essa Interface é uma boa prática para não mostrar os métodos do sistema.
 * Os métodos estão na implementação do serviço, separado.
 */
public interface NegocioService {
    List<Negocio> listarNegocios();
    Optional<List<Negocio>> buscarNegocioPorNome(String nome);
    Negocio buscarNegocioPorId(Long id);
    List<Negocio> listarPrimeirosNegocios(int quantidade);

}