package org.example.microservicemusic.mapper;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ResponseMapper {

    public static Map<String, Object> mapResponse(int status, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status);
        response.put("message", message);
        return response;
    }
}
