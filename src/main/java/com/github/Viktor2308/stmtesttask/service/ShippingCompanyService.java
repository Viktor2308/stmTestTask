package com.github.Viktor2308.stmtesttask.service;

import com.github.Viktor2308.stmtesttask.dto.shippingCompanyDto.ShippingCompanyCreateRequest;
import com.github.Viktor2308.stmtesttask.dto.shippingCompanyDto.ShippingCompanyUpdateRequest;

import java.util.UUID;

public interface ShippingCompanyService {
    void createShippingCompany(ShippingCompanyCreateRequest shippingCompanyCreateRequest);

    void editShippingCompany(ShippingCompanyUpdateRequest shippingCompanyUpdateRequest);

    void deleteShippingCompany(UUID shippingCompanyUuid);
}
