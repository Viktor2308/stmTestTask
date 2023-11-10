package com.github.Viktor2308.stmtesttask.controller;

import com.github.Viktor2308.stmtesttask.dto.routeDto.RouteCreateRequest;
import com.github.Viktor2308.stmtesttask.dto.routeDto.RouteUpdateRequest;
import com.github.Viktor2308.stmtesttask.service.RouteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/route")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @Operation(
            summary = "Create new route, ADMIN only",
            security = @SecurityRequirement(name = "Bearer ")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")})
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createRoute(@RequestBody @Valid RouteCreateRequest routeCreateRequest) {
        routeService.createRoute(routeCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Edit route, ADMIN only",
            security = @SecurityRequirement(name = "Bearer ")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")})
    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> editRoute(@RequestBody @Valid RouteUpdateRequest updateRouteRequest) {
        routeService.editRoute(updateRouteRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(
            summary = "Delete route by id, ADMIN only",
            security = @SecurityRequirement(name = "Bearer ")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")})
    @DeleteMapping("/{routeUuid}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteRoute(@PathVariable UUID routeUuid) {
        routeService.deleteRoute(routeUuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
