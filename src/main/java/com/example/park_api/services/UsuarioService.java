package com.example.park_api.services;

import com.example.park_api.entities.Usuario;
import com.example.park_api.exception.PasswordInvalidException;
import com.example.park_api.exception.UserNotFoundException;
import com.example.park_api.exception.UsernameUniqueViolationException;
import com.example.park_api.repositories.IUsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final IUsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        try {
            return usuarioRepository.save(usuario);
        }catch (DataIntegrityViolationException e){
            throw new UsernameUniqueViolationException(String.format("Username [%s] já cadastrado",
            usuario.getUsername()));
        }
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(
                ()-> new UserNotFoundException(String.format("Usuário id=%s não encontrado", id))
        );
    }

    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public Usuario editPassword(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
        if(!novaSenha.equals(confirmaSenha)){
            throw new PasswordInvalidException("Nova senha não confere com a confirmação de senha");
        }

        Usuario user = this.buscarPorId(id);
        if(!user.getPassword().equals(senhaAtual)){
            throw new PasswordInvalidException("Sua senha não confere");
        }

        user.setPassword(novaSenha);
        return user;
    }
}
