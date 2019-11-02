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
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.io.IOException;

public class ZipCodeDeserializer extends JsonDeserializer<ZipCode> {
  private ZipCodeFormatter zipCodeFormatter;
  private String inputValue;

  public ZipCodeDeserializer() {
    this.zipCodeFormatter = ServiceLocator.resolve(ZipCodeFormatter.class);
  }

  @Override
  public ZipCode deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    inputValue = node.toString();
    enforceNodeType(node);
    return convertValueSafely(node);
  }

  private void enforceNodeType(JsonNode node) throws InvalidZipCodeError {
    if (!node.getNodeType().equals(JsonNodeType.STRING)) {
      throw new InvalidZipCodeError(inputValue);
    }
  }

  private ZipCode convertValueSafely(JsonNode node) throws InvalidZipCodeError {
    try {
      return new ZipCode(node.textValue(), zipCodeFormatter);
    } catch (InvalidArgumentException e) {
      throw new InvalidZipCodeError(inputValue);
    }
  }
}
