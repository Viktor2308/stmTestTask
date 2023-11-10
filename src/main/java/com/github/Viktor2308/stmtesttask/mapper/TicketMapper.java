package com.github.Viktor2308.stmtesttask.mapper;

import com.github.Viktor2308.stmtesttask.dto.ticketDto.TicketDtoResponse;
import com.github.Viktor2308.stmtesttask.entity.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public interface TicketMapper {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "route.company.name", target = "companyName"),
            @Mapping(source = "route.company.phone", target = "companyPhone"),
            @Mapping(source = "route.departurePoint", target = "departurePoint"),
            @Mapping(source = "route.destinationPoint", target = "destinationPoint"),
            @Mapping(source = "route.routeDurationInMin", target = "routeDurationInMin"),
            @Mapping(source = "dateTime", target = "dateTime"),
            @Mapping(source = "placeNumber", target = "placeNumber")
    })
    TicketDtoResponse toTicketDto(Ticket ticket);

}
