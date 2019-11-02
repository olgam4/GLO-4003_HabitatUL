package ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty;

import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public class PersonalProperty extends ValueObject {
  private Amount coverageAmount;
  private Animals animals;

  public PersonalProperty(Amount coverageAmount, Animals animals) {
    this.coverageAmount = coverageAmount;
    this.animals = animals;
  }

  public Amount getCoverageAmount() {
    return coverageAmount;
  }

  public Animals getAnimals() {
    return animals;
  }
}
