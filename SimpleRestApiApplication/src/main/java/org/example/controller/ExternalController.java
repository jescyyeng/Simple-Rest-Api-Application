package org.example.controller;

import org.example.service.ExternalApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/external")
public class ExternalController {

    @Autowired
    private ExternalApiService externalApiService;

    @GetMapping("/fetchData")
    public String fetchExternalData(@RequestParam String url) {

        return externalApiService.getExternalData(url);

    }
}
