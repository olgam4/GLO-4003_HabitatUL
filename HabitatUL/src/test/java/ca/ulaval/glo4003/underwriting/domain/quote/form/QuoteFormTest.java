package ca.ulaval.glo4003.underwriting.domain.quote.form;

import ca.ulaval.glo4003.helper.quote.form.QuoteFormBuilder;
import org.junit.Test;

import static ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiability.UNFILLED_CIVIL_LIABILITY;
import static org.junit.Assert.assertNotNull;

public class QuoteFormTest {
  private QuoteForm subject;

  @Test
  public void creatingQuoteForm_withUnfilledCivilLiability_shouldUseDefaultValue() {
    subject = QuoteFormBuilder.aQuoteForm().withCivilLiability(UNFILLED_CIVIL_LIABILITY).build();

    assertNotNull(subject.getCivilLiability().getCivilLiabilityAmount());
  }
}
