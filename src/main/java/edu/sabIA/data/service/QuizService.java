package edu.sabIA.data.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Schema;
import com.google.genai.types.Type;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import edu.sabIA.data.dto.request.CreateQuizRequest;
import edu.sabIA.domain.models.Quiz;
import edu.sabIA.infra.repository.QuizRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service    
public class QuizService {
    
    private final Client geminiClient;
    private final QuizRepository quizRepository;

    public QuizService(Client geminiClient, QuizRepository quizRepository) {
        this.geminiClient = geminiClient;
        this.quizRepository = quizRepository;
    }

    @Value("${gemini.api.model}")
    private String model;

    // Schema da pergunta individual usando Map
    private Schema createQuestionSchema() {
        Map<String, Schema> properties = new HashMap<>();
        
        properties.put("statement", Schema.builder()
            .type(Type.Known.STRING)
            .description("Texto da pergunta")
            .build());
        
        properties.put("options", Schema.builder()
            .type(Type.Known.ARRAY)
            .description("Array com 4 alternativas")
            .items(Schema.builder()
                .type(Type.Known.STRING)
                .build())
            .build());
        
        properties.put("correctIndex", Schema.builder()
            .type(Type.Known.INTEGER)
            .description("Índice da alternativa correta (0-3)")
            .build());
        
        properties.put("justification", Schema.builder()
            .type(Type.Known.STRING)
            .description("Explicação da resposta correta")
            .build());
        
        return Schema.builder()
            .type(Type.Known.OBJECT)
            .properties(properties)
            .required(List.of("statement", "options", "correctIndex", "justification"))
            .build();
    }

    // Schema final do quiz
    private Schema createQuizSchema() {
        Map<String, Schema> properties = new HashMap<>();
        
        properties.put("questions", Schema.builder()
            .type(Type.Known.ARRAY)
            .description("Array de perguntas do quiz")
            .items(createQuestionSchema())
            .build());
        
        return Schema.builder()
            .type(Type.Known.OBJECT)
            .properties(properties)
            .required(List.of("questions"))
            .build();
    }

    public String generateContent(String prompt) {
        Schema quizSchema = createQuizSchema();

        GenerateContentConfig config = GenerateContentConfig.builder()
            .responseMimeType("application/json")
            .responseSchema(quizSchema)
            .build();
        
        GenerateContentResponse response = geminiClient.models.generateContent(
            model,
            prompt,
            config
        );
        
        return response.text();
    }

    public Quiz createQuiz(CreateQuizRequest request) {
        String prompt = "Gere um quiz sobre o tema " + request.theme() + " com " + request.numberOfQuestions() + " perguntas.";
        String quizJson = generateContent(prompt);
        UUID userId = UUID.fromString(request.userId()); //TODO: implementar validação de usuário antes de salvar
        return new Quiz(request.theme(), request.numberOfQuestions(), quizJson, 0, userId);
    }

    @Transactional
    public void saveQuiz(Quiz quiz) {
        quizRepository.save(quiz);
    }
}