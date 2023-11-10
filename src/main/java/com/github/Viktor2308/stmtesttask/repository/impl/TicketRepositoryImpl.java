package com.github.Viktor2308.stmtesttask.repository.impl;

import com.github.Viktor2308.stmtesttask.dto.ticketDto.TicketUpdateRequest;
import com.github.Viktor2308.stmtesttask.entity.Route;
import com.github.Viktor2308.stmtesttask.entity.ShippingCompany;
import com.github.Viktor2308.stmtesttask.entity.Ticket;
import com.github.Viktor2308.stmtesttask.repository.TicketRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TicketRepositoryImpl implements TicketRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    private final String GET_USER_TICKET_SQL = """
            SELECT t.id, t.date_time, t.place_number, t.price, r.id 
            as route_id, r.departure_point, r.destination_point, r.route_duration_in_min,
            c.id as company_id, c.name, c.phone
            FROM entity.ticket t
            JOIN entity.route r ON t.route_id = r.id
            JOIN entity.shipping_company c ON r.shipping_company_id = c.id
            JOIN entity.users_ticket ut ON t.id = ut.ticket_id
            WHERE ut.users_id=?
            """;

    private final String SAVE_SQL =
            "INSERT INTO entity.ticket (route_id, date_time, place_number, price) VALUES (?,?,?,?)";

    private final String UPDATE_TICKET_SQL =
            "UPDATE entity.ticket SET route_id = ?, date_time = ?, place_number = ?, price = ? WHERE id = ?";
    private final String DELETE_TICKET_SQL =
            "DELETE FROM entity.ticket WHERE id = ?";

    @Override
    public Page<Ticket> findAllTickets(Integer page, Integer size, LocalDateTime startDate, LocalDateTime endDate, String departurePoint, String arrivalPoint, String shippingCompanyName) {
        int offset = (page - 1) * size;
        String findAllSql = """
                        SELECT t.id, t.date_time, t.place_number, t.price, r.id 
                        as route_id, r.departure_point, r.destination_point, r.route_duration_in_min,
                        c.id as company_id, c.name, c.phone
                        FROM entity.ticket t
                        JOIN entity.route r ON t.route_id = r.id
                        JOIN entity.shipping_company c ON r.shipping_company_id = c.id
                        WHERE t.available = true
                """;

        Map<String, Object> params = new HashMap<>();

        if (startDate != null) {
            findAllSql += " AND t.date_time >= :startDate";
            params.put("startDate", startDate);
        }
        if (endDate != null) {
            findAllSql += " AND t.date_time <= :endDate";
            params.put("endDate", endDate);
        }
        if (!StringUtils.isEmpty(departurePoint)) {
            findAllSql += " AND r.departure_point = :departurePoint";
            params.put("departurePoint", departurePoint);
        }
        if (!StringUtils.isEmpty(arrivalPoint)) {
            findAllSql += " AND r.destination_point = :arrivalPoint";
            params.put("arrivalPoint", arrivalPoint);
        }
        if (!StringUtils.isEmpty(shippingCompanyName)) {
            findAllSql += " AND c.name = :shippingCompanyName";
            params.put("shippingCompanyName", shippingCompanyName);
        }

        findAllSql += " ORDER BY t.date_time OFFSET :offset LIMIT :size";
        params.put("offset", offset);
        params.put("size", size);

        List<Ticket> results = namedParameterJdbcTemplate.query(findAllSql, params, new TicketRowMapper());

        String countQuery = "SELECT COUNT(*) FROM entity.ticket t JOIN entity.route r ON t.route_id = r.id JOIN entity.shipping_company c ON r.id = c.id WHERE t.available = true";
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String paramName = entry.getKey();
            Object paramValue = entry.getValue();
            countQuery = countQuery.replace(":" + paramName, paramValue.toString());
        }
        long total = namedParameterJdbcTemplate.queryForObject(countQuery, params, Long.class);

        return new PageImpl<>(results, PageRequest.of(page - 1, size), total);
    }

    @Transactional
    @Override
    public void buyTicket(UUID userUuid, UUID ticketUuid) {
        jdbcTemplate.update("UPDATE entity.ticket SET available=false where id=?",
                new Object[]{ticketUuid});
        jdbcTemplate.update("INSERT INTO entity.users_ticket (users_id, ticket_id) VALUES (?,?)",
                new Object[]{userUuid, ticketUuid});

    }

    @Override
    public List<Ticket> getUserTicket(UUID userUuid) {
        return jdbcTemplate.query(GET_USER_TICKET_SQL, new Object[]{userUuid}, new TicketRowMapper());
    }

    @Override
    public void saveTicket(Ticket ticket) {
        jdbcTemplate.update(SAVE_SQL,
                new Object[]{ticket.getRoute().getId(), ticket.getDateTime(), ticket.getPlaceNumber(), ticket.getPrice()}
        );
    }

    @Override
    public void updateTicket(TicketUpdateRequest updateTicketRequest) {
        jdbcTemplate.update(
                UPDATE_TICKET_SQL,
                updateTicketRequest.getRouteUuid(),
                updateTicketRequest.getDateTime(),
                updateTicketRequest.getPlaceNumber(),
                updateTicketRequest.getPrice(),
                updateTicketRequest.getId()
        );
    }

    @Override
    public void deleteTicket(UUID ticketUuid) {
        jdbcTemplate.update(DELETE_TICKET_SQL,
                ticketUuid.toString());
    }

    private static class TicketRowMapper implements RowMapper<Ticket> {
        @Override
        public Ticket mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Ticket ticket = new Ticket();
            ticket.setId(UUID.fromString(resultSet.getString("id")));
            ticket.setDateTime(resultSet.getTimestamp("date_time").toLocalDateTime());
            ticket.setPlaceNumber(resultSet.getString("place_number"));
            ticket.setPrice(resultSet.getBigDecimal("price"));

            Route route = new Route();
            route.setId(UUID.fromString(resultSet.getString("route_id")));
            route.setDeparturePoint(resultSet.getString("departure_point"));
            route.setDestinationPoint(resultSet.getString("destination_point"));
            route.setRouteDurationInMin(resultSet.getInt("route_duration_in_min"));

            ShippingCompany company = new ShippingCompany();
            company.setId(UUID.fromString(resultSet.getString("company_id")));
            company.setName(resultSet.getString("name"));
            company.setPhone(resultSet.getString("phone"));

            route.setCompany(company);
            ticket.setRoute(route);

            return ticket;
        }
    }
}
