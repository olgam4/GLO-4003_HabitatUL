package ca.ulaval.glo4003.helper.calculator;

import ca.ulaval.glo4003.calculator.domain.premium.formula.input.Animals;
import ca.ulaval.glo4003.calculator.domain.premium.formula.input.CivilLiabilityLimit;
import ca.ulaval.glo4003.calculator.domain.premium.formula.input.UniversityProgram;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.shared.domain.identity.Gender;
import ca.ulaval.glo4003.shared.domain.money.Amount;

import static ca.ulaval.glo4003.helper.calculator.BikePremiumInputGenerator.createBikePrice;
import static ca.ulaval.glo4003.helper.calculator.QuotePremiumInputGenerator.createAnimals;
import static ca.ulaval.glo4003.helper.calculator.QuotePremiumInputGenerator.createCivilLiabilityLimit;
import static ca.ulaval.glo4003.helper.calculator.UniversityProgramGenerator.createUniversityProgram;
import static ca.ulaval.glo4003.helper.quote.form.IdentityGenerator.createGender;

public class QuotePremiumInputBuilder {
  private static final Gender DEFAULT_NAMED_INSURED_GENDER = createGender();
  private static final UniversityProgram DEFAULT_NAMED_INSURED_UNIVERSITY_PROGRAM =
      createUniversityProgram();
  private static final Gender DEFAULT_ADDITIONAL_INSURED_GENDER = createGender();
  private static final UniversityProgram DEFAULT_ADDITIONAL_INSURED_UNIVERSITY_PROGRAM =
      createUniversityProgram();
  private static final Animals DEFAULT_ANIMALS = createAnimals();
  private static final Amount DEFAULT_BIKE_PRICE = createBikePrice();
  private static final CivilLiabilityLimit DEFAULT_CIVIL_LIABILITY_LIMIT =
      createCivilLiabilityLimit();

  private Gender namedInsuredGender = DEFAULT_NAMED_INSURED_GENDER;
  private UniversityProgram namedInsuredUniversityProgram =
      DEFAULT_NAMED_INSURED_UNIVERSITY_PROGRAM;
  private Gender additionalInsuredGender = DEFAULT_ADDITIONAL_INSURED_GENDER;
  private UniversityProgram additionalInsuredUniversityProgram =
      DEFAULT_ADDITIONAL_INSURED_UNIVERSITY_PROGRAM;
  private Animals animals = DEFAULT_ANIMALS;
  private Amount bikePrice = DEFAULT_BIKE_PRICE;
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

  public QuotePremiumInputBuilder withoutBikePrice() {
    this.bikePrice = null;
    return this;
  }

  public QuotePremiumInputBuilder withBikePrice(Amount bikePrice) {
    this.bikePrice = bikePrice;
    return this;
  }

  public QuotePremiumInput build() {
    return new QuotePremiumInput(
        namedInsuredGender,
        namedInsuredUniversityProgram,
        additionalInsuredGender,
        additionalInsuredUniversityProgram,
        animals,
        bikePrice,
        civilLiabilityLimit);
  }
}
