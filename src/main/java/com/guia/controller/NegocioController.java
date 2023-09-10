package com.guia.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.guia.domain.model.Negocio;
import com.guia.service.NegocioService;

@RestController
@RequestMapping("/negocio")
public class NegocioController {
    @Autowired
    private final  NegocioService negocioService;

    public NegocioController(NegocioService negocioService) {
        this.negocioService = negocioService;
    }

    @PostMapping
    public ResponseEntity<Negocio> salvar(@RequestBody Negocio negocioParaSalvar) {
        var negocioSalvo = negocioService.salvarNegocio(negocioParaSalvar);
        URI localizacao = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(negocioSalvo.getId())
            .toUri();
        return ResponseEntity.created(localizacao).body(negocioSalvo);
    }

    @GetMapping
    public ResponseEntity<List<Negocio>> listaNegocios() {
        var listaUsuarios = negocioService.listaNegocios();
        return ResponseEntity.ok(listaUsuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Negocio>> buscaNegocioPorId(@PathVariable("id") Long id) {
        var negocio = negocioService.buscarPorId(id);
        return ResponseEntity.ok(negocio);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerNegocio(@PathVariable("id") Long id) {
        negocioService.buscarPorId(id)
            .map(usuario -> {
                negocioService.apagarNegocioPorId(id);
                return Void.TYPE;
            })
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!")
            );
    }
}
