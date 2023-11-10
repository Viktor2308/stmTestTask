package com.github.Viktor2308.stmtesttask.dto.ticketDto;

import com.github.Viktor2308.stmtesttask.entity.Route;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class TicketUpdateRequest {
    @NotBlank
    @Pattern(regexp = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-4[a-fA-F0-9]{3}-[89abAB][a-fA-F0-9]{3}-[a-fA-F0-9]{12}")
    private UUID id;
    @NotBlank
    @Pattern(regexp = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-4[a-fA-F0-9]{3}-[89abAB][a-fA-F0-9]{3}-[a-fA-F0-9]{12}")
    private UUID routeUuid;
    @NotBlank
    private LocalDateTime dateTime;
    @NotBlank
    private String placeNumber;
    @NotBlank
    private BigDecimal price;
}
