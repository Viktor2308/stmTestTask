package com.github.Viktor2308.stmtesttask.service;

import com.github.Viktor2308.stmtesttask.dto.ticketDto.TicketBuyRequest;
import com.github.Viktor2308.stmtesttask.dto.ticketDto.TicketCreateRequest;
import com.github.Viktor2308.stmtesttask.dto.ticketDto.TicketDtoResponse;
import com.github.Viktor2308.stmtesttask.dto.ticketDto.TicketUpdateRequest;
import com.github.Viktor2308.stmtesttask.entity.Ticket;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Interface for operation with tickets
 * @see Ticket
 */
public interface TicketService {
    Page<Ticket> findAvailableTickets(
            Integer page,
            Integer size,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String departurePoint,
            String arrivalPoint,
            String shippingCompanyName);

    void buyTicket(TicketBuyRequest ticketBuyRequest);

    List<TicketDtoResponse> getCurrentUsersTickets();

    void createTicket(TicketCreateRequest ticketCreateRequest);

    void editTicket(TicketUpdateRequest updateTicketRequest);

    void deleteTicket(UUID ticketUuid);
}
