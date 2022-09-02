package com.wavemaker.ekisan.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wavemaker.ekisan.dto.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.stream.Collectors;

public class JsonUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);
    static ObjectMapper mapper = new ObjectMapper();
    public static String convertToString(Response resp) throws JsonProcessingException {
        return mapper.writeValueAsString(resp);
    }

    public static <T> T convertToObject(HttpServletRequest request, Class clazz) throws IOException {
        String body = request.getReader().lines().collect(Collectors.joining());
        return (T)mapper.readValue(body, clazz);
    }
}
