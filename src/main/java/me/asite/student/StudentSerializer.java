package me.asite.student;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class StudentSerializer extends JsonSerializer<Student> {

    @Override
    public void serialize(Student student, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", student.getId());
        gen.writeEndObject();
    }
}
