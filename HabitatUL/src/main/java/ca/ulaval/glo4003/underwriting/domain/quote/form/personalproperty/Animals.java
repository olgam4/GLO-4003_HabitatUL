package ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty;

import java.util.EnumMap;
import java.util.Map;

public class Animals {
  public static final Animals UNFILLED_ANIMALS = new Animals(new EnumMap<>(AnimalBreed.class));

  private Map<AnimalBreed, Integer> collection;

  public Animals(Map<AnimalBreed, Integer> collection) {
    this.collection = collection;
  }

  public Map<AnimalBreed, Integer> getCollection() {
    return new EnumMap<>(collection);
  }
}
