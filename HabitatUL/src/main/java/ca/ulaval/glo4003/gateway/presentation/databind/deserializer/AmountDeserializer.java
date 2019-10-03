package ca.ulaval.glo4003.gateway.presentation.databind.deserializer;

import ca.ulaval.glo4003.shared.domain.Amount;
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
    return new Amount(BigDecimal.valueOf(node.floatValue()));
  }
}
