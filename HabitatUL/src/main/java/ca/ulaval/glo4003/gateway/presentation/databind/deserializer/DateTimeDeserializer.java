package ca.ulaval.glo4003.gateway.presentation.databind.deserializer;

import ca.ulaval.glo4003.shared.domain.DateTime;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimeDeserializer extends JsonDeserializer<DateTime> {
  @Override
  public DateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    // TODO: should not override the time
    // TODO: need to create a date class
    return DateTime.from(LocalDateTime.of(LocalDate.parse(node.textValue()), LocalTime.MIN));
  }
}
