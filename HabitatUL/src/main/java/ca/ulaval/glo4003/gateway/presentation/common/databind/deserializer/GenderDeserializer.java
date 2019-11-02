package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer;

import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidGenderError;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Gender;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.io.IOException;

public class GenderDeserializer extends JsonDeserializer<Gender> {
  private String inputValue;

  @Override
  public Gender deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    inputValue = node.toString();
    enforceNodeType(node);
    enforceNotBlank(node);
    return convertValueSafely(node);
  }

  private void enforceNodeType(JsonNode node) throws InvalidGenderError {
    if (!node.getNodeType().equals(JsonNodeType.STRING)) {
      throw new InvalidGenderError(inputValue);
    }
  }

  private void enforceNotBlank(JsonNode node) throws InvalidGenderError {
    if (node.textValue().isEmpty()) {
      throw new InvalidGenderError(inputValue);
    }
  }

  private Gender convertValueSafely(JsonNode node) {
    return Gender.getEnum(node.textValue());
  }
}
