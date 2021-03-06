package ca.ulaval.glo4003.coverage.domain.form.personalproperty;

import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public class PersonalProperty extends ValueObject {
  private Amount coverageAmount;
  private Animals animals;
  private Bicycle bicycle;

  public PersonalProperty(Amount coverageAmount, Animals animals, Bicycle bicycle) {
    this.coverageAmount = coverageAmount;
    this.animals = animals;
    this.bicycle = bicycle;
  }

  public Amount getCoverageAmount() {
    return coverageAmount;
  }

  public Animals getAnimals() {
    return animals;
  }

  public Bicycle getBicycle() {
    return bicycle;
  }
}
