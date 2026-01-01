package br.com.rivaldo.models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateOrderRequest(

        @Schema(description = "Request ID", example = "692f4a3b6187ed4f18b65147")
        @NotBlank(message = "The requesterId ID cannot be null or blanck")
        @Size(min = 24, max = 36, message = "The requesterId must be between 24 and 36 characters")
        String requesterId,

        @Schema(description = "Customer ID", example = "692f4a3b6187ed4f18b65147")
        @NotBlank(message = "The customerId ID cannot be null or blanck")
        @Size(min = 24, max = 36, message = "The customerId must be between 24 and 36 characters")
        String customerId,

        @Schema(description = "Title of order", example = "Fix my computer")
        @NotBlank(message = "The title cannot be null or blanck")
        @Size(min = 3, max = 45, message = "The title must be between 3 and 45 characters")
        String title,

        @Schema(description = "Description of order", example = "My computer is broken")
        @NotBlank(message = "The description cannot be null or blanck")
        @Size(min = 10, max = 3000, message = "The title must be between 10 and 3000 characters")
        String description,

        @Schema(description = "Status of order", example = "Open")
        @NotBlank(message = "The status cannot be null or blanck")
        @Size(min = 4, max = 15, message = "The status must be between 4 and 15 characters")
        String status
) {}
