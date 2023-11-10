package com.github.Viktor2308.stmtesttask.dto.routeDto;

import com.github.Viktor2308.stmtesttask.entity.ShippingCompany;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RouteUpdateRequest {
    private UUID id;
    private String departurePoint;
    private String destinationPoint;
    private UUID companyUuid;
    private int routeDurationInMin;
}
