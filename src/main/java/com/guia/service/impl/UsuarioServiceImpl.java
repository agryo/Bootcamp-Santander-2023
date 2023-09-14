package com.guia.service.impl;

import java.util.List;
import static java.util.Optional.ofNullable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guia.domain.model.Negocio;
import com.guia.domain.model.Usuario;
import com.guia.domain.repository.NegocioRepository;
import com.guia.domain.repository.UsuarioRepository;
import com.guia.service.UsuarioService;
import com.guia.service.exception.BusinessException;
import com.guia.service.exception.NotFoundException;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final NegocioRepository negocioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, NegocioRepository negocioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.negocioRepository = negocioRepository;
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {
        return this.usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return this.usuarioRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        ofNullable(usuario).orElseThrow(() -> new BusinessException("O usuário a ser criado não pode em branco."));
        ofNullable(usuario.getCpf())
                .orElseThrow(() -> new BusinessException("O CPF do usuário não pode ser em branco."));
        ofNullable(usuario.getEmail())
                .orElseThrow(() -> new BusinessException("O E-mail do usuário não pode ser em branco."));

        if (usuarioRepository.existsByCpf(usuario.getCpf())) {
            throw new BusinessException("Este número de CPF já existe.");
        }
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new BusinessException("Este e-mail já está em uso por outro usuário.");
        }
        return this.usuarioRepository.save(usuario);
    }

    @Transactional
    public void apagarUsuarioPorId(Long id) {
        Usuario dbUsuario = this.buscarPorId(id);
        this.usuarioRepository.delete(dbUsuario);
    }

    @Transactional
    public Usuario adicionarNegocio(Long usuarioId, Negocio negocio) { 
        // Verifique se o ID do usuário existe
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado."));
        System.out.println(usuario);
        // Verifique se o nome do negócio já existe
        if (negocioRepository.existsByNome(negocio.getNome())) {
            throw new BusinessException("Este nome de negócio já existe.");
        }
        // Adicione o negócio à lista de negocios do usuário
        usuario.getNegocios().add(negocio);
        // Salve o usuário diretamente no serviço
        this.usuarioRepository.save(usuario);
        // Retorne o usuário com o negócio salvo
        return usuario;
    }
}