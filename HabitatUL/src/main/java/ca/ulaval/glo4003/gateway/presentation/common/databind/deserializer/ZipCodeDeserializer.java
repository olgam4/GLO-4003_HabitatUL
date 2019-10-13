package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer;

import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidZipCodeError;
import ca.ulaval.glo4003.shared.domain.InvalidArgumentException;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.ZipCode;
import ca.ulaval.glo4003.underwriting.infrastructure.quote.form.location.CanadianZipCodeFormatter;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class ZipCodeDeserializer extends JsonDeserializer<ZipCode> {
  @Override
  public ZipCode deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    String value = node.textValue();
    return convertValueSafely(value);
  }

  private ZipCode convertValueSafely(String value) throws InvalidZipCodeError {
    try {
      return new ZipCode(value, new CanadianZipCodeFormatter());
    } catch (InvalidArgumentException e) {
      throw new InvalidZipCodeError(value);
    }
  }
}
