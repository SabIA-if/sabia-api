package edu.sabIA.rest.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.sabIA.data.dto.request.CreateQuizRequest;
import edu.sabIA.data.dto.request.QuizzesRequest;
import edu.sabIA.data.dto.request.quiz.UpdateQuizProgressRequest;
import edu.sabIA.data.dto.response.quiz.QuizBasicInformationResponse;
import edu.sabIA.data.dto.response.quiz.QuizResponse;
import edu.sabIA.data.service.QuizService;
import edu.sabIA.domain.models.Quiz;



@RestController
@RequestMapping("quizzes")
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
    
    @GetMapping("/{id}")
    public ResponseEntity<QuizResponse> getQuiz(@PathVariable UUID id) {
        try{
            QuizResponse quiz = quizService.getQuiz(id);

            return ResponseEntity.ok(quiz);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping()
    public ResponseEntity<List<QuizBasicInformationResponse>> listQuizzes(@RequestBody QuizzesRequest request) {
        try {
            List<QuizBasicInformationResponse> quizInfo = quizService.listQuizzes(request.userId());

            return ResponseEntity.ok(quizInfo);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/update-progress")
    public ResponseEntity<?> updateQuizProgress(@RequestBody UpdateQuizProgressRequest request) {
        try {
            QuizResponse quiz = quizService.updateQuizProgress(request);
            return ResponseEntity.ok(quiz);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    
}