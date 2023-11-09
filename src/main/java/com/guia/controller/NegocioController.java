package com.guia.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import com.guia.controller.dto.NegocioDto;
import com.guia.domain.model.Negocio;
import com.guia.service.NegocioService;
import com.guia.service.UploadService;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/negocio")
@Tag(name = "Operações de Negócio", description = "API RESTful para gerenciamento de negócios.")
public class NegocioController {
    @Autowired
    private final NegocioService negocioService;
    @Autowired
    private final UploadService uploadService;
    public NegocioController(NegocioService negocioService, UploadService uploadService) {
        this.negocioService = negocioService;
        this.uploadService = uploadService;
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

    @GetMapping("/listar/{quantidade}")
    @Operation(summary = "Lista os primeiros negócios", description = "Exibe os primeiros 'quantidade' negócios cadastrados no banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista exibida com sucesso!")
    })
    public ResponseEntity<List<NegocioDto>> listaPrimeirosNegocios(@PathVariable("quantidade") int quantidade) {
        List<Negocio> primeirosNegocios = negocioService.listarPrimeirosNegocios(quantidade);
        List<NegocioDto> negocioDtos = primeirosNegocios.stream()
                .map(NegocioDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(negocioDtos);
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

    @PostMapping("/upload")
    @Operation(summary = "Upload de Imagem do Negócio", description = "Faz o upload de uma imagem para o negócio e retorna o link da imagem.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Upload de imagem realizado com sucesso!")
    })
    public ResponseEntity<String> fazerUpload(@RequestParam("file") MultipartFile file) {
        // Verifique se o tipo de arquivo é permitido
        if (!isFileTypeAllowed(file.getOriginalFilename())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tipo de arquivo não permitido.");
        }

        String linkDaImagem = uploadService.fazerUpload(file);
        return ResponseEntity.ok(linkDaImagem);
    }

    private boolean isFileTypeAllowed(String fileName) {
        // Lista de tipos de arquivo permitidos
        List<String> allowedFileTypes = Arrays.asList("jpg", "jpeg", "png", "bmp", "sgv");
        // Obtém a extensão do arquivo
        String fileExtension = getFileExtension(fileName);

        // Verifica se a extensão do arquivo está na lista de tipos permitidos
        return allowedFileTypes.contains(fileExtension.toLowerCase());
    }

    private String getFileExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        if (lastIndex == -1) {
            return ""; // Caso não haja extensão
        }
        return fileName.substring(lastIndex + 1);
    }
}