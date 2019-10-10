package ca.ulaval.glo4003.gateway.presentation.common.databind.serializer;

import ca.ulaval.glo4003.shared.domain.temporal.Date;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class DateSerializer extends JsonSerializer<Date> {
  @Override
  public void serialize(
      Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
      throws IOException {
    String dateString = date.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE);
    jsonGenerator.writeString(dateString);
  }
}
