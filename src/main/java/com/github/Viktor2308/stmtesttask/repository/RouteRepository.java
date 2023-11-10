package com.github.Viktor2308.stmtesttask.repository;

import com.github.Viktor2308.stmtesttask.dto.routeDto.RouteCreateRequest;
import com.github.Viktor2308.stmtesttask.dto.routeDto.RouteUpdateRequest;
import com.github.Viktor2308.stmtesttask.entity.Route;

import java.util.UUID;

public interface RouteRepository {
    Route findById(String routeUuid);

    void saveRoute(RouteCreateRequest routeCreateRequest);

    void updateRoute(RouteUpdateRequest updateRouteRequest);

    void deleteRoute(UUID routeUuid);
}
