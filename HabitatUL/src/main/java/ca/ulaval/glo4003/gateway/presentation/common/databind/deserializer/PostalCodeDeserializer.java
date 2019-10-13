package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer;

import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidPostalCodeError;
import ca.ulaval.glo4003.shared.domain.InvalidArgumentException;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.PostalCode;
import ca.ulaval.glo4003.underwriting.infrastructure.quote.form.location.CanadianPostalCodeFormatter;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class PostalCodeDeserializer extends JsonDeserializer<PostalCode> {
  @Override
  public PostalCode deserialize(
      JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    String value = node.textValue();
    return convertValueSafely(value);
  }

  private PostalCode convertValueSafely(String value) throws InvalidPostalCodeError {
    try {
      return new PostalCode(value, new CanadianPostalCodeFormatter());
    } catch (InvalidArgumentException e) {
      throw new InvalidPostalCodeError(value);
    }
  }
}
