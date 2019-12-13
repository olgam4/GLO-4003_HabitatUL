package ca.ulaval.glo4003.coverage.helper.form.personalproperty;

import ca.ulaval.glo4003.coverage.domain.form.personalproperty.Animals;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.Bicycle;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.PersonalProperty;
import ca.ulaval.glo4003.shared.domain.money.Amount;

import static ca.ulaval.glo4003.coverage.domain.form.personalproperty.Bicycle.UNFILLED_BICYCLE;
import static ca.ulaval.glo4003.coverage.helper.form.personalproperty.BicycleGenerator.createBicycle;
import static ca.ulaval.glo4003.coverage.helper.form.personalproperty.PersonalPropertyGenerator.createCoverageAmount;
import static ca.ulaval.glo4003.coverage.helper.premium.QuotePremiumInputGenerator.createAnimals;

public class PersonalPropertyBuilder {
  private final Amount DEFAULT_COVERAGE_AMOUNT = createCoverageAmount();
  private final Animals DEFAULT_ANIMALS = createAnimals();
  private final Bicycle DEFAULT_BICYCLE = createBicycle();

  private Amount coverageAmount = DEFAULT_COVERAGE_AMOUNT;
  private Animals animals = DEFAULT_ANIMALS;
  private Bicycle bicycle = DEFAULT_BICYCLE;

  private PersonalPropertyBuilder() {}

  public static PersonalPropertyBuilder aPersonalProperty() {
    return new PersonalPropertyBuilder();
  }

  public PersonalPropertyBuilder withCoverageAmount(Amount coverageAmount) {
    this.coverageAmount = coverageAmount;
    return this;
  }

  public PersonalPropertyBuilder withBicycle(Bicycle bicycle) {
    this.bicycle = bicycle;
    return this;
  }

  public PersonalPropertyBuilder withoutBicycle() {
    this.bicycle = UNFILLED_BICYCLE;
    return this;
  }

  public PersonalProperty build() {
    return new PersonalProperty(coverageAmount, animals, bicycle);
  }
}
