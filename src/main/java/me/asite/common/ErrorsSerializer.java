package me.asite.common;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.validation.Errors;

import java.io.IOException;

@JsonComponent
public class ErrorsSerializer extends JsonSerializer<Errors> {
    @Override
    public void serialize(Errors errors, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();

        errors.getFieldErrors().forEach(error -> {
            try {
                gen.writeStartObject();
                gen.writeStringField("field", error.getField());
                gen.writeStringField("objectName", error.getObjectName());
                gen.writeStringField("defaultMessage", error.getDefaultMessage());
                gen.writeStringField("code", error.getCode());
                Object rejectedValue = error.getRejectedValue();
                if (rejectedValue != null) {
                    gen.writeStringField("rejectValue", rejectedValue.toString());
                }
                gen.writeEndObject();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        gen.writeEndArray();
    }
}
