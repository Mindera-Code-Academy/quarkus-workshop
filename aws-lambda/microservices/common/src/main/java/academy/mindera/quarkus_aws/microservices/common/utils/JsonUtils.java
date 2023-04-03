package academy.mindera.quarkus_aws.microservices.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;


public class JsonUtils<T> {
    private final ObjectMapper objectMapper;

    private Class<T> clazz;

    public JsonUtils(Class<T> clazz) {
        objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
                .configure(
                        DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,
                        true)
                .findAndRegisterModules();
        
        this.clazz = clazz;
    }


    public T fromJson(String json) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String toJson(T t) {
        try {
            return objectMapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing object to JSON", e);
        }
    }


    public List<T> fromJsonList(String json) {
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public String toJsonList(List<T> t) {
        try {
            return objectMapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<T> loadFromJsonFile(String jsonFile) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(jsonFile);
        try {
            return objectMapper.readValue(inputStream, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void saveListToJsonFile(List<T> leaders, String jsonFile) {
        try {
            String jsonContent = toJsonList(leaders);
            File outputFile = new File(jsonFile);
            Files.write(outputFile.toPath(), jsonContent.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
