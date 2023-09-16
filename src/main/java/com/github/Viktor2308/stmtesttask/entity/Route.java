package com.github.Viktor2308.stmtesttask.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
/*
Маршрут – определенный путь движения.
Атрибуты: пункт отправления, пункт назначения, перевозчик, длительность в минутах.
 */
@Getter
@Setter
@Builder
public class Route {
    private UUID id;
    private String departurePoint;
    private String destinationPoint;
    private ShippingCompany company;
    private int routeDurationInMin;
}

