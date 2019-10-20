package ca.ulaval.glo4003.underwriting.domain.quote.form;

import ca.ulaval.glo4003.generator.quote.form.QuoteFormGenerator;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class QuoteFormTest {
  private QuoteForm subject;

  @Test
  public void creatingQuoteForm_withoutCivilLiabilityAmount_shouldUseDefaultValue() {
    subject = QuoteFormGenerator.createQuoteFormWithCivilLiabilityAmount(null);

    assertNotNull(subject.getCivilLiability().getCivilLiabilityAmount());
  }
}
