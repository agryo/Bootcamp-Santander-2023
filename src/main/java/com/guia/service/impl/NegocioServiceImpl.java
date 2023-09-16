package com.guia.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guia.domain.model.Negocio;
import com.guia.domain.repository.NegocioRepository;
import com.guia.service.NegocioService;
import com.guia.service.exception.NotFoundException;

@Service
public class NegocioServiceImpl implements NegocioService {
    @Autowired
    private final NegocioRepository negocioRepository;

    public NegocioServiceImpl(NegocioRepository negocioRepository) {
        this.negocioRepository = negocioRepository;
    }

    @Transactional(readOnly = true)
    public List<Negocio> listarNegocios() {
        return negocioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Negocio buscarNegocioPorId(Long id) {
        return negocioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Negócio com o ID: " + id + ", não encontrado!"));
    }
}