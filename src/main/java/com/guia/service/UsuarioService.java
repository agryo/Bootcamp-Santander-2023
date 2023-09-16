package com.guia.service;

import java.util.List;

import com.guia.domain.model.Negocio;
import com.guia.domain.model.Usuario;

/*
 * Essa Interface é uma boa prática para não mostrar os métodos do sistema.
 * Os métodos estão na implementação do serviço, separado.
 */
public interface UsuarioService {
    List<Usuario> listarUsuarios();
    Usuario buscarUsuarioPorId(Long id);
    void apagarUsuarioPorId(Long id);
    Usuario salvarUsuario(Usuario usuario);
    Usuario atualizarUsuario(Long id, Usuario usuarioAtualizado);
    Negocio adicionarNegocio(Long usuarioId, Negocio negocio);
    void removerNegocioPorId(Long usuarioId, Long negocioId);
}