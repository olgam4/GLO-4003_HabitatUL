package ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty;

import ca.ulaval.glo4003.calculator.domain.premium.formula.input.Animals;
import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public class PersonalProperty extends ValueObject {
  private Amount coverageAmount;
  private Animals animals;
  private Bike bike;

  public PersonalProperty(Amount coverageAmount, Animals animals, Bike bike) {
    this.coverageAmount = coverageAmount;
    this.animals = animals;
    this.bike = bike;
  }

  public Amount getCoverageAmount() {
    return coverageAmount;
  }

  public Animals getAnimals() {
    return animals;
  }

  public Bike getBike() {
    return bike;
  }
}
