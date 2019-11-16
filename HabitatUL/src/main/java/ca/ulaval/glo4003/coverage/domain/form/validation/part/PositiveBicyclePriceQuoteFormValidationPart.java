package ca.ulaval.glo4003.coverage.domain.form.validation.part;

import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.Bicycle;
import ca.ulaval.glo4003.coverage.domain.form.validation.quote.QuoteFormValidationPart;

public class PositiveBicyclePriceQuoteFormValidationPart
    extends PositiveBicyclePriceFormValidationPart implements QuoteFormValidationPart {

  @Override
  public void validate(QuoteForm form) {
    Bicycle bicycle = form.getPersonalProperty().getBicycle();
    if (bicycle.isFilled()) {
      validate(bicycle.getPrice());
    }
  }
}
