package com.github.Viktor2308.stmtesttask.entity;

import lombok.*;

import java.util.UUID;
/**
Маршрут – определенный путь движения.
Атрибуты: пункт отправления, пункт назначения, перевозчик, длительность в минутах.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Route {
    private UUID id;
    private String departurePoint;
    private String destinationPoint;
    private ShippingCompany company;
    private int routeDurationInMin;
}

