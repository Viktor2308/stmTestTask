package com.github.Viktor2308.stmtesttask.controller;

import com.github.Viktor2308.stmtesttask.dto.shippingCompanyDto.ShippingCompanyCreateRequest;
import com.github.Viktor2308.stmtesttask.dto.shippingCompanyDto.ShippingCompanyUpdateRequest;
import com.github.Viktor2308.stmtesttask.service.ShippingCompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class ShippingCompanyController {

    private final ShippingCompanyService shippingCompanyService;
    @Operation(
            summary = "Create new ShippingCompany, ADMIN only",
            security = @SecurityRequirement(name = "Bearer ")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")})
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createShippingCompany(@RequestBody ShippingCompanyCreateRequest shippingCompanyCreateRequest) {
        shippingCompanyService.createShippingCompany(shippingCompanyCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Edit ShippingCompany, ADMIN only",
            security = @SecurityRequirement(name = "Bearer ")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")})
    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> editShippingCompany(@RequestBody ShippingCompanyUpdateRequest shippingCompanyUpdateRequest) {
        shippingCompanyService.editShippingCompany(shippingCompanyUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(
            summary = "Delete ShippingCompany by id, ADMIN only",
            security = @SecurityRequirement(name = "Bearer ")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")})
    @DeleteMapping("/{companyUuid}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteShippingCompany(@PathVariable UUID companyUuid){
        shippingCompanyService.deleteShippingCompany(companyUuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
