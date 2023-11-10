package com.github.Viktor2308.stmtesttask.repository;

import com.github.Viktor2308.stmtesttask.dto.ticketDto.TicketUpdateRequest;
import com.github.Viktor2308.stmtesttask.entity.Ticket;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TicketRepository {
    Page<Ticket> findAllTickets(Integer page, Integer size, LocalDateTime startDate, LocalDateTime endDate, String departurePoint, String arrivalPoint, String shippingCompanyName);

    void buyTicket(UUID userUuid, UUID ticketUuid);

    List<Ticket> getUserTicket(UUID userUuid);

    void saveTicket(Ticket ticket);

    void updateTicket(TicketUpdateRequest updateTicketRequest);

    void deleteTicket(UUID ticketUuid);
}
