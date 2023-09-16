package com.github.Viktor2308.stmtesttask.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
/*
Перевозчик – компания, осуществляющая перевозки.
Атрибуты: название, телефон.
 */
@Getter
@Setter
@Builder
public class ShippingCompany {
    private UUID id;
    private String name;
    private String phone;
}
