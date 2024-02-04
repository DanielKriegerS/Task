package com.danielks.Tasks.models;

import jakarta.validation.constraints.NotNull;

public record TaskModelBasic(
    @NotNull Long id
) {
}
