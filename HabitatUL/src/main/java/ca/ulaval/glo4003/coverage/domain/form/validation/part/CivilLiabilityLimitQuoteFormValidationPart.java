package ca.ulaval.glo4003.coverage.domain.form.validation.part;

import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.coverage.domain.form.validation.quote.QuoteFormValidationPart;

public class CivilLiabilityLimitQuoteFormValidationPart
    extends CivilLiabilityLimitFormValidationPart implements QuoteFormValidationPart {
  @Override
  public void validate(QuoteForm form) {
    CivilLiabilityLimit civilLiabilityLimit = form.getCivilLiability().getLimit();
    int numberOfUnits = form.getBuilding().getNumberOfUnits();
    validate(civilLiabilityLimit, numberOfUnits);
  }
}
