package com.guia.service;

import java.util.List;

import com.guia.domain.model.Negocio;
import com.guia.domain.model.Usuario;

/*
 * Essa Interface é uma boa prática para não mostrar os métodos do sistema.
 * Os métodos estão na implementação do serviço, separado.
 */
public interface UsuarioService {
    Usuario salvarUsuario(Usuario usuario);
    List<Usuario> listaUsuarios();
    Usuario buscarPorId(Long id);
    void apagarUsuarioPorId(Long id);
    void associarNegocioAoUsuario(Long usuarioId, Negocio negocio);
}
