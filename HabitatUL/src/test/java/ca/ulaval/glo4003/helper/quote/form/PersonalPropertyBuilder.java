package ca.ulaval.glo4003.helper.quote.form;

import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.Animals;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.PersonalProperty;

import static ca.ulaval.glo4003.helper.quote.form.PersonalPropertyGenerator.createAnimals;
import static ca.ulaval.glo4003.helper.quote.form.PersonalPropertyGenerator.createCoverageAmount;

public class PersonalPropertyBuilder {
  private static final Amount DEFAULT_COVERAGE_AMOUNT = createCoverageAmount();
  private static final Animals DEFAULT_ANIMALS = createAnimals();

  private Amount coverageAmount = DEFAULT_COVERAGE_AMOUNT;
  private Animals animals = DEFAULT_ANIMALS;

  private PersonalPropertyBuilder() {}

  public static PersonalPropertyBuilder aPersonalProperty() {
    return new PersonalPropertyBuilder();
  }

  public PersonalPropertyBuilder withCoverageAmount(Amount coverageAmount) {
    this.coverageAmount = coverageAmount;
    return this;
  }

  public PersonalProperty build() {
    return new PersonalProperty(coverageAmount, animals);
  }
}
