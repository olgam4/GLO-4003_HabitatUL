package ca.ulaval.glo4003.underwriting.domain.quote.price;

import ca.ulaval.glo4003.generator.money.MoneyGenerator;
import ca.ulaval.glo4003.generator.quote.form.QuoteFormGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
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
public class QuotePriceFormulaTest {
  private static final Money INDICATED_PRICE = MoneyGenerator.create();
  private static final Money ADJUSTED_PRICE = MoneyGenerator.create();
  private static final Money ANOTHER_ADJUSTED_PRICE = MoneyGenerator.create();
  private static final QuoteForm QUOTE_FORM = QuoteFormGenerator.createQuoteForm();

  @Mock private QuoteIndicatedPriceCalculator quoteIndicatedPriceCalculator;
  @Mock private QuotePriceFormulaPart quotePriceFormulaPart;
  @Mock private QuotePriceFormulaPart anotherQuotePriceFormulaPart;

  private QuotePriceFormula subject;

  @Before
  public void setUp() {
    when(quoteIndicatedPriceCalculator.computeIndicatedQuotePrice(any(QuoteForm.class)))
        .thenReturn(INDICATED_PRICE);
    when(quotePriceFormulaPart.apply(any(QuoteForm.class), any(Money.class)))
        .thenReturn(ADJUSTED_PRICE);
    when(anotherQuotePriceFormulaPart.apply(any(QuoteForm.class), any(Money.class)))
        .thenReturn(ANOTHER_ADJUSTED_PRICE);
    subject = new QuotePriceFormula(quoteIndicatedPriceCalculator);
  }

  @Test
  public void computingQuotePrice_withoutAdjustments_shouldReturnIndicatedQuotePrice() {
    Money computedPrice = subject.compute(QUOTE_FORM);

    assertEquals(INDICATED_PRICE, computedPrice);
  }

  @Test
  public void computingQuotePrice_withAdjustments_shouldReturnAdjustedQuotePrice() {
    subject.addFormulaPart(quotePriceFormulaPart);
    subject.addFormulaPart(anotherQuotePriceFormulaPart);

    Money computedPrice = subject.compute(QUOTE_FORM);

    assertEquals(ANOTHER_ADJUSTED_PRICE, computedPrice);
  }

  @Test
  public void computingQuotePrice_withAdjustments_shouldConsiderAllFormulaParts() {
    subject.addFormulaPart(quotePriceFormulaPart);
    subject.addFormulaPart(anotherQuotePriceFormulaPart);

    subject.compute(QUOTE_FORM);

    verify(quotePriceFormulaPart).apply(QUOTE_FORM, INDICATED_PRICE);
    verify(anotherQuotePriceFormulaPart).apply(QUOTE_FORM, ADJUSTED_PRICE);
  }
}
