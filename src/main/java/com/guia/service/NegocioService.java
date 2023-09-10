package com.guia.service;

import java.util.List;
import java.util.Optional;

import com.guia.domain.model.Negocio;

public interface NegocioService {
    Negocio salvarNegocio(Negocio negocio);
    List<Negocio> listaNegocios();
    Optional<Negocio> buscarPorId(Long id);
    void apagarNegocioPorId(Long id);
}
