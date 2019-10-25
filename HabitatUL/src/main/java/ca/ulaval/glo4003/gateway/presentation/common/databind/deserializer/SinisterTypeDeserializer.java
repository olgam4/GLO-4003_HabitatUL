package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer;

import ca.ulaval.glo4003.coverage.domain.claim.SinisterType;
import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidSinisterTypeError;
import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class SinisterTypeDeserializer extends JsonDeserializer<SinisterType> {
  @Override
  public SinisterType deserialize(
      JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    String value = node.textValue();
    return convertValueSafely(value);
  }

  private SinisterType convertValueSafely(String value) throws InvalidSinisterTypeError {
    try {
      return SinisterType.getEnum(value);
    } catch (InvalidArgumentException e) {
      throw new InvalidSinisterTypeError(value);
    }
  }
}
