package br.com.rivaldo.models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.io.Serializable;

public record AuthenticateRequest(
        @Schema(description = "User email", example = "rivaldo@mail.com")
        @Email(message = "Invalid email")
        @Size(min = 6, max = 50, message = "Email must contain between 3 and 50 characters")
        String email,

        @Schema(description = "User password", example = "123456")
        @Size(min = 6, max = 20, message = "Password must contain between 6 and 20 characters")
        @NotBlank(message = "Password cannot be empty")
        String password
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}