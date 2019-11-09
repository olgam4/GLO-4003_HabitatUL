package ca.ulaval.glo4003.calculator.domain.premium.formula.quote;

import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.AnimalsInput;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.CivilLiabilityLimitInput;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.UniversityProgramInput;
import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.identity.Gender;

public class QuotePremiumInput extends ValueObject {
  private final Gender namedInsuredGender;
  private final UniversityProgramInput namedInsuredUniversityProgram;
  private final Gender additionalInsuredGender;
  private final UniversityProgramInput additionalInsuredUniversityProgram;
  private final AnimalsInput animals;
  private final CivilLiabilityLimitInput civilLiabilityLimit;

  public QuotePremiumInput(
      Gender namedInsuredGender,
      UniversityProgramInput namedInsuredUniversityProgram,
      Gender additionalInsuredGender,
      UniversityProgramInput additionalInsuredUniversityProgram,
      AnimalsInput animals,
      CivilLiabilityLimitInput civilLiabilityLimit) {
    this.namedInsuredGender = namedInsuredGender;
    this.namedInsuredUniversityProgram = namedInsuredUniversityProgram;
    this.additionalInsuredGender = additionalInsuredGender;
    this.additionalInsuredUniversityProgram = additionalInsuredUniversityProgram;
    this.animals = animals;
    this.civilLiabilityLimit = civilLiabilityLimit;
  }

  public Gender getNamedInsuredGender() {
    return namedInsuredGender;
  }

  public UniversityProgramInput getNamedInsuredUniversityProgram() {
    return namedInsuredUniversityProgram;
  }

  public Gender getAdditionalInsuredGender() {
    return additionalInsuredGender;
  }

  public UniversityProgramInput getAdditionalInsuredUniversityProgram() {
    return additionalInsuredUniversityProgram;
  }

  public AnimalsInput getAnimals() {
    return animals;
  }

  public CivilLiabilityLimitInput getCivilLiabilityLimit() {
    return civilLiabilityLimit;
  }
}
