package com.github.Viktor2308.stmtesttask.service.impl;

import com.github.Viktor2308.stmtesttask.dto.routeDto.RouteCreateRequest;
import com.github.Viktor2308.stmtesttask.dto.routeDto.RouteUpdateRequest;
import com.github.Viktor2308.stmtesttask.entity.Route;
import com.github.Viktor2308.stmtesttask.repository.RouteRepository;
import com.github.Viktor2308.stmtesttask.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;

    @Override
    public Route findById(String routeUuid) {
        return routeRepository.findById(routeUuid);
    }

    @Override
    public void createRoute(RouteCreateRequest routeCreateRequest) {
        routeRepository.saveRoute(routeCreateRequest);
    }

    @Override
    public void editRoute(RouteUpdateRequest updateRouteRequest) {
        routeRepository.updateRoute(updateRouteRequest);
    }

    @Override
    public void deleteRoute(UUID routeUuid) {
        routeRepository.deleteRoute(routeUuid);
    }
}
