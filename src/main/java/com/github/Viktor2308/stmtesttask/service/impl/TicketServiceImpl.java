package com.github.Viktor2308.stmtesttask.service.impl;

import com.github.Viktor2308.stmtesttask.dto.ticketDto.TicketBuyRequest;
import com.github.Viktor2308.stmtesttask.dto.ticketDto.TicketCreateRequest;
import com.github.Viktor2308.stmtesttask.dto.ticketDto.TicketDtoResponse;
import com.github.Viktor2308.stmtesttask.dto.ticketDto.TicketUpdateRequest;
import com.github.Viktor2308.stmtesttask.entity.Route;
import com.github.Viktor2308.stmtesttask.entity.Ticket;
import com.github.Viktor2308.stmtesttask.entity.User;
import com.github.Viktor2308.stmtesttask.mapper.TicketMapper;
import com.github.Viktor2308.stmtesttask.repository.TicketRepository;
import com.github.Viktor2308.stmtesttask.service.AuthService;
import com.github.Viktor2308.stmtesttask.service.RouteService;
import com.github.Viktor2308.stmtesttask.service.TicketService;
import com.github.Viktor2308.stmtesttask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final RouteService routeService;
    private final AuthService authService;
    private final UserService userService;
    private final TicketMapper ticketMapper;

    @Override
    public Page<Ticket> findAvailableTickets(
            Integer page, Integer size,
            LocalDateTime startDate, LocalDateTime endDate,
            String departurePoint, String arrivalPoint, String shippingCompanyName) {

        return ticketRepository.findAllTickets(page, size, startDate, endDate, departurePoint, arrivalPoint, shippingCompanyName);
    }

    @Override
    public void buyTicket(TicketBuyRequest ticketBuyRequest) {
        String currentUserLogin = authService.getAuthInfo().getName();
        User user = userService.getUserByLogin(currentUserLogin).orElseThrow(
                () -> new NotFoundException("User not found"));
        ticketRepository.buyTicket(user.getId(), UUID.fromString(ticketBuyRequest.getTicketUuid()));

    }

    @Override
    public List<TicketDtoResponse> getCurrentUsersTickets() {
        String currentUserLogin = authService.getAuthInfo().getName();
        User user = userService.getUserByLogin(currentUserLogin).orElseThrow(
                () -> new NotFoundException("User not found"));
        return ticketRepository.getUserTicket(user.getId())
                .parallelStream()
                .map(ticketMapper::toTicketDto)
                .toList();
    }

    @Override
    public void createTicket(TicketCreateRequest ticketCreateRequest) {
        Route route = routeService.findById(ticketCreateRequest.getRouteUuid());
        Ticket ticket = Ticket.builder()
                .route(route)
                .dateTime(ticketCreateRequest.getDateTime())
                .placeNumber(ticketCreateRequest.getPlaceNumber())
                .price(ticketCreateRequest.getPrice())
                .available(true)
                .build();
        ticketRepository.saveTicket(ticket);
    }

    @Override
    public void editTicket(TicketUpdateRequest updateTicketRequest) {
        ticketRepository.updateTicket(updateTicketRequest);
    }

    @Override
    public void deleteTicket(UUID ticketUuid) {
        ticketRepository.deleteTicket(ticketUuid);
    }
}
