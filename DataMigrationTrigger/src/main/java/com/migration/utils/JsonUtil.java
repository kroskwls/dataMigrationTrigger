package com.migration.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JsonUtil {
  @Autowired
  ObjectMapper objectMapper;

  public String toJson(Object obj) throws JsonProcessingException {
    return objectMapper.writeValueAsString(obj);
  }

  public <T> T fromJson(String jsonString) throws JsonMappingException, JsonProcessingException {
    TypeReference<T> typeReference = new TypeReference<T>() {};
    return objectMapper.readValue(jsonString, typeReference);
  }
}
