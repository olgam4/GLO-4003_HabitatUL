package ca.ulaval.glo4003.coverage.domain.form.validationpart;

import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.coverage.domain.form.validation.quote.QuoteFormValidationPart;
import ca.ulaval.glo4003.coverage.domain.form.validationpart.error.CivilLiabilityLimitError;

public class CivilLiabilityLimitFormValidationPart implements QuoteFormValidationPart {
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
      throw new CivilLiabilityLimitError();
    }
  }
}