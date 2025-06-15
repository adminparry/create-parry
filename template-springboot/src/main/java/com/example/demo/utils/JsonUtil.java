package com.example.demo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;


public class JsonUtil {
    private static ObjectMapper objectMapper;

    public JsonUtil() {
    }
    static  {
        objectMapper = new ObjectMapper();
    }

    public class JsonException extends RuntimeException {
        public JsonException(String message) {
            super(message);
        }

        public JsonException(String message, Throwable cause) {
            super(message, cause);
        }

        public JsonException(String messageTemplate, Object... params) {
            super(String.format(messageTemplate, params));
        }
    }


    public String toJsonString(Object object) {
        if (object == null) {
            return "";
        } else {
            try {
                return this.objectMapper.writeValueAsString(object);
            } catch (JsonProcessingException var4) {
                JsonProcessingException e = var4;
                String message = String.format("%s to json string error: %s", object.getClass().getSimpleName(), e.getMessage());
                throw new JsonException(message, e);
            }
        }
    }

    public <T> T toBean(String json, Class<T> clazz) {
        if (!this.isEmpty(json) && clazz != null) {
            try {
                return this.objectMapper.readValue(json, clazz);
            } catch (JsonProcessingException var5) {
                JsonProcessingException e = var5;
                String message = String.format("Json to java pojo error: %s", e.getMessage());
                throw new JsonException(message, e);
            }
        } else {
            return null;
        }
    }

    public Map<String, Object> toMap(String json) {
        if (this.isEmpty(json)) {
            return Collections.emptyMap();
        } else {
            try {
                return (Map)this.objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {
                });
            } catch (JsonProcessingException var3) {
                JsonProcessingException e = var3;
                throw new JsonException(e.getMessage(), e);
            }
        }
    }

    public <T> List<T> toList(String json, Class<T> clazz) {
        if (!this.isEmpty(json) && clazz != null) {
            try {
                JavaType javaType = this.objectMapper.getTypeFactory().constructParametricType(List.class, new Class[]{clazz});
                return (List)this.objectMapper.readValue(json, javaType);
            } catch (JsonProcessingException var4) {
                JsonProcessingException e = var4;
                throw new JsonException(e.getMessage(), e);
            }
        } else {
            return Collections.emptyList();
        }
    }

    public JsonNode toJsonNode(String json) {
        if (this.isEmpty(json)) {
            return null;
        } else {
            try {
                return this.objectMapper.readTree(json);
            } catch (JsonProcessingException var3) {
                JsonProcessingException e = var3;
                throw new JsonException(e.getMessage(), e);
            }
        }
    }

    public <T> T convertValue(Object obj, Class<T> clazz) {
        return obj != null && clazz != null ? this.objectMapper.convertValue(obj, clazz) : null;
    }

    private boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public ObjectMapper getObjectMapper() {
        return this.objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
