package com.example.park_api.services;

import com.example.park_api.repositories.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final IUsuarioRepository iUsuarioRepository;

}
