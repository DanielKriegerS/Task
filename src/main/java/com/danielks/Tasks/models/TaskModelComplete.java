package com.danielks.Tasks.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskModelComplete(
        @NotNull Long id,
        @NotBlank (message = "O cabeçalho nao pode estar vazio!") String header,
        @NotBlank (message = "O corpo nao pode estar vazio!") String body,
        @NotNull boolean ended
) {
}
