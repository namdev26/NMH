package com.costumestore.webclient.service;

import com.costumestore.webclient.dto.CostumeDto;
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
public class CostumeClientService {

    private final RestTemplate restTemplate;

    @Value("${services.costume.url}")
    private String costumeServiceUrl;

    public List<CostumeDto> getCostumeListToImport() {
        try {
            String url = costumeServiceUrl + "/api/costumes/import-list";
            return restTemplate.exchange(url, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<CostumeDto>>() {}).getBody();
        } catch (Exception e) {
            log.error("Error fetching costume import list: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}
