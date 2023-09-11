package com.guia.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guia.domain.model.Negocio;
import com.guia.domain.model.Usuario;
import com.guia.service.NegocioService;
import com.guia.service.UsuarioService;

@RestController
@RequestMapping("/negocio")
@OpenAPIDefinition(
    info = @Info(
        title = "Projeto Guia-Cruzeta",
        description = "Projeto RESTful API do Bootcamp Santander 2023",
        version = "1.0.5",
        contact = @Contact(
            name = "Agryo Araujo",
            url = "https://www.linkedin.com/in/agryo/",
            email = "agryostallion@gmail.com"
        )
    )
)
public class NegocioController {
    @Autowired
    private final NegocioService negocioService;
    @Autowired
    private final UsuarioService usuarioService;

    public NegocioController(NegocioService negocioService, UsuarioService usuarioService) {
        this.negocioService = negocioService;
        this.usuarioService = usuarioService;
    }

    @Operation(summary = "Salvar um negócio")
    @PostMapping
    public ResponseEntity<Negocio> salvar(@RequestBody Negocio negocioParaSalvar) {
        var negocioSalvo = negocioService.salvarNegocio(negocioParaSalvar);
        URI localizacao = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(negocioSalvo.getId())
                .toUri();
        return ResponseEntity.created(localizacao).body(negocioSalvo);
    }

    @Operation(summary = "Lista todos os negócios")
    @GetMapping
    public ResponseEntity<List<Negocio>> listaNegocios() {
        var listaNegocios = negocioService.listaNegocios();
        return ResponseEntity.ok(listaNegocios);
    }

    @Operation(summary = "Buscar negócio por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Negócio encontrado"),
        @ApiResponse(responseCode = "404", description = "Negócio não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Negocio> buscaNegocioPorId(@PathVariable("id") Long id) {
        Negocio negocio = negocioService.buscarPorId(id);
        return ResponseEntity.ok(negocio);
    }

    @Operation(summary = "Remover um negócio por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Negócio removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Negócio não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerNegocio(@PathVariable("id") Long id) {
        if (negocioService.buscarPorId(id) != null) {
            negocioService.apagarNegocioPorId(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Associar um usuário a um negócio")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Associação realizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Negócio ou usuário não encontrado")
    })
    @PostMapping("/{negocioId}/associar-usuario/{usuarioId}")
    public ResponseEntity<Void> associarUsuarioAoNegocio(
            @PathVariable("negocioId") Long negocioId,
            @PathVariable("usuarioId") Long usuarioId) {
        
        // Busque o negócio pelo ID
        Negocio negocio = negocioService.buscarPorId(negocioId);
        
        // Busque o usuário pelo ID
        Usuario usuario = usuarioService.buscarPorId(usuarioId);

        if (negocio != null && usuario != null) {
            // Associe o usuário ao negócio
            negocio.setUsuario(usuario);
            
            // Salve o negócio para persistir a associação
            negocioService.salvarNegocio(negocio);
            
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
