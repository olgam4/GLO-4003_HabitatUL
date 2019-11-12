package ca.ulaval.glo4003.coverage.domain.form;

import ca.ulaval.glo4003.helper.coverage.form.QuoteFormBuilder;
import org.junit.Test;

import static ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiability.UNFILLED_CIVIL_LIABILITY;
import static org.junit.Assert.assertNotNull;

public class QuoteFormTest {
  private QuoteForm subject;

  @Test
  public void creatingQuoteForm_withUnfilledCivilLiability_shouldUseDefaultValue() {
    subject = QuoteFormBuilder.aQuoteForm().withCivilLiability(UNFILLED_CIVIL_LIABILITY).build();

    assertNotNull(subject.getCivilLiability().getLimit());
  }
}
