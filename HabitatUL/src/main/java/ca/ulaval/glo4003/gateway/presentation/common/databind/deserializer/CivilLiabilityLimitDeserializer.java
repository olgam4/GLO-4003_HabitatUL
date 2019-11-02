package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer;

import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidCivilLiabilityLimitError;
import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;
import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiabilityLimit;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.io.IOException;

public class CivilLiabilityLimitDeserializer extends JsonDeserializer<CivilLiabilityLimit> {
  @Override
  public CivilLiabilityLimit deserialize(
      JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    enforceNodeType(node);
    return convertValueSafely(node);
  }

  private void enforceNodeType(JsonNode node) throws InvalidCivilLiabilityLimitError {
    if (!node.getNodeType().equals(JsonNodeType.STRING)) {
      throw new InvalidCivilLiabilityLimitError(node.toString());
    }
  }

  private CivilLiabilityLimit convertValueSafely(JsonNode node)
      throws InvalidCivilLiabilityLimitError {
    String value = node.textValue();
    try {
      return CivilLiabilityLimit.getEnum(value);
    } catch (InvalidArgumentException e) {
      throw new InvalidCivilLiabilityLimitError(value);
    }
  }
}
