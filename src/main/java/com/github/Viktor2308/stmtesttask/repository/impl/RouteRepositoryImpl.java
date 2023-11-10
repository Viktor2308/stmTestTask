package com.github.Viktor2308.stmtesttask.repository.impl;

import com.github.Viktor2308.stmtesttask.dto.routeDto.RouteCreateRequest;
import com.github.Viktor2308.stmtesttask.dto.routeDto.RouteUpdateRequest;
import com.github.Viktor2308.stmtesttask.entity.Route;
import com.github.Viktor2308.stmtesttask.entity.ShippingCompany;
import com.github.Viktor2308.stmtesttask.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RouteRepositoryImpl implements RouteRepository {

    private final JdbcTemplate jdbcTemplate;
    String findByIdSql = """
            SELECT r.id AS route_id, r.departure_point, r.destination_point,
            r.shipping_company_id, r.route_duration_in_min,
            c.id AS company_id, c.name AS company_name, c.phone AS company_phone
            FROM entity.route r
            JOIN entity.shipping_company c ON r.shipping_company_id = c.id
            WHERE r.id = ?
            """;
    private final String UPDATE_ROUTE_SQL = """
            UPDATE entity.route 
            SET departure_point = ?, destination_point = ?, shipping_company_id = ?, route_duration_in_min = ? 
            WHERE id = ?
            """;

    private final String DELETE_ROUTE_SQL =
            "DELETE FROM entity.route WHERE id = ?";

    @Override
    public Route findById(String routeUuid) {
        return jdbcTemplate.queryForObject(
                findByIdSql,
                new Object[]{routeUuid},
                (rs, rowNum) -> {
                    Route route = new Route();
                    route.setId(UUID.fromString(rs.getString("route_id")));
                    route.setDeparturePoint(rs.getString("departure_point"));
                    route.setDestinationPoint(rs.getString("destination_point"));

                    ShippingCompany company = new ShippingCompany();
                    company.setId(UUID.fromString(rs.getString("company_id")));
                    company.setName(rs.getString("company_name"));
                    company.setPhone(rs.getString("company_phone"));

                    route.setCompany(company);

                    route.setRouteDurationInMin(rs.getInt("route_duration_in_min"));
                    return route;
                }
        );
    }

    @Override
    public void saveRoute(RouteCreateRequest routeCreateRequest) {

    }

    @Override
    public void updateRoute(RouteUpdateRequest updateRouteRequest) {
        jdbcTemplate.update(UPDATE_ROUTE_SQL,
                updateRouteRequest.getDeparturePoint(),
                updateRouteRequest.getDestinationPoint(),
                updateRouteRequest.getCompanyUuid(),
                updateRouteRequest.getRouteDurationInMin(),
                updateRouteRequest.getId());
    }

    @Override
    public void deleteRoute(UUID routeUuid) {
        jdbcTemplate.update(DELETE_ROUTE_SQL,
                routeUuid.toString());
    }
}
