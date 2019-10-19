package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer;

import ca.ulaval.glo4003.coverage.domain.claim.LossCategory;
import ca.ulaval.glo4003.coverage.domain.claim.LossDeclarations;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

public class LossDeclarationsDeserializer extends JsonDeserializer<LossDeclarations> {
  @Override
  public LossDeclarations deserialize(
      JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
    ArrayNode nodes = jsonParser.getCodec().readTree(jsonParser);
    Map<LossCategory, Amount> lossesMap = new EnumMap<>(LossCategory.class);
    for (JsonNode node : nodes) {
      LossCategory lossCategory = getLossCategory(node);
      Amount actualLossAmount = lossesMap.getOrDefault(lossCategory, Amount.ZERO);
      Amount lossAmount = new Amount(node.get("amount").decimalValue());
      lossesMap.put(lossCategory, actualLossAmount.add(lossAmount));
    }
    return new LossDeclarations(lossesMap);
  }

  private LossCategory getLossCategory(JsonNode node) {
    String textValue = node.get("lossCategory").textValue();
    return LossCategory.getEnum(textValue);
  }
}
