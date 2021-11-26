package za.co.investec.assessment.clientmanagement.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class JsonUtils {
    private static final String TIMESTAMP_PATTERN = "yyyy-dd-MM' 'hh:mm:ss";
    private static final String DATE_PATTERN = "yyyy-dd-MM";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.setDefaultPropertyInclusion(JsonInclude.Include.NON_EMPTY);
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(LocalDateTime.class, new JsonSerializer<>() {
            @Override
            public void serialize(LocalDateTime dateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN);
                jsonGenerator.writeString(formatter.format(dateTime));
            }
        });
        simpleModule.addSerializer(LocalDate.class, new JsonSerializer<>() {
            @Override
            public void serialize(LocalDate dateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
                jsonGenerator.writeString(formatter.format(dateTime));
            }
        });

        OBJECT_MAPPER.registerModule(new Jdk8Module());
        OBJECT_MAPPER.registerModule(simpleModule);
    }

    private JsonUtils() {
    }

    public static String toString(Object object) {
        try {
            return OBJECT_MAPPER.writer().withDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return object.toString();
        }
    }
}
