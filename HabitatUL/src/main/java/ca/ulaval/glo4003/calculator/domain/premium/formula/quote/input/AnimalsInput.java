package ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input;

import ca.ulaval.glo4003.shared.domain.ValueObject;

import java.util.Map;

public class AnimalsInput extends ValueObject {
  private final Map<AnimalBreedInput, Integer> collection;

  public AnimalsInput(Map<AnimalBreedInput, Integer> collection) {
    this.collection = collection;
  }

  public Map<AnimalBreedInput, Integer> getCollection() {
    return collection;
  }
}
