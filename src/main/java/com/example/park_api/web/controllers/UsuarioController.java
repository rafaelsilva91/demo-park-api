package com.example.park_api.web.controllers;

import com.example.park_api.entities.Usuario;
import com.example.park_api.services.UsuarioService;
import com.example.park_api.web.dto.UsuarioCreateDto;
import com.example.park_api.web.dto.UsuarioResponseDto;
import com.example.park_api.web.dto.UsuarioSenhaDto;
import com.example.park_api.web.dto.mapper.UsuarioMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/v1/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create(@Valid @RequestBody UsuarioCreateDto createDto){
        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> findById(@PathVariable Long id){
        Usuario user = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(UsuarioMapper.toDto(user));
    }

    @GetMapping("/")
    public ResponseEntity<List<UsuarioResponseDto>> findAll(){
        List<Usuario> users = usuarioService.findAll();
        return ResponseEntity.ok(UsuarioMapper.toListDto(users));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id,
                                               @Valid @RequestBody UsuarioSenhaDto dto){
        Usuario user = usuarioService.editPassword(id,
                dto.getSenhaAtual(),
                dto.getNovaSenha(),
                dto.getConfirmaSenha());

        return ResponseEntity.noContent().build();
    }
}
