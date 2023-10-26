package com.guia.service;

import java.util.List;
import java.util.UUID;

import com.guia.domain.model.Negocio;
import com.guia.domain.model.Usuario;

/*
 * Essa Interface é uma boa prática para não mostrar os métodos do sistema.
 * Os métodos estão na implementação do serviço, separado.
 */
public interface UsuarioService {
    List<Usuario> listarUsuarios();
    Usuario buscarUsuarioPorId(UUID id);
    void apagarUsuarioPorId(UUID id);
    Usuario salvarUsuario(Usuario usuario);
    Usuario atualizarUsuario(UUID id, Usuario usuarioAtualizado);
    Negocio adicionarNegocio(UUID usuarioId, Negocio negocio);
    void removerNegocioPorId(UUID usuarioId, Long negocioId);
}