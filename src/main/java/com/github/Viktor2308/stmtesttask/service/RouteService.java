package com.github.Viktor2308.stmtesttask.service;

import com.github.Viktor2308.stmtesttask.dto.routeDto.RouteCreateRequest;
import com.github.Viktor2308.stmtesttask.dto.routeDto.RouteUpdateRequest;
import com.github.Viktor2308.stmtesttask.entity.Route;

import java.util.UUID;

public interface RouteService {
    Route findById(String routeUuid);

    void createRoute(RouteCreateRequest routeCreateRequest);

    void editRoute(RouteUpdateRequest updateRouteRequest);

    void deleteRoute(UUID routeUuid);
}
