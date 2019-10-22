package ca.ulaval.glo4003.underwriting.domain.quote.price.part;

import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.helper.quote.form.QuoteFormBuilder;
import ca.ulaval.glo4003.helper.quote.form.QuoteFormGenerator;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Gender;
import ca.ulaval.glo4003.underwriting.domain.quote.price.RoommateAdjustmentProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RoommateFormulaPartTest {
  private static final Money BASE_PRICE = MoneyGenerator.create();
  private static final Money PRICE_ADJUSTMENT = MoneyGenerator.create();
  private static final QuoteForm QUOTE_FORM = QuoteFormGenerator.createQuoteForm();
  private static final QuoteForm QUOTE_FORM_WITHOUT_ADDITIONAL_INSURED =
      QuoteFormBuilder.aQuoteForm().withoutAdditionalInsured().build();

  @Mock private RoommateAdjustmentProvider roommateAdjustmentProvider;
  @Mock private QuotePriceAdjustment quotePriceAdjustment;

  private RoommateFormulaPart subject;

  @Before
  public void setUp() {
    when(roommateAdjustmentProvider.getAdjustment(any(Gender.class), any(Gender.class)))
        .thenReturn(quotePriceAdjustment);
    when(quotePriceAdjustment.apply(any(Money.class))).thenReturn(PRICE_ADJUSTMENT);
    subject = new RoommateFormulaPart(roommateAdjustmentProvider);
  }

  @Test
  public void computingFormulaPart_shouldGetRoommateAdjustment() {
    subject.compute(QUOTE_FORM, BASE_PRICE);

    verify(roommateAdjustmentProvider)
        .getAdjustment(
            QUOTE_FORM.getPersonalInformation().getGender(),
            QUOTE_FORM.getAdditionalInsured().getGender());
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

  @Test
  public void computingFormulaPart_withoutAdditionalInsured_shouldNotGetRoommateAdjustment() {
    subject.compute(QUOTE_FORM_WITHOUT_ADDITIONAL_INSURED, BASE_PRICE);

    verify(roommateAdjustmentProvider, never()).getAdjustment(any(), any());
  }

  @Test
  public void computingFormulaPart_withoutAdditionalInsured_shouldReturnNullAdjustmentAmount() {
    Money adjustmentAmount = subject.compute(QUOTE_FORM_WITHOUT_ADDITIONAL_INSURED, BASE_PRICE);

    assertEquals(new Money(Amount.ZERO), adjustmentAmount);
  }
}
