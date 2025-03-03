package com.shop_hub.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObjectMapperUtils {

    public static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T readValue(String json, Class<T> clazz){
        try {
            return objectMapper.readValue(json, clazz);
        }catch (Exception e) {
            log.error("[ObjectMapperUtils][readValue] {}", e.getMessage());
            return null;
        }
    }

    public static<T> T readValue(String json, TypeReference<T> clazz) {
        try{
            return objectMapper.readValue(json, clazz);
        }catch (Exception e){
            log.error("[ObjectMapperUtils][readValue] {}", e.getMessage());
            return null;
        }
    }

}
