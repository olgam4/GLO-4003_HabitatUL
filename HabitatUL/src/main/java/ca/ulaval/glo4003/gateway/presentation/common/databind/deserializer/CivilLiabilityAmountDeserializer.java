package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer;

import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidCivilLiabilityAmountError;
import ca.ulaval.glo4003.shared.domain.InvalidArgumentException;
import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiabilityAmount;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class CivilLiabilityAmountDeserializer extends JsonDeserializer<CivilLiabilityAmount> {
  @Override
  public CivilLiabilityAmount deserialize(
      JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    String value = node.textValue();
    return convertValueSafely(value);
  }

  private CivilLiabilityAmount convertValueSafely(String value)
      throws InvalidCivilLiabilityAmountError {
    try {
      return CivilLiabilityAmount.getEnum(value);
    } catch (InvalidArgumentException e) {
      throw new InvalidCivilLiabilityAmountError(value);
    }
  }
}
