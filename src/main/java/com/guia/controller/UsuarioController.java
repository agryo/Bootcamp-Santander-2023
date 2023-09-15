package com.guia.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.guia.domain.model.Negocio;
import com.guia.domain.model.Usuario;
import com.guia.service.UsuarioService;

@CrossOrigin
@RestController
@RequestMapping("/usuario")
@Tag(name = "Operações de Usuário", description = "API RESTful para gerenciamento de usuários.")
public class UsuarioController {
        @Autowired
        private final UsuarioService usuarioService;

        public UsuarioController(UsuarioService usuarioService) {
                this.usuarioService = usuarioService;
        }

        @GetMapping
        @Operation(summary = "Lista todos os usuários", description = "Exibe uma lista com todos os usuários cadastrados no banco de dados. Ou exibe uma lista vazia, caso não tenha nenhum usuário cadastrado.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Lista exibida com sucesso!")
        })
        public ResponseEntity<List<Usuario>> listaUsuarios() {
                var listaUsuarios = usuarioService.listarUsuarios();
                return ResponseEntity.ok(listaUsuarios);
        }

        @GetMapping("/{id}")
        @Operation(summary = "Buscar usuário por ID", description = "Exibe o usuário cadastrado no banco de dados com a ID solicitada. Caso não exista, diz que o usuário não existe.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
                        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
        })
        public ResponseEntity<Usuario> buscaUsuarioPorId(@PathVariable("id") Long id) {
                Usuario usuario = usuarioService.buscarUsuarioPorId(id);
                return ResponseEntity.ok(usuario);
        }

        @PostMapping
        @Operation(summary = "Salvar um usuário", description = "Salva o usuário preenchido no formulário JSON, caso todos os campos estejam corretos, ele salva o usuário no banco de dados e exibe o usuário criado.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Usuário salvo com sucesso!"),
                        @ApiResponse(responseCode = "422", description = "CPF, E-mail ou Telefone já cadastrado!")
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
        public ResponseEntity<String> removerUsuario(@PathVariable("id") Long id) {
                usuarioService.apagarUsuarioPorId(id);
                return ResponseEntity.status(HttpStatus.OK).body("Usuário removido com sucesso!");
        }

        @PostMapping("/{usuarioId}/negocios")
        @Operation(summary = "Associar um negócio a um usuário", description = "Associa um negócio a um usuário com base nos IDs fornecidos.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Negócio associado com sucesso!"),
                        @ApiResponse(responseCode = "400", description = "Solicitação inválida"),
                        @ApiResponse(responseCode = "422", description = "Associação já existe")
        })
        public ResponseEntity<Negocio> adicionarNegocio(
                        @PathVariable Long usuarioId,
                        @RequestBody Negocio negocio) {
                Negocio negocioAssociado = usuarioService.adicionarNegocio(usuarioId, negocio);
                URI localizacao = ServletUriComponentsBuilder.fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(negocioAssociado.getId())
                                .toUri();
                return ResponseEntity.created(localizacao).body(negocioAssociado);
        }

        @DeleteMapping("/{usuarioId}/negocios/{negocioId}")
        @Operation(summary = "Remover um negócio de um usuário", description = "Remove um negócio de um usuário com base nos IDs fornecidos.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Negócio removido com sucesso!"),
                        @ApiResponse(responseCode = "400", description = "Solicitação inválida"),
                        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
                        @ApiResponse(responseCode = "404", description = "Usuário ou negócio não encontrado")
        })
        public ResponseEntity<String> removerNegocio(
                        @PathVariable Long usuarioId,
                        @PathVariable Long negocioId) {
                usuarioService.removerNegocioPorId(usuarioId, negocioId);
                return ResponseEntity.status(HttpStatus.OK).body("Negócio removido com sucesso!");
        }

}