package edu.sabIA.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {
    
    private final Client geminiClient;
    
    @Value("${gemini.api.model}")
    private String model;
    
    public GeminiService(Client geminiClient) {
        this.geminiClient = geminiClient;
    }
    
    public String generateContent(String prompt) {
        GenerateContentResponse response = geminiClient.models.generateContent(
            model,
            prompt,
            null
        );
        
        return response.text();
    }
}