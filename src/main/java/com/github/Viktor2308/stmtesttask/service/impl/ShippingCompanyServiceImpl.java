package com.github.Viktor2308.stmtesttask.service.impl;

import com.github.Viktor2308.stmtesttask.dto.shippingCompanyDto.ShippingCompanyCreateRequest;
import com.github.Viktor2308.stmtesttask.dto.shippingCompanyDto.ShippingCompanyUpdateRequest;
import com.github.Viktor2308.stmtesttask.service.ShippingCompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@RequiredArgsConstructor
public class ShippingCompanyServiceImpl implements ShippingCompanyService {

    @Override
    public void createShippingCompany(ShippingCompanyCreateRequest shippingCompanyCreateRequest) {

    }

    @Override
    public void editShippingCompany(ShippingCompanyUpdateRequest shippingCompanyUpdateRequest) {

    }

    @Override
    public void deleteShippingCompany(UUID shippingCompanyUuid) {

    }
}
