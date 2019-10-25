package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer;

import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidAmountError;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.math.BigDecimal;

public class AmountDeserializer extends JsonDeserializer<Amount> {
  @Override
  public Amount deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    float value = node.floatValue();
    if (value < 0 || !node.isNumber()) throw new InvalidAmountError(node.toString());

    return new Amount(BigDecimal.valueOf(value));
  }
}
