package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidFloorError;
import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.Floor;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.FloorFormatter;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.io.IOException;

public class FloorDeserializer extends JsonDeserializer<Floor> {
  private FloorFormatter floorFormatter;

  public FloorDeserializer() {
    this.floorFormatter = ServiceLocator.resolve(FloorFormatter.class);
  }

  @Override
  public Floor deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    enforceNodeType(node);
    return convertValueSafely(node);
  }

  private void enforceNodeType(JsonNode node) throws InvalidFloorError {
    if (!node.getNodeType().equals(JsonNodeType.STRING)) {
      throw new InvalidFloorError(node.toString());
    }
  }

  private Floor convertValueSafely(JsonNode node) throws InvalidFloorError {
    String value = node.textValue();
    try {
      return new Floor(value, floorFormatter);
    } catch (InvalidArgumentException e) {
      throw new InvalidFloorError(value);
    }
  }
}
