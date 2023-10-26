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

    @Transactional(readOnly = true)
    public List<Negocio> buscarNegocioPorNome(String nome) {
        List<Negocio> negocios = negocioRepository.findByNome(nome);
        if (negocios.isEmpty()) {
            throw new NotFoundException("Negócio com o Nome: " + nome + " não encontrado!");
        }
        return negocios;
    }

    @Transactional(readOnly = true)
    public List<Negocio> listarPrimeirosNegocios(int quantidade) {
        List<Negocio> negocios = negocioRepository.findAll();
        if (negocios.size() <= quantidade) {
            return negocios; // Retorna a lista completa se for menor ou igual à quantidade desejada.
        }
        return negocios.subList(0, quantidade); // Retorna os primeiros 'quantidade' negócios.
    }
}