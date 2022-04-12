package com.rps.util.file;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.requireNonNull;

@UtilityClass
public class Reader {

    @SneakyThrows
    public static <T> T readJsonFrom(String fileLocation, Class<T> type) {
        String fileContent = readFromClassPath(fileLocation);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        T object = null;
        try {
            object = mapper.readValue(fileContent, type);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return object;
    }

   public static String readFromClassPath(String fileName) {
        try {
            return read(fileName);
        } catch (IOException e) {
             throw new IllegalArgumentException(e);
        }
    }

    public static String read(String fileName) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try (BufferedReader buffer = new BufferedReader(
                new InputStreamReader(requireNonNull(classloader.getResourceAsStream(fileName)), UTF_8))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }
}
