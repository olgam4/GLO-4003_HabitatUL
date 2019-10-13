package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer;

import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidFloorError;
import ca.ulaval.glo4003.shared.domain.InvalidArgumentException;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.Floor;
import ca.ulaval.glo4003.underwriting.infrastructure.quote.form.location.UsCanadianConventionFloorFormatter;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class FloorDeserializer extends JsonDeserializer<Floor> {
  @Override
  public Floor deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    String value = node.textValue();
    return convertValueSafely(value);
  }

  private Floor convertValueSafely(String value) throws InvalidFloorError {
    try {
      return new Floor(value, new UsCanadianConventionFloorFormatter());
    } catch (InvalidArgumentException e) {
      throw new InvalidFloorError(value);
    }
  }
}
