package com.example.aichatbot.service;

import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.MediaType;


@Service
public class OllamaChatModel {

     private final RestTemplate restTemplate = new RestTemplate();

    public String call(String prompt) {
        String url = "http://localhost:11434/api/generate";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = Map.of(
            "model", "mistral",
            "prompt", prompt,
            "stream", false
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            System.out.println("📤 Sending prompt to Ollama...");
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<Map<String, Object>>() {}
            );
            System.out.println("✅ Received response from Ollama.");

            Map<String, Object> body = response.getBody();

            if (body != null && body.containsKey("response")) {
                return body.get("response").toString().trim();
            } else {
                return "⚠️ Unexpected response format from Ollama.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "⚠️ Error calling Ollama: " + e.getMessage();
        }
    }

}
