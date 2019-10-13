package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer;

import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidPreventionSystemError;
import ca.ulaval.glo4003.shared.domain.InvalidArgumentException;
import ca.ulaval.glo4003.underwriting.domain.quote.form.building.PreventionSystem;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class PreventionSystemDeserializer extends JsonDeserializer<PreventionSystem> {
  @Override
  public PreventionSystem deserialize(
      JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    String value = node.textValue();
    return convertValueSafely(value);
  }

  private PreventionSystem convertValueSafely(String value) throws InvalidPreventionSystemError {
    try {
      return PreventionSystem.getEnum(value);
    } catch (InvalidArgumentException e) {
      throw new InvalidPreventionSystemError(value);
    }
  }
}
