package com.guia.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guia.domain.model.Negocio;
import com.guia.domain.model.Usuario;
import com.guia.domain.repository.NegocioRepository;
import com.guia.service.NegocioService;
import com.guia.service.UsuarioService;

@Service
public class NegocioServiceImpl implements NegocioService {
    @Autowired
    private final NegocioRepository negocioRepository;

    @Autowired
    private UsuarioService usuarioService;

    public NegocioServiceImpl(NegocioRepository negocioRepository) {
        this.negocioRepository = negocioRepository;
    }

    public Negocio salvarNegocio(Negocio negocio) {
        return negocioRepository.save(negocio);
    }

    public List<Negocio> listaNegocios() {
        return negocioRepository.findAll();
    }

    public Optional<Negocio> buscarPorId(Long id) {
        return negocioRepository.findById(id);
    }

    public void apagarNegocioPorId(Long id) {
        negocioRepository.deleteById(id);
    }

    public Negocio pegarIdUsuario(Long negocioId, Long usuarioId) {
        Optional<Negocio> negocioOptional = negocioRepository.findById(negocioId);
        Optional<Usuario> usuarioOptional = usuarioService.buscarPorId(usuarioId);

        if (negocioOptional.isPresent() && usuarioOptional.isPresent()) {
            Negocio negocio = negocioOptional.get();
            Usuario usuario = usuarioOptional.get();

            negocio.setUsuario(usuario); // Associe o usuário ao negócio
            return negocioRepository.save(negocio);
        } else {
            return null; //Vou fazer depois
        }
    }
}
