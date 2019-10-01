package ca.ulaval.glo4003.gateway.presentation.databind.serializer;

import ca.ulaval.glo4003.shared.domain.DateTime;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class DateTimeSerializer extends JsonSerializer<DateTime> {
  @Override
  public void serialize(
      DateTime dateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
      throws IOException {
    long value = dateTime.toUnixEpochTimestamp();
    jsonGenerator.writeNumber(value);
  }
}
