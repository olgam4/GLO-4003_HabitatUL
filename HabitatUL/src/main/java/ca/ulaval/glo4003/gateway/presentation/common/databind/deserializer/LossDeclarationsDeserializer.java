package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer;

import ca.ulaval.glo4003.coverage.domain.claim.LossCategory;
import ca.ulaval.glo4003.coverage.domain.claim.LossDeclarations;
import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidLossDeclarationsError;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class LossDeclarationsDeserializer extends JsonDeserializer<LossDeclarations> {
  private String inputValue;

  @Override
  public LossDeclarations deserialize(
      JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
    JsonNode nodes = jsonParser.getCodec().readTree(jsonParser);
    inputValue = nodes.toString();
    enforceNodeType(nodes, JsonNodeType.ARRAY);
    return convertValueSafely(nodes);
  }

  private void enforceNodeType(JsonNode node, JsonNodeType nodeType)
      throws InvalidLossDeclarationsError {
    if (!node.getNodeType().equals(nodeType)) {
      throw new InvalidLossDeclarationsError(inputValue);
    }
  }

  private LossDeclarations convertValueSafely(JsonNode nodes) throws InvalidLossDeclarationsError {
    return new LossDeclarations(parseNodes(nodes));
  }

  private Map<LossCategory, Amount> parseNodes(JsonNode nodes) throws InvalidLossDeclarationsError {
    Map<LossCategory, Amount> lossDeclarationsMap = new EnumMap<>(LossCategory.class);
    for (JsonNode node : nodes) {
      enforceNodeType(node, JsonNodeType.OBJECT);
      parseNode(lossDeclarationsMap, node);
    }
    return lossDeclarationsMap;
  }

  private void parseNode(Map<LossCategory, Amount> lossesMap, JsonNode node)
      throws InvalidLossDeclarationsError {
    LossCategory lossCategory = getLossCategory(node);
    Amount currentLossAmount = lossesMap.getOrDefault(lossCategory, Amount.ZERO);
    Amount lossAmount = currentLossAmount.add(getLossAmount(node));
    lossesMap.put(lossCategory, lossAmount);
  }

  private LossCategory getLossCategory(JsonNode node) throws InvalidLossDeclarationsError {
    JsonNode lossCategoryNode =
        Optional.ofNullable(node.get("category"))
            .orElseThrow(() -> new InvalidLossDeclarationsError(inputValue));
    enforceNodeType(lossCategoryNode, JsonNodeType.STRING);
    enforceNotBlank(lossCategoryNode);
    String textValue = lossCategoryNode.textValue();
    return LossCategory.getEnum(textValue);
  }

  private void enforceNotBlank(JsonNode node) throws InvalidLossDeclarationsError {
    if (node.textValue().isEmpty()) {
      throw new InvalidLossDeclarationsError(inputValue);
    }
  }

  private Amount getLossAmount(JsonNode node) throws InvalidLossDeclarationsError {
    JsonNode lossAmountNode =
        Optional.ofNullable(node.get("amount"))
            .orElseThrow(() -> new InvalidLossDeclarationsError(inputValue));
    enforceNodeType(lossAmountNode, JsonNodeType.NUMBER);
    Amount lossAmount = new Amount(lossAmountNode.decimalValue());
    if (lossAmount.isSmallerThan(Amount.ZERO)) {
      throw new InvalidLossDeclarationsError(inputValue);
    }
    return lossAmount;
  }
}
