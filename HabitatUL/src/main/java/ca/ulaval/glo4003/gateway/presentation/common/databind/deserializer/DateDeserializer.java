package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer;

import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidDateError;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DateDeserializer extends JsonDeserializer<Date> {
  private String inputValue;

  @Override
  public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    inputValue = node.toString();
    enforceNodeType(node);
    return convertValueSafely(node);
  }

  private void enforceNodeType(JsonNode node) throws InvalidDateError {
    if (!node.getNodeType().equals(JsonNodeType.STRING)) {
      throw new InvalidDateError(inputValue);
    }
  }

  private Date convertValueSafely(JsonNode node) throws InvalidDateError {
    try {
      return Date.from(LocalDate.parse(node.textValue()));
    } catch (DateTimeParseException e) {
      throw new InvalidDateError(inputValue);
    }
  }
}
