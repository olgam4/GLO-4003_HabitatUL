package ca.ulaval.glo4003.underwriting.domain.quote.form;

import ca.ulaval.glo4003.helper.quote.form.CivilLiabilityBuilder;
import ca.ulaval.glo4003.helper.quote.form.QuoteFormBuilder;
import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiability;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class QuoteFormTest {
  private static final CivilLiability CIVIL_LIABILITY_WITHOUT_AMOUNT =
      CivilLiabilityBuilder.aCivilLiability().withoutAmount().build();

  private QuoteForm subject;

  @Test
  public void creatingQuoteForm_withoutCivilLiabilityAmount_shouldUseDefaultValue() {
    subject =
        QuoteFormBuilder.aQuoteForm().withCivilLiability(CIVIL_LIABILITY_WITHOUT_AMOUNT).build();

    assertNotNull(subject.getCivilLiability().getCivilLiabilityAmount());
  }
}
