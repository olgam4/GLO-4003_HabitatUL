package ca.ulaval.glo4003.calculator.domain.premium.formula.quote;

import ca.ulaval.glo4003.calculator.domain.premium.formula.input.Animals;
import ca.ulaval.glo4003.calculator.domain.premium.formula.input.CivilLiabilityLimit;
import ca.ulaval.glo4003.calculator.domain.premium.formula.input.UniversityProgram;
import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.identity.Gender;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public class QuotePremiumInput extends ValueObject {
  private final Gender namedInsuredGender;
  private final UniversityProgram namedInsuredUniversityProgram;
  private final Gender additionalInsuredGender;
  private final UniversityProgram additionalInsuredUniversityProgram;
  private final Animals animals;
  private final Amount bikePrice;
  private final CivilLiabilityLimit civilLiabilityLimit;

  public QuotePremiumInput(
      Gender namedInsuredGender,
      UniversityProgram namedInsuredUniversityProgram,
      Gender additionalInsuredGender,
      UniversityProgram additionalInsuredUniversityProgram,
      Animals animals,
      Amount bikePrice,
      CivilLiabilityLimit civilLiabilityLimit) {
    this.namedInsuredGender = namedInsuredGender;
    this.namedInsuredUniversityProgram = namedInsuredUniversityProgram;
    this.additionalInsuredGender = additionalInsuredGender;
    this.additionalInsuredUniversityProgram = additionalInsuredUniversityProgram;
    this.animals = animals;
    this.bikePrice = bikePrice;
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

  public Amount getBikePrice() {
    return bikePrice;
  }

  public CivilLiabilityLimit getCivilLiabilityLimit() {
    return civilLiabilityLimit;
  }
}
