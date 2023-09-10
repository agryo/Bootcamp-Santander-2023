package com.guia.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.guia.domain.model.Negocio;
import com.guia.domain.model.Usuario;
import com.guia.domain.repository.UsuarioRepository;
import com.guia.service.NegocioService;
import com.guia.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final NegocioService negocioService;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, NegocioService negocioService) {
         this.usuarioRepository = usuarioRepository;
         this.negocioService = negocioService;
    }

    public Usuario salvarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listaUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public void apagarUsuarioPorId(Long id) {
        usuarioRepository.deleteById(id);
    }

    public void associarNegocioAoUsuario(Long usuarioId, Negocio negocio) {
        Usuario usuario = buscarPorId(usuarioId);
        // Certifique-se de que o usuário foi encontrado
        if (usuario != null) {
            // Associe o negócio ao usuário
            negocio.setUsuario(usuario);
            // Salve o negócio para persistir a associação
            negocioService.salvarNegocio(negocio);
        } else {
            // Trate o caso em que o usuário não foi encontrado
            throw new NoSuchElementException("Usuário não encontrado");
        }
    }
}
