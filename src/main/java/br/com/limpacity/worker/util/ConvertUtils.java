package br.com.limpacity.worker.util;

import br.com.limpacity.worker.exception.JsonMapperException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ConvertUtils {

    private ConvertUtils() {
    }

    public static <T> List<T> convertToList(final Set<T> set) {
        return new ArrayList<>(set);
    }

    public static String toJson(final Object object) {
        try {
            return buildObjectMapper().writeValueAsString(object);
        } catch (final JsonProcessingException e) {
            throw new JsonMapperException(e.getMessage());
        }
    }

    private static ObjectMapper buildObjectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
        return objectMapper;
    }
}
