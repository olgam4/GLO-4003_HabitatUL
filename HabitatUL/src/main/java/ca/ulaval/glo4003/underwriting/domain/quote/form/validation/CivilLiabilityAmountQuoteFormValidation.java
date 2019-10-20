package ca.ulaval.glo4003.underwriting.domain.quote.form.validation;

import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteCivilLiabilityError;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiabilityAmount;

public class CivilLiabilityAmountQuoteFormValidation implements QuoteFormValidation {
  static final int MIN_NUMBER_OF_UNITS_FOR_HIGHER_CIVIL_LIABILITY_AMOUNT = 4;
  static final int MAX_NUMBER_OF_UNITS_FOR_LOWER_CIVIL_LIABILITY_AMOUNT = 50;

  @Override
  public void validate(QuoteForm quoteForm) {
    int numberOfUnits = quoteForm.getBuilding().getNumberOfUnits();
    CivilLiabilityAmount civilLiabilityAmount =
        quoteForm.getCivilLiability().getCivilLiabilityAmount();
    if (numberOfUnits < MIN_NUMBER_OF_UNITS_FOR_HIGHER_CIVIL_LIABILITY_AMOUNT) {
      enforceCivilLiabilityAmount(civilLiabilityAmount, CivilLiabilityAmount.ONE_MILLION);
    } else if (numberOfUnits >= MAX_NUMBER_OF_UNITS_FOR_LOWER_CIVIL_LIABILITY_AMOUNT) {
      enforceCivilLiabilityAmount(civilLiabilityAmount, CivilLiabilityAmount.TWO_MILLION);
    }
  }

  private void enforceCivilLiabilityAmount(
      CivilLiabilityAmount observedCivilLiabilityAmount,
      CivilLiabilityAmount enforcedCivilLiabilityAmount) {
    if (observedCivilLiabilityAmount != enforcedCivilLiabilityAmount) {
      throw new QuoteCivilLiabilityError();
    }
  }
}
