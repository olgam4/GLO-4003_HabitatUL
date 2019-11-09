package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer;

import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidYearError;
import ca.ulaval.glo4003.shared.domain.temporal.Year;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.io.IOException;
import java.time.format.DateTimeParseException;

public class YearDeserializer extends JsonDeserializer<Year> {
  private String inputValue;

  @Override
  public Year deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    inputValue = node.toString();
    enforceNodeType(node);
    return convertValueSafely(node);
  }

  private void enforceNodeType(JsonNode node) throws InvalidYearError {
    if (!node.getNodeType().equals(JsonNodeType.STRING)) {
      throw new InvalidYearError(inputValue);
    }
  }

  private Year convertValueSafely(JsonNode node) throws InvalidYearError {
    try {
      return Year.from(java.time.Year.parse(node.textValue()));
    } catch (DateTimeParseException e) {
      throw new InvalidYearError(inputValue);
    }
  }
}
