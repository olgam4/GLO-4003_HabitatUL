package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer;

import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidAmountError;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.io.IOException;
import java.math.BigDecimal;

public class AmountDeserializer extends JsonDeserializer<Amount> {
  private String inputValue;

  @Override
  public Amount deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    inputValue = node.toString();
    enforceNodeType(node);
    return convertValueSafely(node);
  }

  private void enforceNodeType(JsonNode node) throws InvalidAmountError {
    if (!node.getNodeType().equals(JsonNodeType.NUMBER)) {
      throw new InvalidAmountError(inputValue);
    }
  }

  private Amount convertValueSafely(JsonNode node) {
    return new Amount(BigDecimal.valueOf(node.floatValue()));
  }
}
