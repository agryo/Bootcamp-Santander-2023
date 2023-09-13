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

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guia.domain.model.Negocio;
import com.guia.domain.model.Usuario;
import com.guia.domain.repository.NegocioRepository;
import com.guia.service.UsuarioService;
import com.guia.service.exception.BusinessException;

@CrossOrigin
@RestController
@RequestMapping("/usuario")
@Tag(name = "Operações de Usuário", description = "API RESTful para gerenciamento de usuários.")
public class UsuarioController {
    @Autowired
    private final UsuarioService usuarioService;

    @Autowired
    private final NegocioRepository negocioRepository; // Injete o NegocioRepository aqui

    public UsuarioController(UsuarioService usuarioService, NegocioRepository negocioRepository) {
        this.usuarioService = usuarioService;
        this.negocioRepository = negocioRepository; // Atribua o NegocioRepository injetado ao campo local
    }

    @GetMapping
    @Operation(summary = "Lista todos os usuários", description = "Exibe uma lista com todos usuários cadastrados no banco de dados. Ou exibe uma lista vazia, caso não tenha nenhum usuário cadastrado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista exibida com sucesso!")
    })
    public ResponseEntity<List<Usuario>> listaUsuarios() {
        var listaUsuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(listaUsuarios);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Exibe o usuário cadastrado no banco de dados com a ID solicitada. Caso não exista, diz que o úsuario não existe.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Usuario> buscaUsuarioPorId(@PathVariable("id") Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    @Operation(summary = "Salvar um usuário", description = "Salva o usuário preenchido no formulário JSON, caso todos os campos estejam corretos, ele salva o usuário no banco de dados e exibe o usuário criado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário salvo com sucesso!"),
            @ApiResponse(responseCode = "422", description = "CPF ou E-mail já cadastrado!"),
            @ApiResponse(responseCode = "500", description = "Telefone já cadastrado!")
    })
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuarioParaSalvar) {
        var usuarioSalvo = usuarioService.salvarUsuario(usuarioParaSalvar);
        URI localizacao = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(usuarioSalvo.getId())
                .toUri();
        return ResponseEntity.created(localizacao).body(usuarioSalvo);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover um usuário por ID", description = "Apaga um usuário do banco de dados de acordo com o ID solicitado. Caso não exista, informa que o usuário não existe.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Void> removerUsuario(@PathVariable("id") Long id) {
        if (usuarioService.buscarPorId(id) != null) {
            usuarioService.apagarUsuarioPorId(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{usuarioId}/negocio")
    @Operation(summary = "Associar um negócio a um usuário", description = "Associa um negócio a um usuário com base nos IDs fornecidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Negócio associado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Solicitação inválida"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado ou Negócio não encontrado"),
            @ApiResponse(responseCode = "422", description = "Associação já existe")
    })
    public ResponseEntity<Usuario> associarNegocioAoUsuario(
            @PathVariable("usuarioId") Long usuarioId,
            @RequestBody Negocio negocio) {
        // Verifique se o ID do usuário existe
        Usuario usuario = usuarioService.buscarPorId(usuarioId);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        // Verifique se o nome do negócio já existe
        if (negocioRepository.existsByNome(negocio.getNome())) {
            throw new BusinessException("Este nome de negócio já existe.");
        }
        // Associe o negócio ao usuário
        usuario.getNegocios().add(negocio);
        // Salve o usuário para atualizar a associação
        usuarioService.salvarUsuario(usuario);

        return ResponseEntity.created(null).body(usuario);
    }

}
