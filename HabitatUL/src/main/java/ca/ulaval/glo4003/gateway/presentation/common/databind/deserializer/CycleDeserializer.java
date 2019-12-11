package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer;

import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidCycleError;
import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;
import ca.ulaval.glo4003.shared.domain.identity.Cycle;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.io.IOException;

public class CycleDeserializer extends JsonDeserializer<Cycle> {
  private String inputValue;

  @Override
  public Cycle deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    inputValue = node.toString();
    enforceNodeType(node);
    return convertValueSafely(node);
  }

  private void enforceNodeType(JsonNode node) throws InvalidCycleError {
    if (!node.getNodeType().equals(JsonNodeType.STRING)) {
      throw new InvalidCycleError(inputValue);
    }
  }

  private Cycle convertValueSafely(JsonNode node) throws InvalidCycleError {
    try {
      return Cycle.getEnum(node.textValue());
    } catch (InvalidArgumentException e) {
      throw new InvalidCycleError(inputValue);
    }
  }
}
