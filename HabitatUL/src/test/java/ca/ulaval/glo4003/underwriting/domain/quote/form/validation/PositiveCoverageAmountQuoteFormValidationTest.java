package ca.ulaval.glo4003.underwriting.domain.quote.form.validation;

import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.helper.quote.form.PersonalPropertyBuilder;
import ca.ulaval.glo4003.helper.quote.form.QuoteFormBuilder;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.underwriting.domain.quote.error.QuotePositiveCoverageAmountError;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.PersonalProperty;
import org.junit.Before;
import org.junit.Test;

public class PositiveCoverageAmountQuoteFormValidationTest {
  private PositiveCoverageAmountQuoteFormValidation subject;
  private QuoteForm quoteForm;

  @Before
  public void setUp() {
    subject = new PositiveCoverageAmountQuoteFormValidation();
  }

  @Test
  public void validatingQuoteForm_withPositiveCoverageAmount_shouldNotThrow() {
    Amount coverageAmount = MoneyGenerator.createAmountGreaterThan(Amount.ZERO);
    PersonalProperty personalProperty =
        PersonalPropertyBuilder.aPersonalProperty().withCoverageAmount(coverageAmount).build();
    quoteForm = QuoteFormBuilder.aQuoteForm().withPersonalProperty(personalProperty).build();

    subject.validate(quoteForm);
  }

  @Test(expected = QuotePositiveCoverageAmountError.class)
  public void validatingQuoteForm_withNullCoverageAmount_shouldThrow() {
    PersonalProperty personalProperty =
        PersonalPropertyBuilder.aPersonalProperty().withCoverageAmount(Amount.ZERO).build();
    quoteForm = QuoteFormBuilder.aQuoteForm().withPersonalProperty(personalProperty).build();

    subject.validate(quoteForm);
  }

  @Test(expected = QuotePositiveCoverageAmountError.class)
  public void validatingQuoteForm_withNegativeCoverageAmount_shouldThrow() {
    Amount coverageAmount = MoneyGenerator.createAmountSmallerThan(Amount.ZERO);
    PersonalProperty personalProperty =
        PersonalPropertyBuilder.aPersonalProperty().withCoverageAmount(coverageAmount).build();
    quoteForm = QuoteFormBuilder.aQuoteForm().withPersonalProperty(personalProperty).build();

    subject.validate(quoteForm);
  }
}
