package me.asite.attendance;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class AttendanceSerializer extends JsonSerializer<Attendance> {
    @Override
    public void serialize(Attendance attendance, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", attendance.getId());
        gen.writeStringField("attendanceDate", attendance.getAttendanceEndState().toString());
        gen.writeStringField("startTime", attendance.getStartTime().toString());
        gen.writeStringField("endTime", attendance.getEndTime().toString());
        gen.writeStringField("attendanceState", attendance.getAttendanceState().name());
        gen.writeStringField("attendanceEndState", attendance.getAttendanceEndState().name());
        gen.writeEndObject();

    }
}
