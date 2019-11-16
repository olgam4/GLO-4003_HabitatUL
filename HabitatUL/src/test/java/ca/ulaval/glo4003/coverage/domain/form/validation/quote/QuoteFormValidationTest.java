package ca.ulaval.glo4003.coverage.domain.form.validation.quote;

import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.form.validation.FormValidation;
import ca.ulaval.glo4003.coverage.domain.form.validation.FormValidationTest;

import static ca.ulaval.glo4003.helper.coverage.form.QuoteFormGenerator.createQuoteForm;

public class QuoteFormValidationTest extends FormValidationTest<QuoteForm> {
  @Override
  public FormValidation createSubject() {
    return new QuoteFormValidation();
  }

  @Override
  public QuoteForm createForm() {
    return createQuoteForm();
  }
}
