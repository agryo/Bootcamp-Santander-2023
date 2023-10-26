package com.guia.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.guia.controller.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.guia.controller.dto.NegocioDto;
import com.guia.controller.dto.UsuarioDto;
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
        public ResponseEntity<List<UsuarioDto>> listaUsuarios() {
                List<Usuario> listaUsuarios = usuarioService.listarUsuarios();
                List<UsuarioDto> listaUsuariosDto = listaUsuarios.stream()
                                .map(UsuarioDto::new)
                                .collect(Collectors.toList());
                return ResponseEntity.ok(listaUsuariosDto);
        }

        @GetMapping("/{id}")
        @Operation(summary = "Buscar usuário por ID", description = "Exibe o usuário cadastrado no banco de dados com a ID solicitada. Caso não exista, diz que o usuário não existe.")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
                @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
                @ApiResponse(responseCode = "400", description = "UUID inválido")
        })
        public ResponseEntity<UsuarioDto> buscaUsuarioPorId(@PathVariable("id") String id) {
                UUID uuid;
                try {
                        uuid = UUID.fromString(id);
                } catch (IllegalArgumentException e) {
                        throw new InvalidUUIDException("ID inválido: " + id);
                }
                Usuario usuario = usuarioService.buscarUsuarioPorId(uuid);
                UsuarioDto usuarioDto = new UsuarioDto(usuario);
                return ResponseEntity.ok(usuarioDto);
        }

        @PostMapping
        @Operation(summary = "Salvar um usuário", description = "Salva o usuário preenchido no formulário JSON, caso todos os campos estejam corretos, ele salva o usuário no banco de dados e exibe o usuário criado.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Usuário salvo com sucesso!"),
                        @ApiResponse(responseCode = "422", description = "CPF, E-mail ou Telefone já cadastrado!")
        })
        public ResponseEntity<UsuarioDto> salvar(@RequestBody UsuarioDto usuarioDtoParaSalvar) {
                // Converter o UsuarioDto em um objeto Usuario
                Usuario usuarioParaSalvar = usuarioDtoParaSalvar.toModel();
                var usuarioSalvo = usuarioService.salvarUsuario(usuarioParaSalvar);
                // Converter o Usuario de volta para UsuarioDto
                UsuarioDto usuarioSalvoDto = new UsuarioDto(usuarioSalvo);
                URI localizacao = ServletUriComponentsBuilder.fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(usuarioSalvo.getId())
                                .toUri();
                return ResponseEntity.created(localizacao).body(usuarioSalvoDto);
        }

        @PatchMapping("/{id}")
        @Operation(summary = "Atualizar um usuário por ID", description = "Atualiza um usuário existente com base no ID fornecido.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso!"),
                        @ApiResponse(responseCode = "400", description = "Solicitação inválida"),
                        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
        })
        public ResponseEntity<UsuarioDto> atualizarUsuario(
                        @PathVariable UUID id,
                        @RequestBody Usuario usuarioAtualizado) {
                // Verifique se o usuário com o ID especificado existe no banco de dados
                Usuario usuarioExistente = usuarioService.atualizarUsuario(id, usuarioAtualizado);

                // Converta o Usuario atualizado de volta para UsuarioDto
                UsuarioDto usuarioSalvoDto = new UsuarioDto(usuarioExistente);
                return ResponseEntity.ok(usuarioSalvoDto);
        }

        @DeleteMapping("/{id}")
        @Operation(summary = "Remover um usuário por ID", description = "Apaga um usuário do banco de dados de acordo com o ID solicitado. Caso não exista, informa que o usuário não existe.")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "204", description = "Usuário removido com sucesso"),
                @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
                @ApiResponse(responseCode = "400", description = "UUID inválido")
        })
        public ResponseEntity<String> removerUsuario(@PathVariable("id") String id) {
                UUID uuid;
                try {
                        uuid = UUID.fromString(id);
                } catch (IllegalArgumentException e) {
                        throw new InvalidUUIDException("ID inválido: " + id);
                }
                usuarioService.apagarUsuarioPorId(uuid);
                return ResponseEntity.status(HttpStatus.OK).body("Usuário removido com sucesso!");
        }

        @PostMapping("/{usuarioId}/negocios")
        @Operation(summary = "Associar um negócio a um usuário", description = "Associa um negócio a um usuário com base nos IDs fornecidos.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Negócio associado com sucesso!"),
                        @ApiResponse(responseCode = "400", description = "Solicitação inválida"),
                        @ApiResponse(responseCode = "422", description = "Associação já existe")
        })
        public ResponseEntity<NegocioDto> adicionarNegocio(
                        @PathVariable UUID usuarioId,
                        @RequestBody NegocioDto negocioDto) {
                // Converter o NegocioDto em um objeto Negocio
                Negocio negocio = negocioDto.toModel();
                Negocio negocioAssociado = usuarioService.adicionarNegocio(usuarioId, negocio);
                // Converter o Negocio de volta para NegocioDto
                NegocioDto negocioAssociadoDto = new NegocioDto(negocioAssociado);
                URI localizacao = ServletUriComponentsBuilder.fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(negocioAssociado.getId())
                                .toUri();
                return ResponseEntity.created(localizacao).body(negocioAssociadoDto);
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
                        @PathVariable UUID usuarioId,
                        @PathVariable Long negocioId) {
                usuarioService.removerNegocioPorId(usuarioId, negocioId);
                return ResponseEntity.status(HttpStatus.OK).body("Negócio removido com sucesso!");
        }
}