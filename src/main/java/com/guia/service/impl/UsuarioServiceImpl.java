package com.guia.service.impl;

import java.util.List;
import static java.util.Optional.ofNullable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import com.guia.domain.model.Negocio;
import com.guia.domain.model.Usuario;
import com.guia.domain.repository.UsuarioRepository;
//import com.guia.service.NegocioService;
import com.guia.service.UsuarioService;
import com.guia.service.exception.BusinessException;
import com.guia.service.exception.NotFoundException;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    private static final Long UNCHANGEABLE_USER_ID = 1L;

    private final UsuarioRepository usuarioRepository;
    //private final NegocioService negocioService;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository/*NegocioService negocioService*/) {
         this.usuarioRepository = usuarioRepository;
         //this.negocioService = negocioService;
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
        ofNullable(usuario.getCpf()).orElseThrow(() -> new BusinessException("O CPF do usuário não pode ser em branco."));
        ofNullable(usuario.getEmail()).orElseThrow(() -> new BusinessException("O E-mail do usuário não pode ser em branco."));

        this.validateChangeableId(usuario.getId(), "Criado");
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
        this.validateChangeableId(id, "Deletado");
        Usuario dbUsuario = this.buscarPorId(id);
        this.usuarioRepository.delete(dbUsuario);
    }

/*
    @Transactional
    public void associarNegocioAoUsuario(Long usuarioId, Negocio negocio) {
        Usuario usuario = buscarPorId(usuarioId);
        // Certifique-se de que o usuário foi encontrado
        if (usuario != null) {
            // Associe o negócio ao usuário
            negocio.setUsuario(usuario);
            // Salve o negócio para persistir a associação
            this.negocioService.salvarNegocio(negocio);
        } else {
            // Trate o caso em que o usuário não foi encontrado
            throw new NotFoundException();
        }
    }
*/
    private void validateChangeableId(Long id, String operation) {
        if (UNCHANGEABLE_USER_ID.equals(id)) {
            throw new BusinessException("Usuário com ID %d não pode ser %s.".formatted(UNCHANGEABLE_USER_ID, operation));
        }
    }
}
