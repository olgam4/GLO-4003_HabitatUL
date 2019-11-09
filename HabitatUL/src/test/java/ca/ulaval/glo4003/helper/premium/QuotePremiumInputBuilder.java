package ca.ulaval.glo4003.helper.premium;

import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.AnimalsInput;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.CivilLiabilityLimitInput;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.GenderInput;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.UniversityProgramInput;

import static ca.ulaval.glo4003.helper.premium.QuotePremiumInputGenerator.*;

public class QuotePremiumInputBuilder {
  private static final GenderInput DEFAULT_NAMED_INSURED_GENDER = createGenderInput();
  private static final UniversityProgramInput DEFAULT_NAMED_INSURED_UNIVERSITY_PROGRAM =
      UniversityProgramInputGenerator.create();
  private static final GenderInput DEFAULT_ADDITIONAL_INSURED_GENDER = createGenderInput();
  private static final UniversityProgramInput DEFAULT_ADDITIONAL_INSURED_UNIVERSITY_PROGRAM =
      UniversityProgramInputGenerator.create();
  private static final AnimalsInput DEFAULT_ANIMALS = createAnimalsInput();
  private static final CivilLiabilityLimitInput DEFAULT_CIVIL_LIABILITY_LIMIT =
      createCivilLiabilityLimitInput();

  private GenderInput namedInsuredGender = DEFAULT_NAMED_INSURED_GENDER;
  private UniversityProgramInput namedInsuredUniversityProgram =
      DEFAULT_NAMED_INSURED_UNIVERSITY_PROGRAM;
  private GenderInput additionalInsuredGender = DEFAULT_ADDITIONAL_INSURED_GENDER;
  private UniversityProgramInput additionalInsuredUniversityProgram =
      DEFAULT_ADDITIONAL_INSURED_UNIVERSITY_PROGRAM;
  private AnimalsInput animals = DEFAULT_ANIMALS;
  private CivilLiabilityLimitInput civilLiabilityLimit = DEFAULT_CIVIL_LIABILITY_LIMIT;

  private QuotePremiumInputBuilder() {}

  public static QuotePremiumInputBuilder aQuotePremiumInput() {
    return new QuotePremiumInputBuilder();
  }

  public QuotePremiumInputBuilder withCivilLiabilityLimit(
      CivilLiabilityLimitInput civilLiabilityLimit) {
    this.civilLiabilityLimit = civilLiabilityLimit;
    return this;
  }

  public QuotePremiumInputBuilder withNamedInsuredGender(GenderInput gender) {
    this.namedInsuredGender = gender;
    return this;
  }

  public QuotePremiumInputBuilder withNamedInsuredUniversityProgram(
      UniversityProgramInput universityProgram) {
    this.namedInsuredUniversityProgram = universityProgram;
    return this;
  }

  public QuotePremiumInputBuilder withAdditionalInsuredGender(GenderInput gender) {
    this.additionalInsuredGender = gender;
    return this;
  }

  public QuotePremiumInputBuilder withAdditionalInsuredUniversityProgram(
      UniversityProgramInput universityProgram) {
    this.additionalInsuredUniversityProgram = universityProgram;
    return this;
  }

  public QuotePremiumInput build() {
    return new QuotePremiumInput(
        namedInsuredGender,
        namedInsuredUniversityProgram,
        additionalInsuredGender,
        additionalInsuredUniversityProgram,
        animals,
        civilLiabilityLimit);
  }
}
