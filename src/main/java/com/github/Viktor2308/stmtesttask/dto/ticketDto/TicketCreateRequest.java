package com.github.Viktor2308.stmtesttask.dto.ticketDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TicketCreateRequest {
    @NotBlank
    @Pattern(regexp = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-4[a-fA-F0-9]{3}-[89abAB][a-fA-F0-9]{3}-[a-fA-F0-9]{12}")
    private String routeUuid;
    @NotBlank
    private LocalDateTime dateTime;
    @NotBlank
    @Positive
    private String placeNumber;
    @NotBlank
    private BigDecimal price;
}
