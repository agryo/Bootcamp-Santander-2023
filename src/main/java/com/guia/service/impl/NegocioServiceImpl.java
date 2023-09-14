package com.guia.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guia.domain.model.Negocio;
import com.guia.domain.model.Usuario;
import com.guia.domain.repository.NegocioRepository;
import com.guia.domain.repository.UsuarioRepository;
import com.guia.service.NegocioService;
import com.guia.service.exception.NotFoundException;

@Service
public class NegocioServiceImpl implements NegocioService {
    @Autowired
    private final NegocioRepository negocioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public NegocioServiceImpl(NegocioRepository negocioRepository) {
        this.negocioRepository = negocioRepository;
    }

    @Transactional(readOnly = true)
    public List<Negocio> listarNegocios() {
        return this.negocioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Negocio buscarNegocioPorId(Long id) {
        return this.negocioRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public void apagarNegocioPorId(Long id) {
        Negocio negocioParaRemover = buscarNegocioPorId(id);
        // Verifique se o negócio possui uma associação com um usuário
        Usuario usuarioDoNegocio = negocioParaRemover.getUsuario();
        if (usuarioDoNegocio != null) {
            // Remova o negócio da lista de negócios do usuário
            usuarioDoNegocio.getNegocios().remove(negocioParaRemover);
            // Limpe a referência do usuário no negócio
            negocioParaRemover.setUsuario(null);
            // Salve o usuário para atualizar a associação
            usuarioRepository.save(usuarioDoNegocio);
        }
        // Limpe as associações em cascata com telefone, endereço e coordenadas
        negocioParaRemover.setEndereco(null);
        negocioParaRemover.getEndereco().setCoordenadas(null);
        if (negocioParaRemover.getTelefones() != null) {
            negocioParaRemover.getTelefones().clear();
        }
        // Agora, exclua o negócio
        this.negocioRepository.delete(negocioParaRemover);
    }
}