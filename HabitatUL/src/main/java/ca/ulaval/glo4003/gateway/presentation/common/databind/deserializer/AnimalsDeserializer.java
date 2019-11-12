package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer;

import ca.ulaval.glo4003.calculator.domain.input.AnimalBreed;
import ca.ulaval.glo4003.calculator.domain.input.Animals;
import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidAnimalsError;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class AnimalsDeserializer extends JsonDeserializer<Animals> {
  private String inputValue;

  @Override
  public Animals deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    JsonNode nodes = jsonParser.getCodec().readTree(jsonParser);
    inputValue = nodes.toString();
    enforceNodeType(nodes, JsonNodeType.ARRAY);
    return convertValueSafely(nodes);
  }

  private void enforceNodeType(JsonNode node, JsonNodeType nodeType) throws InvalidAnimalsError {
    if (!node.getNodeType().equals(nodeType)) {
      throw new InvalidAnimalsError(inputValue);
    }
  }

  private Animals convertValueSafely(JsonNode nodes) throws InvalidAnimalsError {
    return new Animals(parseNodes(nodes));
  }

  private Map<AnimalBreed, Integer> parseNodes(JsonNode nodes) throws InvalidAnimalsError {
    Map<AnimalBreed, Integer> animalsMap = new EnumMap<>(AnimalBreed.class);
    for (JsonNode node : nodes) {
      enforceNodeType(node, JsonNodeType.OBJECT);
      parseNode(animalsMap, node);
    }
    return animalsMap;
  }

  private void parseNode(Map<AnimalBreed, Integer> animalsMap, JsonNode node)
      throws InvalidAnimalsError {
    AnimalBreed breed = getBreed(node);
    int currentQuantity = animalsMap.getOrDefault(breed, 0);
    int quantity = currentQuantity + getQuantity(node);
    animalsMap.put(breed, quantity);
  }

  private AnimalBreed getBreed(JsonNode node) throws InvalidAnimalsError {
    JsonNode breedNode =
        Optional.ofNullable(node.get("breed"))
            .orElseThrow(() -> new InvalidAnimalsError(inputValue));
    enforceNodeType(breedNode, JsonNodeType.STRING);
    String breed = breedNode.textValue();
    if (breed.isEmpty()) throw new InvalidAnimalsError(inputValue);
    return AnimalBreed.getEnum(breed);
  }

  private int getQuantity(JsonNode node) throws InvalidAnimalsError {
    JsonNode quantityNode =
        Optional.ofNullable(node.get("quantity"))
            .orElseThrow(() -> new InvalidAnimalsError(inputValue));
    enforceNodeType(quantityNode, JsonNodeType.NUMBER);
    int quantity = quantityNode.intValue();
    if (quantity < 0) throw new InvalidAnimalsError(inputValue);
    return quantity;
  }
}
