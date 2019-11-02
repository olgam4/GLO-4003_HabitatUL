package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer;

import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidDateTimeError;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeDeserializer extends JsonDeserializer<DateTime> {
  private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  private String inputValue;

  @Override
  public DateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    inputValue = node.toString();
    enforceNodeType(node);
    return convertValueSafely(node);
  }

  private void enforceNodeType(JsonNode node) throws InvalidDateTimeError {
    if (!node.getNodeType().equals(JsonNodeType.STRING)) {
      throw new InvalidDateTimeError(inputValue);
    }
  }

  private DateTime convertValueSafely(JsonNode node) throws InvalidDateTimeError {
    try {
      return DateTime.from(LocalDateTime.parse(node.textValue(), formatter));
    } catch (DateTimeParseException e) {
      throw new InvalidDateTimeError(inputValue);
    }
  }
}
