package ca.ulaval.glo4003.underwriting.domain.quote.price;

import ca.ulaval.glo4003.generator.MoneyGenerator;
import ca.ulaval.glo4003.generator.quote.form.QuoteFormGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.price.part.QuotePriceFormulaPart;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuotePriceFormulaTest {
  private static final Money BASE_PRICE = MoneyGenerator.create();
  private static final Money PRICE_ADJUSTMENT_AMOUNT = MoneyGenerator.create();
  private static final Money ANOTHER_PRICE_ADJUSTMENT_AMOUNT = MoneyGenerator.create();
  private static final QuoteForm QUOTE_FORM = QuoteFormGenerator.createQuoteForm();

  @Mock private QuoteBasePriceCalculator quoteBasePriceCalculator;
  @Mock private QuotePriceFormulaPart quotePriceFormulaPart;
  @Mock private QuotePriceFormulaPart anotherQuotePriceFormulaPart;

  private QuotePriceFormula subject;

  @Before
  public void setUp() {
    when(quoteBasePriceCalculator.computeQuoteBasePrice(any(QuoteForm.class)))
        .thenReturn(BASE_PRICE);
    when(quotePriceFormulaPart.compute(any(QuoteForm.class), any(Money.class)))
        .thenReturn(PRICE_ADJUSTMENT_AMOUNT);
    when(anotherQuotePriceFormulaPart.compute(any(QuoteForm.class), any(Money.class)))
        .thenReturn(ANOTHER_PRICE_ADJUSTMENT_AMOUNT);
    subject = new QuotePriceFormula(quoteBasePriceCalculator);
  }

  @Test
  public void computingQuotePrice_withoutAdjustments_shouldReturnQuoteBasePrice() {
    Money computedPrice = subject.compute(QUOTE_FORM);

    assertEquals(BASE_PRICE, computedPrice);
  }

  @Test
  public void computingQuotePrice_withAdjustments_shouldReturnAdjustedQuotePrice() {
    subject.addFormulaPart(quotePriceFormulaPart);
    subject.addFormulaPart(anotherQuotePriceFormulaPart);

    Money computedPrice = subject.compute(QUOTE_FORM);

    Money expectedPrice =
        BASE_PRICE.add(PRICE_ADJUSTMENT_AMOUNT).add(ANOTHER_PRICE_ADJUSTMENT_AMOUNT);
    assertEquals(expectedPrice, computedPrice);
  }
}
