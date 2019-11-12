package ca.ulaval.glo4003.coverage.domain.premium.formula.quote;

import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.coverage.domain.form.identity.UniversityProgram;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.Animals;
import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.identity.Gender;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public class QuotePremiumInput extends ValueObject {
  private final Gender namedInsuredGender;
  private final UniversityProgram namedInsuredUniversityProgram;
  private final Gender additionalInsuredGender;
  private final UniversityProgram additionalInsuredUniversityProgram;
  private final Animals animals;
  private final Amount bicyclePrice;
  private final CivilLiabilityLimit civilLiabilityLimit;

  public QuotePremiumInput(
      Gender namedInsuredGender,
      UniversityProgram namedInsuredUniversityProgram,
      Gender additionalInsuredGender,
      UniversityProgram additionalInsuredUniversityProgram,
      Animals animals,
      Amount bicyclePrice,
      CivilLiabilityLimit civilLiabilityLimit) {
    this.namedInsuredGender = namedInsuredGender;
    this.namedInsuredUniversityProgram = namedInsuredUniversityProgram;
    this.additionalInsuredGender = additionalInsuredGender;
    this.additionalInsuredUniversityProgram = additionalInsuredUniversityProgram;
    this.animals = animals;
    this.bicyclePrice = bicyclePrice;
    this.civilLiabilityLimit = civilLiabilityLimit;
  }

  public Gender getNamedInsuredGender() {
    return namedInsuredGender;
  }

  public UniversityProgram getNamedInsuredUniversityProgram() {
    return namedInsuredUniversityProgram;
  }

  public Gender getAdditionalInsuredGender() {
    return additionalInsuredGender;
  }

  public UniversityProgram getAdditionalInsuredUniversityProgram() {
    return additionalInsuredUniversityProgram;
  }

  public Animals getAnimals() {
    return animals;
  }

  public Amount getBicyclePrice() {
    return bicyclePrice;
  }

  public CivilLiabilityLimit getCivilLiabilityLimit() {
    return civilLiabilityLimit;
  }
}
