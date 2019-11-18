package ca.ulaval.glo4003.helper.coverage.premium;

import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.coverage.domain.form.identity.UniversityProgram;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.Animals;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.shared.domain.identity.Gender;
import ca.ulaval.glo4003.shared.domain.money.Amount;

import static ca.ulaval.glo4003.helper.coverage.form.identity.IdentityGenerator.createGender;
import static ca.ulaval.glo4003.helper.coverage.form.identity.UniversityProgramGenerator.createUniversityProgram;
import static ca.ulaval.glo4003.helper.coverage.form.personalproperty.BicycleGenerator.createBicyclePrice;
import static ca.ulaval.glo4003.helper.coverage.premium.QuotePremiumInputGenerator.createAnimals;
import static ca.ulaval.glo4003.helper.coverage.premium.QuotePremiumInputGenerator.createCivilLiabilityLimit;

public class QuotePremiumInputBuilder {
  private final Gender DEFAULT_NAMED_INSURED_GENDER = createGender();
  private final UniversityProgram DEFAULT_NAMED_INSURED_UNIVERSITY_PROGRAM =
      createUniversityProgram();
  private final Gender DEFAULT_ADDITIONAL_INSURED_GENDER = createGender();
  private final UniversityProgram DEFAULT_ADDITIONAL_INSURED_UNIVERSITY_PROGRAM =
      createUniversityProgram();
  private final Animals DEFAULT_ANIMALS = createAnimals();
  private final Amount DEFAULT_BICYCLE_PRICE = createBicyclePrice();
  private final CivilLiabilityLimit DEFAULT_CIVIL_LIABILITY_LIMIT = createCivilLiabilityLimit();

  private Gender namedInsuredGender = DEFAULT_NAMED_INSURED_GENDER;
  private UniversityProgram namedInsuredUniversityProgram =
      DEFAULT_NAMED_INSURED_UNIVERSITY_PROGRAM;
  private Gender additionalInsuredGender = DEFAULT_ADDITIONAL_INSURED_GENDER;
  private UniversityProgram additionalInsuredUniversityProgram =
      DEFAULT_ADDITIONAL_INSURED_UNIVERSITY_PROGRAM;
  private Animals animals = DEFAULT_ANIMALS;
  private Amount bicyclePrice = DEFAULT_BICYCLE_PRICE;
  private CivilLiabilityLimit civilLiabilityLimit = DEFAULT_CIVIL_LIABILITY_LIMIT;

  private QuotePremiumInputBuilder() {}

  public static QuotePremiumInputBuilder aQuotePremiumInput() {
    return new QuotePremiumInputBuilder();
  }

  public QuotePremiumInputBuilder withCivilLiabilityLimit(CivilLiabilityLimit civilLiabilityLimit) {
    this.civilLiabilityLimit = civilLiabilityLimit;
    return this;
  }

  public QuotePremiumInputBuilder withNamedInsuredGender(Gender gender) {
    this.namedInsuredGender = gender;
    return this;
  }

  public QuotePremiumInputBuilder withNamedInsuredUniversityProgram(
      UniversityProgram universityProgram) {
    this.namedInsuredUniversityProgram = universityProgram;
    return this;
  }

  public QuotePremiumInputBuilder withAdditionalInsuredGender(Gender gender) {
    this.additionalInsuredGender = gender;
    return this;
  }

  public QuotePremiumInputBuilder withAdditionalInsuredUniversityProgram(
      UniversityProgram universityProgram) {
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
        bicyclePrice,
        civilLiabilityLimit);
  }
}
