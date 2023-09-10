package com.guia.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
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
public class NegocioController {
    @Autowired
    private final NegocioService negocioService;
    @Autowired
    private final UsuarioService usuarioService;

    public NegocioController(NegocioService negocioService, UsuarioService usuarioService) {
        this.negocioService = negocioService;
        this.usuarioService = usuarioService;
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
        var listaNegocios = negocioService.listaNegocios();
        return ResponseEntity.ok(listaNegocios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Negocio> buscaNegocioPorId(@PathVariable("id") Long id) {
        Negocio negocio = negocioService.buscarPorId(id);
        return ResponseEntity.ok(negocio);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerNegocio(@PathVariable("id") Long id) {
        if (negocioService.buscarPorId(id) != null) {
            negocioService.apagarNegocioPorId(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

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
