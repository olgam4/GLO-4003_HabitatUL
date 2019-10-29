package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidZipCodeError;
import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.ZipCode;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.ZipCodeFormatter;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class ZipCodeDeserializer extends JsonDeserializer<ZipCode> {
  private ZipCodeFormatter zipCodeFormatter;

  public ZipCodeDeserializer() {
    this.zipCodeFormatter = ServiceLocator.resolve(ZipCodeFormatter.class);
  }

  @Override
  public ZipCode deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    String value = node.textValue();
    return convertValueSafely(value);
  }

  private ZipCode convertValueSafely(String value) throws InvalidZipCodeError {
    try {
      return new ZipCode(value, zipCodeFormatter);
    } catch (InvalidArgumentException e) {
      throw new InvalidZipCodeError(value);
    }
  }
}
