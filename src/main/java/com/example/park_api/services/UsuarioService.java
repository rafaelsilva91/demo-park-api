package com.example.park_api.services;

import com.example.park_api.entities.Usuario;
import com.example.park_api.repositories.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final IUsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Usuário não encontrado")
        );
    }

    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public Usuario editPassword(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
        if(!novaSenha.equals(confirmaSenha)){
            throw new RuntimeException("Nova senha não confere com a confirmação de senha");
        }

        Usuario user = this.buscarPorId(id);
        if(!user.getPassword().equals(senhaAtual)){
            throw new RuntimeException("Sua senha não confere");
        }

        user.setPassword(novaSenha);
        return user;
    }
}
