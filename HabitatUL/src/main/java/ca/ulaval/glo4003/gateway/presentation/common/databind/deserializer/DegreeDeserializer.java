package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer;

import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidDegreeError;
import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;
import ca.ulaval.glo4003.shared.domain.identity.Degree;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.io.IOException;

public class DegreeDeserializer extends JsonDeserializer<Degree> {
  private String inputValue;

  @Override
  public Degree deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    inputValue = node.toString();
    enforceNodeType(node);
    return convertValueSafely(node);
  }

  private void enforceNodeType(JsonNode node) throws InvalidDegreeError {
    if (!node.getNodeType().equals(JsonNodeType.STRING)) {
      throw new InvalidDegreeError(inputValue);
    }
  }

  private Degree convertValueSafely(JsonNode node) throws InvalidDegreeError {
    try {
      return Degree.getEnum(node.textValue());
    } catch (InvalidArgumentException e) {
      throw new InvalidDegreeError(inputValue);
    }
  }
}
