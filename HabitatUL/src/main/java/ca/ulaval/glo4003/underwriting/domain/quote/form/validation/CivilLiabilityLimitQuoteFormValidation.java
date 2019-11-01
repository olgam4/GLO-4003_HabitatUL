package ca.ulaval.glo4003.underwriting.domain.quote.form.validation;

import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteCivilLiabilityLimitError;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiabilityLimit;

public class CivilLiabilityLimitQuoteFormValidation implements QuoteFormValidation {
  static final int MIN_NUMBER_OF_UNITS_FOR_HIGHER_CIVIL_LIABILITY_LIMIT = 4;
  static final int MAX_NUMBER_OF_UNITS_FOR_LOWER_CIVIL_LIABILITY_LIMIT = 50;

  @Override
  public void validate(QuoteForm quoteForm) {
    int numberOfUnits = quoteForm.getBuilding().getNumberOfUnits();
    CivilLiabilityLimit civilLiabilityLimit = quoteForm.getCivilLiability().getLimit();
    if (numberOfUnits < MIN_NUMBER_OF_UNITS_FOR_HIGHER_CIVIL_LIABILITY_LIMIT) {
      enforceCivilLiabilityLimit(civilLiabilityLimit, CivilLiabilityLimit.ONE_MILLION);
    } else if (numberOfUnits >= MAX_NUMBER_OF_UNITS_FOR_LOWER_CIVIL_LIABILITY_LIMIT) {
      enforceCivilLiabilityLimit(civilLiabilityLimit, CivilLiabilityLimit.TWO_MILLION);
    }
  }

  private void enforceCivilLiabilityLimit(
      CivilLiabilityLimit observedCivilLiabilityLimit,
      CivilLiabilityLimit enforcedCivilLiabilityLimit) {
    if (observedCivilLiabilityLimit != enforcedCivilLiabilityLimit) {
      throw new QuoteCivilLiabilityLimitError();
    }
  }
}