package com.github.Viktor2308.stmtesttask.dto.routeDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RouteCreateRequest {
    @NotBlank
    private String departurePoint;
    @NotBlank
    private String destinationPoint;
    @NotBlank
    @Pattern(regexp = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-4[a-fA-F0-9]{3}-[89abAB][a-fA-F0-9]{3}-[a-fA-F0-9]{12}")
    private UUID companyUuid;
    @Positive
    private int routeDurationInMin;
}
