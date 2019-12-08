package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer;

import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidLossRatioError;
import ca.ulaval.glo4003.insuring.domain.policy.lossratio.LossRatio;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.io.IOException;

public class LossRatioDeserializer extends JsonDeserializer<LossRatio> {
  private String inputValue;

  @Override
  public LossRatio deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    inputValue = node.toString();
    enforceNodeType(node);
    return convertValueSafely(node);
  }

  private void enforceNodeType(JsonNode node) throws InvalidLossRatioError {
    if (!node.getNodeType().equals(JsonNodeType.NUMBER)) {
      throw new InvalidLossRatioError(inputValue);
    }
  }

  private LossRatio convertValueSafely(JsonNode node) {
    return new LossRatio(node.floatValue());
  }
}
