package ca.ulaval.glo4003.helper.premium;

import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.AnimalsInput;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.CivilLiabilityLimitInput;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.UniversityProgramInput;
import ca.ulaval.glo4003.shared.domain.identity.Gender;

import static ca.ulaval.glo4003.helper.premium.QuotePremiumInputGenerator.createAnimalsInput;
import static ca.ulaval.glo4003.helper.premium.QuotePremiumInputGenerator.createCivilLiabilityLimitInput;
import static ca.ulaval.glo4003.helper.quote.form.IdentityGenerator.createGender;

public class QuotePremiumInputBuilder {
  private static final Gender DEFAULT_NAMED_INSURED_GENDER = createGender();
  private static final UniversityProgramInput DEFAULT_NAMED_INSURED_UNIVERSITY_PROGRAM =
      UniversityProgramInputGenerator.create();
  private static final Gender DEFAULT_ADDITIONAL_INSURED_GENDER = createGender();
  private static final UniversityProgramInput DEFAULT_ADDITIONAL_INSURED_UNIVERSITY_PROGRAM =
      UniversityProgramInputGenerator.create();
  private static final AnimalsInput DEFAULT_ANIMALS = createAnimalsInput();
  private static final CivilLiabilityLimitInput DEFAULT_CIVIL_LIABILITY_LIMIT =
      createCivilLiabilityLimitInput();

  private Gender namedInsuredGender = DEFAULT_NAMED_INSURED_GENDER;
  private UniversityProgramInput namedInsuredUniversityProgram =
      DEFAULT_NAMED_INSURED_UNIVERSITY_PROGRAM;
  private Gender additionalInsuredGender = DEFAULT_ADDITIONAL_INSURED_GENDER;
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

  public QuotePremiumInputBuilder withNamedInsuredGender(Gender gender) {
    this.namedInsuredGender = gender;
    return this;
  }

  public QuotePremiumInputBuilder withNamedInsuredUniversityProgram(
      UniversityProgramInput universityProgram) {
    this.namedInsuredUniversityProgram = universityProgram;
    return this;
  }

  public QuotePremiumInputBuilder withAdditionalInsuredGender(Gender gender) {
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
