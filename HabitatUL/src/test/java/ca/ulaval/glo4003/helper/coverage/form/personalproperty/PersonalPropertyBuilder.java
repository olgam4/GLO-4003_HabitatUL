package ca.ulaval.glo4003.helper.coverage.form.personalproperty;

import ca.ulaval.glo4003.coverage.domain.form.personalproperty.Animals;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.Bike;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.PersonalProperty;
import ca.ulaval.glo4003.shared.domain.money.Amount;

import static ca.ulaval.glo4003.helper.coverage.form.personalproperty.BikeGenerator.createBike;
import static ca.ulaval.glo4003.helper.coverage.form.personalproperty.PersonalPropertyGenerator.createCoverageAmount;
import static ca.ulaval.glo4003.helper.coverage.premium.QuotePremiumInputGenerator.createAnimals;

public class PersonalPropertyBuilder {
  private static final Amount DEFAULT_COVERAGE_AMOUNT = createCoverageAmount();
  private static final Animals DEFAULT_ANIMALS = createAnimals();
  private static final Bike DEFAULT_BIKE = createBike();

  private Amount coverageAmount = DEFAULT_COVERAGE_AMOUNT;
  private Animals animals = DEFAULT_ANIMALS;
  private Bike bike = DEFAULT_BIKE;

  private PersonalPropertyBuilder() {}

  public static PersonalPropertyBuilder aPersonalProperty() {
    return new PersonalPropertyBuilder();
  }

  public PersonalPropertyBuilder withCoverageAmount(Amount coverageAmount) {
    this.coverageAmount = coverageAmount;
    return this;
  }

  public PersonalPropertyBuilder withBike(Bike bike) {
    this.bike = bike;
    return this;
  }

  public PersonalProperty build() {
    return new PersonalProperty(coverageAmount, animals, bike);
  }
}
