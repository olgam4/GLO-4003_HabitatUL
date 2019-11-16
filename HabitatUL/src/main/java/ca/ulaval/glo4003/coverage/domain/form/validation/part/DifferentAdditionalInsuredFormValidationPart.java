package ca.ulaval.glo4003.coverage.domain.form.validation.part;

import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.form.validation.part.error.DifferentAdditionalInsuredError;
import ca.ulaval.glo4003.coverage.domain.form.validation.quote.QuoteFormValidationPart;

public class DifferentAdditionalInsuredFormValidationPart implements QuoteFormValidationPart {
  @Override
  public void validate(QuoteForm quoteForm) {
    if (isNamedAndAdditionalInsuredSamePerson(quoteForm)) {
      throw new DifferentAdditionalInsuredError();
    }
  }

  private boolean isNamedAndAdditionalInsuredSamePerson(QuoteForm quoteForm) {
    return quoteForm.getPersonalInformation().equals(quoteForm.getAdditionalInsured());
  }
}
