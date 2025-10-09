package com.tracker.demo.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String generateInsights(String summaryJson) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + apiKey;

        // Request body
        Map<String, Object> request = Map.of(
            "contents", List.of(
                Map.of("parts", List.of(
                    Map.of("text", """
                        You are a smart personal finance assistant.
                        Analyze the user's expense data and return a JSON object with this structure:
                        {
                        "categoryAnalysis": { "Food": 1234, "Transport": 567, ... },
                        "anomalies": [ { "date": "...", "amount": ..., "reason": "..." } ],
                        "predictions": [ { "category": "...", "expectedNextMonth": ... } ],
                        "recommendations": [ "...", "...", "..." ],
                        "summary": "Short 2-line monthly summary."
                        }

                        Expense data:
                        """ + summaryJson)
                ))
            )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Map<String, Object>> response =
        restTemplate.exchange(
            url,
            HttpMethod.POST,
            entity,
            new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        // Null-safe check
        Map<String, Object> body = Optional.ofNullable(response.getBody())
                                           .orElse(Map.of("error", "Empty response from Gemini"));

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> candidates = (List<Map<String, Object>>) body.get("candidates");
        if (candidates == null || candidates.isEmpty()) {
            return "No insights received from AI.";
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");

        @SuppressWarnings("unchecked")
        List<Map<String, String>> parts = (List<Map<String, String>>) content.get("parts");

        return parts.get(0).getOrDefault("text", "No text returned by Gemini.");
    }
}
