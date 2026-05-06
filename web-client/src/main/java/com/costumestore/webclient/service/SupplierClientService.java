package com.costumestore.webclient.service;

import com.costumestore.webclient.dto.SupplierDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SupplierClientService {

    private final RestTemplate restTemplate;

    @Value("${services.supplier.url}")
    private String supplierServiceUrl;

    public List<SupplierDto> getSupplierList() {
        try {
            String url = supplierServiceUrl + "/api/suppliers";
            return restTemplate.exchange(url, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<SupplierDto>>() {}).getBody();
        } catch (Exception e) {
            log.error("Error fetching suppliers: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}
