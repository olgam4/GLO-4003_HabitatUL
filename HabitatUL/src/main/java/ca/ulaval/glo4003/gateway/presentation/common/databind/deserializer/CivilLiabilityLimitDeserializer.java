package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer;

import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidCivilLiabilityLimitError;
import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;
import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiabilityLimit;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.Optional;

public class CivilLiabilityLimitDeserializer extends JsonDeserializer<CivilLiabilityLimit> {
  @Override
  public CivilLiabilityLimit deserialize(
      JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    String value =
        Optional.ofNullable(node.textValue())
            .orElseThrow(() -> new InvalidCivilLiabilityLimitError(node.toString()));
    return convertValueSafely(value);
  }

  private CivilLiabilityLimit convertValueSafely(String value)
      throws InvalidCivilLiabilityLimitError {
    try {
      return CivilLiabilityLimit.getEnum(value);
    } catch (InvalidArgumentException e) {
      throw new InvalidCivilLiabilityLimitError(value);
    }
  }
}
