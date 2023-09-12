package com.guia.service;

import java.util.List;

import com.guia.domain.model.Negocio;

/*
 * Essa Interface é uma boa prática para não mostrar os métodos do sistema.
 * Os métodos estão na implementação do serviço, separado.
 */
public interface NegocioService {
    Negocio salvarNegocio(Negocio negocio);
    List<Negocio> listarNegocios();
    Negocio buscarPorId(Long id);
    void apagarNegocioPorId(Long id);
}
