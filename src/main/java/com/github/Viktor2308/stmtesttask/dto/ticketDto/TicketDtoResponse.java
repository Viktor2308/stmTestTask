package com.github.Viktor2308.stmtesttask.dto.ticketDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class TicketDtoResponse {
    private UUID id;
    private String companyName;
    private String companyPhone;
    private String departurePoint;
    private String destinationPoint;
    private int routeDurationInMin;
    private LocalDateTime dateTime;
    private String placeNumber;
}
