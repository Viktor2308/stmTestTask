package com.github.Viktor2308.stmtesttask.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
/*
Билет – право проезда по определенному маршруту в определенную дату и место в транспортном средстве.
Атрибуты: маршрут, дата/время, номер места, цена.
 */
@Getter
@Setter
@Builder
public class Ticket {
    private UUID id;
    private Route route;
    private LocalDateTime dateTime;
    private String placeNumber;
    private BigDecimal price;
}
