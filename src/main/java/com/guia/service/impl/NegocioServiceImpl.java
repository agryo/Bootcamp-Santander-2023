package com.guia.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guia.domain.model.Negocio;
import com.guia.domain.repository.NegocioRepository;
import com.guia.service.NegocioService;

@Service
public class NegocioServiceImpl implements NegocioService {
    @Autowired
    private final NegocioRepository negocioRepository;

    public NegocioServiceImpl(NegocioRepository negocioRepository) {
        this.negocioRepository = negocioRepository;
    }

    public Negocio salvarNegocio(Negocio negocio) {
        return negocioRepository.save(negocio);
    }

    public List<Negocio> listaNegocios() {
        return negocioRepository.findAll();
    }

    public Negocio buscarPorId(Long id) {
        return negocioRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public void apagarNegocioPorId(Long id) {
        negocioRepository.deleteById(id);
    }
}
