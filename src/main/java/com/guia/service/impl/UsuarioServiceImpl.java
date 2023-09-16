package com.guia.service.impl;

import java.util.Optional;
import java.util.Objects;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guia.domain.model.Endereco;
import com.guia.domain.model.Negocio;
import com.guia.domain.model.Telefone;
import com.guia.domain.model.Usuario;
import com.guia.domain.repository.NegocioRepository;
import com.guia.domain.repository.UsuarioRepository;
import com.guia.service.UsuarioService;
import com.guia.service.exception.BusinessException;
import com.guia.service.exception.NotFoundException;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private final UsuarioRepository usuarioRepository;

    @Autowired
    private final NegocioRepository negocioRepository;

    public UsuarioServiceImpl(
            UsuarioRepository usuarioRepository,
            NegocioRepository negocioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.negocioRepository = negocioRepository;
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário com o ID: " + id + ", não encontrado!"));
    }

    @Transactional
    public void apagarUsuarioPorId(Long id) {
        Usuario usuarioParaRemover = buscarUsuarioPorId(id);
        usuarioRepository.delete(usuarioParaRemover);
    }

    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        validarUsuario(usuario);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario atualizarUsuario(Long id, Usuario usuarioAtualizado) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado com o ID: " + id));
        // Atualize os campos simples (nome, CPF, email)
        if (!Objects.equals(usuarioAtualizado.getNome(), usuarioExistente.getNome())) {
            usuarioExistente.setNome(usuarioAtualizado.getNome());
        }
        if (!Objects.equals(usuarioAtualizado.getCpf(), usuarioExistente.getCpf())) {
            usuarioExistente.setCpf(usuarioAtualizado.getCpf());
        }
        if (!Objects.equals(usuarioAtualizado.getEmail(), usuarioExistente.getEmail())) {
            usuarioExistente.setEmail(usuarioAtualizado.getEmail());
        }
        // Atualize os endereços
        List<Endereco> enderecosAtualizados = usuarioAtualizado.getEnderecos();
        List<Endereco> enderecosExistentes = usuarioExistente.getEnderecos();
        for (Endereco endereco : enderecosAtualizados) {
            if (!enderecosExistentes.contains(endereco)) {
                enderecosExistentes.add(endereco);
            }
        }
        // Atualize os telefones
        List<Telefone> telefonesAtualizados = usuarioAtualizado.getTelefones();
        List<Telefone> telefonesExistentes = usuarioExistente.getTelefones();
        for (Telefone telefone : telefonesAtualizados) {
            if (!telefonesExistentes.contains(telefone)) {
                telefonesExistentes.add(telefone);
            }
        }
        // Salve o usuário atualizado no banco de dados
        return usuarioRepository.save(usuarioExistente);
    }

    @Transactional
    public Negocio adicionarNegocio(Long usuarioId, Negocio negocio) {
        Usuario usuario = buscarUsuarioPorId(usuarioId);
        validarNegocio(negocio);
        // Verifique se o nome do negócio já existe
        if (negocioRepository.existsByNome(negocio.getNome())) {
            throw new BusinessException("Este nome de negócio já existe.");
        }
        // Verifique se o número do negócio já existe
        for (Telefone telefone : negocio.getTelefones()) {
            if (negocioRepository.existsByTelefonesNumero(telefone.getNumero())) {
                throw new BusinessException("Este número de telefone " + telefone.getNumero()
                        + ", já está em uso por outro negócio ou usuário.");
            }
        }
        usuario.getNegocios().add(negocio);
        negocio.setUsuario(usuario);
        // Salve o objeto Negocio no repositório antes de retorná-lo
        negocioRepository.save(negocio);
        // Salve o objeto Usuario no repositório
        usuarioRepository.save(usuario);
        // Retorne o negócio
        return negocio;
    }

    @Transactional
    public void removerNegocioPorId(Long usuarioId, Long negocioId) {
        // Verifica se o usuário existe
        Usuario usuario = buscarUsuarioPorId(usuarioId);
        // Verifica se o negócio existe
        Negocio negocioParaRemover = usuario.getNegocios().stream()
                .filter(negocio -> negocio.getId().equals(negocioId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Negócio não encontrado."));
        // Verifique se o usuário que está fazendo a solicitação é o proprietário do
        // negócio
        if (!negocioParaRemover.getUsuario().getId().equals(usuarioId)) {
            throw new BusinessException("Você não tem permissão para remover este negócio.");
        }
        usuario.getNegocios().remove(negocioParaRemover);
        negocioParaRemover.setUsuario(null);
        usuarioRepository.save(usuario);
        negocioRepository.delete(negocioParaRemover);
    }

    private void validarUsuario(Usuario usuario) {
        Optional.ofNullable(usuario)
                .orElseThrow(() -> new BusinessException("O usuário a ser criado não pode ser nulo."));
        Optional.ofNullable(usuario.getCpf())
                .orElseThrow(() -> new BusinessException("O CPF do usuário não pode ser nulo."));
        Optional.ofNullable(usuario.getEmail())
                .orElseThrow(() -> new BusinessException("O E-mail do usuário não pode ser nulo."));
        // Realiza os testes dos campos únicos
        if (usuarioRepository.existsByCpf(usuario.getCpf())) {
            throw new BusinessException("Este número de CPF já existe.");
        }
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new BusinessException("Este e-mail já está em uso por outro usuário.");
        }
        for (Telefone telefone : usuario.getTelefones()) {
            if (usuarioRepository.existsByTelefonesNumero(telefone.getNumero())) {
                throw new BusinessException("Este número de telefone já está em uso por outro usuário.");
            }
        }
    }

    private void validarNegocio(Negocio negocio) {
        Optional.ofNullable(negocio)
                .orElseThrow(() -> new BusinessException("O negócio a ser criado não pode ser nulo."));
        Optional.ofNullable(negocio.getNome())
                .orElseThrow(() -> new BusinessException("O nome do negócio não pode ser nulo."));
    }
}