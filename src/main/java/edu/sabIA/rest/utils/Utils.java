package edu.sabIA.rest.utils;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Utils {

    private final ObjectMapper mapper = new ObjectMapper();

    public JsonNode convertStrToJson(String string) {

        String stringToConvert = string.replace("\n", "");
        try {
            return mapper.readTree(stringToConvert);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter String para JSON", e);
        }
    }
}

