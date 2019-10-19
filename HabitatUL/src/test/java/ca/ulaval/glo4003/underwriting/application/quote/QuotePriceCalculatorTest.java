package ca.ulaval.glo4003.underwriting.application.quote;

import ca.ulaval.glo4003.generator.MoneyGenerator;
import ca.ulaval.glo4003.generator.quote.form.QuoteFormGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.price.QuotePriceFormula;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuotePriceCalculatorTest {
  private static final Money PRICE = MoneyGenerator.create();
  private static final QuoteForm QUOTE_FORM = QuoteFormGenerator.createQuoteForm();

  @Mock private QuotePriceFormula quotePriceFormula;

  private QuotePriceCalculator subject;

  @Before
  public void setUp() {
    when(quotePriceFormula.compute(any(QuoteForm.class))).thenReturn(PRICE);
    subject = new QuotePriceCalculator(quotePriceFormula);
  }

  @Test
  public void computingQuotePrice_shouldComputeQuotePriceFormula() {
    subject.compute(QUOTE_FORM);

    verify(quotePriceFormula).compute(QUOTE_FORM);
  }

  @Test
  public void computingQuotePrice_shouldReturnComputedQuotePrice() {
    Money computedPrice = subject.compute(QUOTE_FORM);

    assertEquals(PRICE, computedPrice);
  }
}
