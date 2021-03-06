package ca.ulaval.glo4003.coverage.domain.form.personalproperty;

import ca.ulaval.glo4003.shared.domain.ValueObject;

import java.util.EnumMap;
import java.util.Map;

public class Animals extends ValueObject {
  public static final Animals UNFILLED_ANIMALS = new Animals(new EnumMap<>(AnimalBreed.class));

  private final Map<AnimalBreed, Integer> collection;

  public Animals(Map<AnimalBreed, Integer> collection) {
    this.collection = collection;
  }

  public Map<AnimalBreed, Integer> getCollection() {
    return new EnumMap<>(collection);
  }
}
