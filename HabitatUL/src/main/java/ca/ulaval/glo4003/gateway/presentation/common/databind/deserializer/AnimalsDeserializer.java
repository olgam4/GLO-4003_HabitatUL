package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer;

import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.AnimalBreed;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.Animals;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

public class AnimalsDeserializer extends JsonDeserializer<Animals> {
  @Override
  public Animals deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    ArrayNode nodes = jsonParser.getCodec().readTree(jsonParser);
    Map<AnimalBreed, Integer> animalsMap = new EnumMap<>(AnimalBreed.class);
    for (JsonNode node : nodes) {
      AnimalBreed breed = getBreed(node);
      int currentQuantity = animalsMap.getOrDefault(breed, 0);
      int quantity = currentQuantity + node.get("quantity").intValue();
      animalsMap.put(breed, quantity);
    }
    return new Animals(animalsMap);
  }

  private AnimalBreed getBreed(JsonNode node) {
    String textValue = node.get("breed").textValue();
    return AnimalBreed.getEnum(textValue);
  }
}
