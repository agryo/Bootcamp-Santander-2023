package com.guia.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guia.controller.dto.NegocioDto;
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
    public ResponseEntity<List<NegocioDto>> listaNegocios() {
        List<Negocio> listaNegocios = negocioService.listarNegocios();
        List<NegocioDto> listaNegociosDto = listaNegocios.stream()
                .map(NegocioDto::new) // Converter Negocio para NegocioDto
                .collect(Collectors.toList());
        return ResponseEntity.ok(listaNegociosDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar negócio por ID", description = "Exibe o negócio cadastrado no banco de dados com a ID solicitada. Caso não exista, diz que o negócio não existe.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Negócio encontrado"),
            @ApiResponse(responseCode = "404", description = "Negócio não encontrado")
    })
    public ResponseEntity<NegocioDto> buscaNegocioPorId(@PathVariable("id") Long id) {
        Negocio negocio = negocioService.buscarNegocioPorId(id);
        NegocioDto negocioDto = new NegocioDto(negocio); // Converter Negocio para NegocioDto
        return ResponseEntity.ok(negocioDto);
    }

    @GetMapping("/buscar/{nome}")
    @Operation(summary = "Buscar negócio por Nome", description = "Exibe os negócios cadastrados no banco de dados com o nome solicitado. Caso não exista, diz que o negócio não existe.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Negócio encontrado"),
            @ApiResponse(responseCode = "404", description = "Negócio não encontrado")
    })
    public ResponseEntity<List<NegocioDto>> buscaNegocioPorNome(@PathVariable("nome") String nome) {
        List<Negocio> negocios = negocioService.buscarNegocioPorNome(nome);
        List<NegocioDto> negocioDtos = negocios.stream()
                .map(NegocioDto::new)
                .collect(Collectors.toList()); // Converter Negocio para NegocioDto
        return ResponseEntity.ok(negocioDtos);
    }
}