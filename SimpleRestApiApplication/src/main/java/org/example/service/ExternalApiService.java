package org.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalApiService {

    private final RestTemplate restTemplate;

    @Autowired
    public ExternalApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getExternalData(String url) {
        try {
            return restTemplate.getForObject(url, String.class);
        } catch (HttpClientErrorException ex) {
            if (ex.getResponseHeaders().getContentType().includes(MediaType.TEXT_HTML)) {
                String errorHtml = ex.getResponseBodyAsString();
                System.err.println("Error HTML: " + errorHtml);
                return null;
            }
            throw ex;
        }
    }
}
