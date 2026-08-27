package edu.sabIA.data.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Schema;
import com.google.genai.types.Type;

import edu.sabIA.data.contracts.request.CreateQuizRequest;
import edu.sabIA.data.contracts.request.quiz.UpdateQuizProgressRequest;
import edu.sabIA.data.contracts.response.quiz.QuizBasicInformationResponse;
import edu.sabIA.data.contracts.response.quiz.QuizResponse;
import edu.sabIA.domain.models.Quiz;
import edu.sabIA.infra.repository.QuizRepository;
import edu.sabIA.rest.utils.Utils;

@Service    
public class QuizService {
    
    private final Utils utils;
    private final Client geminiClient;
    private final QuizRepository quizRepository;

    public QuizService(Client geminiClient, QuizRepository quizRepository, Utils utils) {
        this.geminiClient = geminiClient;
        this.quizRepository = quizRepository;
        this.utils = utils;
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

    public List<QuizBasicInformationResponse> listQuizzes(UUID userId){
        Optional<List<Quiz>> consult = quizRepository.findByUserId(userId);
        if(consult.isEmpty()){
            throw new RuntimeException("Quiz not found");
        } 

        List<QuizBasicInformationResponse> entities = new ArrayList<>();
        for (Quiz quiz : consult.get()) {
            QuizBasicInformationResponse response = new QuizBasicInformationResponse(
                quiz.getId(),
                quiz.getTheme(),
                quiz.getTopics(),
                quiz.getCurrentQuestion(),
                quiz.getNumberOfQuestions(),
                quiz.getScore()
            );
            entities.add(response);
        }

        return entities;
    }

    public QuizResponse getQuiz(UUID id){
        Optional<Quiz> consult = quizRepository.findById(id);
        if(consult.isEmpty()){
            throw new RuntimeException("Quiz not found");
        } 

        Quiz entity = consult.get();

        Object quizJson = utils.convertStrToJsonObject(entity.getQuizJson());

        QuizResponse response = new QuizResponse(
            entity.getId(),
            entity.getTheme(),
            entity.getTopics(),
            entity.getNumberOfQuestions(),
            quizJson,
            entity.getCurrentQuestion(),
            entity.getScore(),
            entity.getUserId(),
            entity.isFinished()
        );

        return response;
    }

    public QuizResponse updateQuizProgress(UpdateQuizProgressRequest request){
        Optional<Quiz> consult = quizRepository.findById(request.id());
        if(consult.isEmpty()){
            throw new RuntimeException("O ID do quiz nao está no banco");
        } 
        Quiz entity = consult.get();

        if (entity.isFinished()) {
            throw new RuntimeException("Este quiz já foi finalizado");
        }

        if(request.hasScored()){
            entity.setScore(entity.getScore() + 1);
        }

        entity.setCurrentQuestion(entity.getCurrentQuestion() + 1);

        if(entity.getCurrentQuestion() == entity.getNumberOfQuestions()){
            entity.setFinished(true);
        }

        quizRepository.save(entity);

        Object quizJson = utils.convertStrToJsonObject(entity.getQuizJson());

        QuizResponse response = new QuizResponse(
            entity.getId(),
            entity.getTheme(),
            entity.getTopics(),
            entity.getNumberOfQuestions(),
            quizJson,
            entity.getCurrentQuestion(),
            entity.getScore(),
            entity.getUserId(),
            entity.isFinished()
        );

        return response;
    }
        
}