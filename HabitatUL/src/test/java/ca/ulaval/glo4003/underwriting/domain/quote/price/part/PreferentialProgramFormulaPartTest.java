package ca.ulaval.glo4003.underwriting.domain.quote.price.part;

import ca.ulaval.glo4003.generator.MoneyGenerator;
import ca.ulaval.glo4003.generator.quote.form.QuoteFormGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.price.PreferentialProgramAdjustmentProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;
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
public class PreferentialProgramFormulaPartTest {
  private static final Money BASE_PRICE = MoneyGenerator.create();
  private static final Money PRICE_ADJUSTMENT = MoneyGenerator.create();
  private static final QuoteForm QUOTE_FORM = QuoteFormGenerator.createQuoteForm();

  @Mock private PreferentialProgramAdjustmentProvider preferentialProgramAdjustmentProvider;
  @Mock private QuotePriceAdjustment quotePriceAdjustment;

  private PreferentialProgramFormulaPart subject;

  @Before
  public void setUp() {
    when(preferentialProgramAdjustmentProvider.getAdjustment(any(String.class)))
        .thenReturn(quotePriceAdjustment);
    when(quotePriceAdjustment.apply(any(Money.class))).thenReturn(PRICE_ADJUSTMENT);
    subject = new PreferentialProgramFormulaPart(preferentialProgramAdjustmentProvider);
  }

  @Test
  public void computingFormulaPart_shouldGetPreferentialProgramAdjustment() {
    subject.compute(QUOTE_FORM, BASE_PRICE);

    verify(preferentialProgramAdjustmentProvider)
        .getAdjustment(QUOTE_FORM.getIdentity().getUniversityProfile().getProgram());
  }

  @Test
  public void computingFormulaPart_shouldComputeAdjustmentAmount() {
    subject.compute(QUOTE_FORM, BASE_PRICE);

    verify(quotePriceAdjustment).apply(BASE_PRICE);
  }

  @Test
  public void computingFormulaPart_shouldReturnAdjustmentAmount() {
    Money adjustmentAmount = subject.compute(QUOTE_FORM, BASE_PRICE);

    assertEquals(PRICE_ADJUSTMENT, adjustmentAmount);
  }
}
