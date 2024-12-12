package com.example.park_api.web.dto;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioCreateDto {

    private String username;
    private String password;
}
