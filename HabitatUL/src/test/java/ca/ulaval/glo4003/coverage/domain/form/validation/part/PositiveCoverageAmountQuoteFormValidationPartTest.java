package ca.ulaval.glo4003.coverage.domain.form.validation.part;

import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.PersonalProperty;
import ca.ulaval.glo4003.coverage.domain.form.validation.error.PositiveCoverageAmountError;
import ca.ulaval.glo4003.coverage.helper.form.QuoteFormBuilder;
import ca.ulaval.glo4003.coverage.helper.form.personalproperty.PersonalPropertyBuilder;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.shared.helper.MoneyGenerator.createAmountGreaterThanZero;
import static ca.ulaval.glo4003.shared.helper.MoneyGenerator.createAmountSmallerThanZero;

public class PositiveCoverageAmountQuoteFormValidationPartTest {
  private PositiveCoverageAmountQuoteFormValidationPart subject;
  private QuoteForm quoteForm;

  @Before
  public void setUp() {
    subject = new PositiveCoverageAmountQuoteFormValidationPart();
  }

  @Test
  public void validatingQuoteForm_withPositiveCoverageAmount_shouldNotThrow() {
    Amount coverageAmount = createAmountGreaterThanZero();
    PersonalProperty personalProperty =
        PersonalPropertyBuilder.aPersonalProperty().withCoverageAmount(coverageAmount).build();
    quoteForm = QuoteFormBuilder.aQuoteForm().withPersonalProperty(personalProperty).build();

    subject.validate(quoteForm);
  }

  @Test(expected = PositiveCoverageAmountError.class)
  public void validatingQuoteForm_withNullCoverageAmount_shouldThrow() {
    PersonalProperty personalProperty =
        PersonalPropertyBuilder.aPersonalProperty().withCoverageAmount(Amount.ZERO).build();
    quoteForm = QuoteFormBuilder.aQuoteForm().withPersonalProperty(personalProperty).build();

    subject.validate(quoteForm);
  }

  @Test(expected = PositiveCoverageAmountError.class)
  public void validatingQuoteForm_withNegativeCoverageAmount_shouldThrow() {
    Amount coverageAmount = createAmountSmallerThanZero();
    PersonalProperty personalProperty =
        PersonalPropertyBuilder.aPersonalProperty().withCoverageAmount(coverageAmount).build();
    quoteForm = QuoteFormBuilder.aQuoteForm().withPersonalProperty(personalProperty).build();

    subject.validate(quoteForm);
  }
}
