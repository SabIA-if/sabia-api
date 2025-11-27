package edu.sabIA.rest.controller;

import edu.sabIA.data.dto.request.CreateQuizRequest;
import edu.sabIA.data.service.QuizService;
import edu.sabIA.domain.models.Quiz;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("quiz")
public class QuizController {
    
    private final QuizService quizService;
    
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }
    
    @PostMapping("/create")
    public ResponseEntity<Quiz> generateContent(@RequestBody CreateQuizRequest request) { //TODO: Implementar CreateQuizResponse
        try {
            Quiz quiz = quizService.createQuiz(request);
            quizService.saveQuiz(quiz);
            
            return ResponseEntity.ok(quiz);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
}