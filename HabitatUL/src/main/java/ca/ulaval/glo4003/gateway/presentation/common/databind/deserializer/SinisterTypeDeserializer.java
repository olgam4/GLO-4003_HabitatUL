package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer;

import ca.ulaval.glo4003.coverage.domain.claim.SinisterType;
import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidSinisterTypeError;
import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.io.IOException;

public class SinisterTypeDeserializer extends JsonDeserializer<SinisterType> {
  @Override
  public SinisterType deserialize(
      JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    enforceNodeType(node);
    return convertValueSafely(node);
  }

  private void enforceNodeType(JsonNode node) throws InvalidSinisterTypeError {
    if (!node.getNodeType().equals(JsonNodeType.STRING)) {
      throw new InvalidSinisterTypeError(node.toString());
    }
  }

  private SinisterType convertValueSafely(JsonNode node) throws InvalidSinisterTypeError {
    String value = node.textValue();
    try {
      return SinisterType.getEnum(value);
    } catch (InvalidArgumentException e) {
      throw new InvalidSinisterTypeError(value);
    }
  }
}
