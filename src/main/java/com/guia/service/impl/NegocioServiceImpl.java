package com.guia.service.impl;

import java.util.List;
import static java.util.Optional.ofNullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guia.domain.model.Negocio;
import com.guia.domain.repository.NegocioRepository;
import com.guia.service.NegocioService;
import com.guia.service.exception.BusinessException;
import com.guia.service.exception.NotFoundException;

@Service
public class NegocioServiceImpl implements NegocioService {
    private static final Long UNCHANGEABLE_USER_ID = 1L;

    @Autowired
    private final NegocioRepository negocioRepository;

    public NegocioServiceImpl(NegocioRepository negocioRepository) {
        this.negocioRepository = negocioRepository;
    }

    @Transactional(readOnly = true)
    public List<Negocio> listarNegocios() {
        return this.negocioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Negocio buscarPorId(Long id) {
        return this.negocioRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Negocio salvarNegocio(Negocio negocio) {
        ofNullable(negocio).orElseThrow(() -> new BusinessException("O negócio a ser criado não pode ser em branco."));
        ofNullable(negocio.getNome()).orElseThrow(() -> new BusinessException("O nome do negócio a ser criado não pode ser em branco."));

        this.validateChangeableId(negocio.getId(), "Criado");
        if (negocioRepository.existsByNome(negocio.getNome())) {
            throw new BusinessException("Este nome de negócio já existe.");
        }
        return this.negocioRepository.save(negocio);
    }

    @Transactional
    public void apagarNegocioPorId(Long id) {
        this.validateChangeableId(id, "Deletado");
        Negocio dbNegocio = this.buscarPorId(id);
        this.negocioRepository.delete(dbNegocio);
    }

    private void validateChangeableId(Long id, String operation) {
        if (UNCHANGEABLE_USER_ID.equals(id)) {
            throw new BusinessException("User with ID %d can not be %s.".formatted(UNCHANGEABLE_USER_ID, operation));
        }
    }
}
