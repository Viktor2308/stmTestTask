package com.github.Viktor2308.stmtesttask.controller;

import com.github.Viktor2308.stmtesttask.dto.ticketDto.TicketBuyRequest;
import com.github.Viktor2308.stmtesttask.dto.ticketDto.TicketCreateRequest;
import com.github.Viktor2308.stmtesttask.dto.ticketDto.TicketDtoResponse;
import com.github.Viktor2308.stmtesttask.dto.ticketDto.TicketUpdateRequest;
import com.github.Viktor2308.stmtesttask.entity.Ticket;
import com.github.Viktor2308.stmtesttask.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ticket")
@RequiredArgsConstructor
@Validated
public class TicketController {

    private final TicketService ticketService;

    @Operation(
            summary = "Get tickets with filter date and departure arrival points",
            security = @SecurityRequirement(name = "Bearer ")
    )
    @GetMapping("/available")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Page<Ticket>> getAvailableTicket(
            @Parameter(
                    in = ParameterIn.QUERY, required = true,
                    description = "Page",
                    schema = @Schema(type = "integer"))
            @RequestParam(name = "page", defaultValue = "0") @Min(0) Integer page,
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "Count tickets on one page",
                    schema = @Schema(type = "integer"))
            @RequestParam(name = "size", defaultValue = "10") @Min(1) @Max(100) Integer size,
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "Start_date filter",
                    schema = @Schema(implementation = LocalDateTime.class))
            @RequestParam(name = "start_date", required = false)
            @DateTimeFormat(pattern = "d::MMM::uuuu HH::mm::ss") LocalDateTime startDate,
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "End_date filter",
                    schema = @Schema(implementation = LocalDateTime.class))
            @RequestParam(name = "end_date", required = false)
            @DateTimeFormat(pattern = "d::MMM::uuuu HH::mm::ss") LocalDateTime endDate,
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "Departure point filter",
                    schema = @Schema(implementation = String.class))
            @RequestParam(name = "departure_point", required = false) String departurePoint,
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "Arrival point filter",
                    schema = @Schema(implementation = String.class))
            @RequestParam(name = "arrival_point", required = false) String arrivalPoint,
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "Shipping company name filter",
                    schema = @Schema(implementation = String.class))
            @RequestParam(name = "shipping_company", required = false) String shippingCompanyName
    ) {
        return ResponseEntity.ok(ticketService.findAvailableTickets(page, size, startDate,
                endDate, departurePoint, arrivalPoint, shippingCompanyName));
    }

    @Operation(
            summary = "Buy ticket by ticket_UUID",
            security = @SecurityRequirement(name = "Bearer ")
    )
    @PostMapping("/buy")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> buyTicket(@RequestBody TicketBuyRequest ticketBuyRequest) {
        ticketService.buyTicket(ticketBuyRequest);
        return ResponseEntity.ok("Ticket purchased successfully");
    }

    @Operation(
            summary = "Get all current user ticket",
            security = @SecurityRequirement(name = "Bearer ")
    )
    @GetMapping("/")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<TicketDtoResponse>> getMyTickets() {
        return ResponseEntity.ok(ticketService.getCurrentUsersTickets());
    }
    @Operation(
            summary = "Create new ticket, ADMIN only",
            security = @SecurityRequirement(name = "Bearer ")
    )
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createTicket(@RequestBody TicketCreateRequest ticketCreateRequest) {
        ticketService.createTicket(ticketCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Edit ticket, ADMIN only",
            security = @SecurityRequirement(name = "Bearer ")
    )
    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> editTicket(@RequestBody TicketUpdateRequest updateTicketRequest) {
        ticketService.editTicket(updateTicketRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(
            summary = "Delete ticket by id, ADMIN only",
            security = @SecurityRequirement(name = "Bearer ")
    )
    @DeleteMapping("/{ticketUuid}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteTicket(@PathVariable UUID ticketUuid){
        ticketService.deleteTicket(ticketUuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
