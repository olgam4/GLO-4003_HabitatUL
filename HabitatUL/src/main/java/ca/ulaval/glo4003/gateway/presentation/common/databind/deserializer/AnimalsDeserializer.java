package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer;

import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidAnimalsError;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.AnimalBreed;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.Animals;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class AnimalsDeserializer extends JsonDeserializer<Animals> {
  @Override
  public Animals deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    JsonNode nodes = jsonParser.getCodec().readTree(jsonParser);
    if (!nodes.isArray()) throw new InvalidAnimalsError(nodes.asText());
    return new Animals(parseNodes(nodes));
  }

  private Map<AnimalBreed, Integer> parseNodes(JsonNode nodes) throws InvalidAnimalsError {
    Map<AnimalBreed, Integer> animalsMap = new EnumMap<>(AnimalBreed.class);
    for (JsonNode node : nodes) {
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
            .orElseThrow(() -> new InvalidAnimalsError(node.toString()));
    String breed = breedNode.textValue();
    if (breed == null || breed.isEmpty()) throw new InvalidAnimalsError(node.toString());
    return AnimalBreed.getEnum(breed);
  }

  private int getQuantity(JsonNode node) throws InvalidAnimalsError {
    JsonNode quantityNode =
        Optional.ofNullable(node.get("quantity"))
            .orElseThrow(() -> new InvalidAnimalsError(node.toString()));
    int quantity = quantityNode.intValue();
    if (quantity < 0) throw new InvalidAnimalsError(node.toString());
    return quantity;
  }
}
