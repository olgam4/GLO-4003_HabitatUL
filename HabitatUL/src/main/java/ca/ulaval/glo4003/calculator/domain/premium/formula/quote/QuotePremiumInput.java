package ca.ulaval.glo4003.calculator.domain.premium.formula.quote;

import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.AnimalsInput;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.CivilLiabilityLimitInput;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.GenderInput;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.UniversityProgramInput;
import ca.ulaval.glo4003.shared.domain.ValueObject;

public class QuotePremiumInput extends ValueObject {
  private final GenderInput namedInsuredGender;
  private final UniversityProgramInput namedInsuredUniversityProgram;
  private final GenderInput additionalInsuredGender;
  private final UniversityProgramInput additionalInsuredUniversityProgram;
  private final AnimalsInput animals;
  private final CivilLiabilityLimitInput civilLiabilityLimit;

  public QuotePremiumInput(
      GenderInput namedInsuredGender,
      UniversityProgramInput namedInsuredUniversityProgram,
      GenderInput additionalInsuredGender,
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

  public GenderInput getNamedInsuredGender() {
    return namedInsuredGender;
  }

  public UniversityProgramInput getNamedInsuredUniversityProgram() {
    return namedInsuredUniversityProgram;
  }

  public GenderInput getAdditionalInsuredGender() {
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
