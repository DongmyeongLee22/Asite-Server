package me.asite.attendance;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;

public class AttendanceListSerializer extends JsonSerializer<List<Attendance>> {
    @Override
    public void serialize(List<Attendance> attendanceList, JsonGenerator gen, SerializerProvider serializers) throws IOException {

        gen.writeStartArray();

        for (Attendance attendance : attendanceList) {
            gen.writeStartObject();
            gen.writeStringField("attendanceDate", attendance.getAttendanceDate().toString());
            gen.writeStringField("startTime", attendance.getStartTime().toString());
            gen.writeStringField("endTime", attendance.getEndTime().toString());
            gen.writeStringField("attendanceState", attendance.getAttendanceState().name());
            gen.writeStringField("attendanceEndState", attendance.getAttendanceEndState().name());
            gen.writeEndObject();
        }

        gen.writeEndArray();

    }
}
