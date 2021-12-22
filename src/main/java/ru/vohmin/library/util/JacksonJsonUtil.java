package ru.vohmin.library.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonSyntaxException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@Slf4j
@UtilityClass
public class JacksonJsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> void saveFromObjToJson(T objToSave, String fileName) {
        if (objToSave != null) {
            try (FileWriter writer = new FileWriter(fileName)) {
                mapper.writeValue(writer, objToSave);
                writer.flush();
            } catch (IOException e) {
                log.error("Error while trying to save in JSON file: \"{}\".", fileName, e);
            }
        }
    }

    public static <T> T loadFromJsonToClass(Class<T> objClass, String fileName) {
        if (new File(fileName).exists()) {
            try (FileReader reader = new FileReader(fileName)) {
                mapper.readValue(reader, objClass);
            } catch (IOException e) {
                log.error("Error while trying to load JSON from file: \"{}\".", fileName, e);
            } catch (JsonSyntaxException e) {
                log.error("Bad JSON in file: \"{}\".", fileName, e);
            }
        } else {
            log.info("\"{}\" doesn't exist", fileName);
        }
        return null;
    }

}
