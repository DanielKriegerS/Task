package com.danielks.Tasks.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record TaskDTO(
        Long id,
        @NotEmpty (message = "{header.notempty}") String header,
        @NotEmpty (message = "{body.notempty}") String body,
        @NotNull boolean ended
) {
}
