package edu.sabIA.rest.controller;

import edu.sabIA.data.dto.QuizRequest;
import edu.sabIA.data.service.GeminiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gemini")
public class GeminiController {
    
    private final GeminiService geminiService;
    
    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }
    
    @PostMapping("/generate")
    public ResponseEntity<String> generateContent(@RequestBody QuizRequest request) {
        String prompt = "Gere um quiz sobre o tema " + request.getTheme() + " com " + request.getNumberOfQuestions() + " perguntas.";
        try {
            String response = geminiService.generateContent(prompt);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Erro ao gerar conteúdo: " + e.getMessage());
        }
    }
    
}