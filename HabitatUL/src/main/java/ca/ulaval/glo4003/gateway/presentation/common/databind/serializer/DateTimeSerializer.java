package ca.ulaval.glo4003.gateway.presentation.common.databind.serializer;

import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class DateTimeSerializer extends JsonSerializer<DateTime> {
  private ZoneId zoneId;

  public DateTimeSerializer(ZoneId zoneId) {
    this.zoneId = zoneId;
  }

  @Override
  public void serialize(
      DateTime dateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
      throws IOException {
    jsonGenerator.writeString(
        ZonedDateTime.ofInstant(dateTime.getValue(), ZoneOffset.UTC, zoneId).toString());
  }
}
