package com.example.park_api.services;

import com.example.park_api.entities.Usuario;
import com.example.park_api.entities.enums.RoleEnum;
import com.example.park_api.exception.PasswordInvalidException;
import com.example.park_api.exception.UserNotFoundException;
import com.example.park_api.exception.UsernameUniqueViolationException;
import com.example.park_api.repositories.IUsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final IUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        try {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
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
        if(!passwordEncoder.matches(senhaAtual, user.getPassword())){
            throw new PasswordInvalidException("Sua senha não confere");
        }
//        if(!user.getPassword().equals(senhaAtual)){
//            throw new PasswordInvalidException("Sua senha não confere");
//        }

        user.setPassword(passwordEncoder.encode(novaSenha));
//        user.setPassword(novaSenha);
        return user;
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username).orElseThrow(
                ()-> new UserNotFoundException(String.format("Usuário '%s' não encontrado", username))
        );
    }

    @Transactional(readOnly = true)
    public RoleEnum buscarRolePorUsername(String username) {
        return usuarioRepository.findRoleByUsername(username);
    }
}
