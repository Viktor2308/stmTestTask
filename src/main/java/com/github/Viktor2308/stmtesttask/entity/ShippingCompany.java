package com.github.Viktor2308.stmtesttask.entity;

import lombok.*;

import java.util.UUID;
/**
Перевозчик – компания, осуществляющая перевозки.
Атрибуты: название, телефон.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShippingCompany {
    private UUID id;
    private String name;
    private String phone;
}
