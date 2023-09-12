package com.guia.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.guia.domain.model.Negocio;
import com.guia.service.NegocioService;

@CrossOrigin
@RestController
@RequestMapping("/negocio")
@Tag(name = "Operações de Negócio", description = "API RESTful para gerenciamento de negócios.")
public class NegocioController {
    @Autowired
    private final NegocioService negocioService;

    public NegocioController(NegocioService negocioService) {
        this.negocioService = negocioService;
    }

    @GetMapping
    @Operation(summary = "Lista todos os negócios", description = "Exibe uma lista com todos os negócios cadastrados no banco de dados. Ou exibe uma lista vazia, caso não tenha nenhum negócio cadastrado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista exibida com sucesso!")
    })
    public ResponseEntity<List<Negocio>> listaNegocios() {
        var listaNegocios = negocioService.listarNegocios();
        return ResponseEntity.ok(listaNegocios);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar negócio por ID", description = "Exibe o negócio cadastrado no banco de dados com a ID solicitada. Caso não exista, diz que o negócio não existe.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Negócio encontrado"),
            @ApiResponse(responseCode = "404", description = "Negócio não encontrado")
    })
    public ResponseEntity<Negocio> buscaNegocioPorId(@PathVariable("id") Long id) {
        Negocio negocio = negocioService.buscarPorId(id);
        return ResponseEntity.ok(negocio);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover um negócio por ID", description = "Apaga um negócio do banco de dados de acordo com o ID solicitado. Caso não exista, informa que o negócio não existe.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Negócio removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Negócio não encontrado")
    })
    public ResponseEntity<Void> removerNegocio(@PathVariable("id") Long id) {
        if (negocioService.buscarPorId(id) != null) {
            negocioService.apagarNegocioPorId(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Salvar um negócio", description = "Salva o negócio preenchido no formulário JSON, caso todos os campos estejam corretos, ele salva o negócio no banco de dados e exibe o negócio criado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Negócio salvo com sucesso!"),
            @ApiResponse(responseCode = "422", description = "Negócio já cadastrado!"),
            @ApiResponse(responseCode = "500", description = "... já cadastrado!")
    })
    public ResponseEntity<Negocio> salvar(@RequestBody Negocio negocioParaSalvar) {
        var negocioSalvo = negocioService.salvarNegocio(negocioParaSalvar);
        URI localizacao = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(negocioSalvo.getId())
                .toUri();
        return ResponseEntity.created(localizacao).body(negocioSalvo);
    }
}
