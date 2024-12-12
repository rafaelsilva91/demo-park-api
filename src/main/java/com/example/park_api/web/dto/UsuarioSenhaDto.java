package com.example.park_api.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioSenhaDto {

    @NotBlank
    @Size(min = 6, max = 8)
    private String senhaAtual;

    @NotBlank
    @Size(min = 6, max = 8)
    private String novaSenha;

    @NotBlank
    @Size(min = 6, max = 8)
    private String confirmaSenha;
}
