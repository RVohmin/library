package ru.vohmin.library.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.apache.commons.io.FileUtils;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Json {
    private static final ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows
    public static <P> P getObjectFromJson(String path, Class<P> clazz) {
        return mapper.readValue(getJson(path), clazz);
    }

    @SneakyThrows
    public static <P> P getObjectFromJsonWithCustomMapper(String path, Class<P> clazz) {
        return getCustomMapper().readValue(getJson(path), clazz);
    }

    private static ObjectMapper getCustomMapper() {
        LocalDateDeserializer deserializer = new LocalDateDeserializer(DateTimeFormatter.ISO_LOCAL_DATE);
        JavaTimeModule module = new JavaTimeModule();
        module.addDeserializer(LocalDate.class, deserializer);
        return Jackson2ObjectMapperBuilder.json()
                .modules(module)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
    }

    private static String getJson(String pathname) throws IOException {
        File file = new ClassPathResource(pathname).getFile();
        return FileUtils.readFileToString(file, UTF_8.toString());
    }
}
