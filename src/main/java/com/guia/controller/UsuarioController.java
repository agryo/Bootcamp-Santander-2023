package com.guia.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guia.domain.model.Usuario;
import com.guia.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
@Tag(name = "Operações de Usuário")
public class UsuarioController {
    @Autowired
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(summary = "Salvar um usuário")
    @PostMapping
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuarioParaSalvar) {
        var usuarioSalvo = usuarioService.salvarUsuario(usuarioParaSalvar);
        URI localizacao = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(usuarioSalvo.getId())
                .toUri();
        return ResponseEntity.created(localizacao).body(usuarioSalvo);
    }

    @Operation(summary = "Lista todos os usuários")
    @GetMapping
    public ResponseEntity<List<Usuario>> listaUsuarios() {
        var listaUsuarios = usuarioService.listaUsuarios();
        return ResponseEntity.ok(listaUsuarios);
    }

    @Operation(summary = "Buscar usuário por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscaUsuarioPorId(@PathVariable("id") Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Remover um usuário por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuário removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerUsuario(@PathVariable("id") Long id) {
        if (usuarioService.buscarPorId(id) != null) {
            usuarioService.apagarUsuarioPorId(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
