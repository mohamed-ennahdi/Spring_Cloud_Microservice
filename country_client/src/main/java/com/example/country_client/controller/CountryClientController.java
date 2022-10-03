package com.example.country_client.controller;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.countryserver.entity.Country;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class CountryClientController {
    private DiscoveryClient discoveryClient;

    @GetMapping("/")
    public List<Country> handleRequest() {
        List<ServiceInstance> instances = discoveryClient.getInstances("Country-Service");
        if (instances != null && !instances.isEmpty()) {
            ServiceInstance serviceInstance = instances.get(0);
            String url = serviceInstance.getUri().toString();
            url = url + "/countries";
            log.info("URL " + url);
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject(url, List.class);
        }
     return null;
    }
}
