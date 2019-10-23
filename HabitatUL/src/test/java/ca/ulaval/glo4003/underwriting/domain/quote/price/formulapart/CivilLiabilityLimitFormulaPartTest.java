package ca.ulaval.glo4003.underwriting.domain.quote.price.formulapart;

import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.helper.quote.form.CivilLiabilityBuilder;
import ca.ulaval.glo4003.helper.quote.form.QuoteFormBuilder;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiability;
import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.underwriting.domain.quote.price.CivilLiabilityLimitAdjustmentProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.helper.quote.form.CivilLiabilityGenerator.createCivilLiabilityLimit;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CivilLiabilityLimitFormulaPartTest {
  private static final Money BASE_PRICE = MoneyGenerator.create();
  private static final Money PRICE_ADJUSTMENT = MoneyGenerator.create();
  private static final CivilLiabilityLimit CIVIL_LIABILITY_LIMIT = createCivilLiabilityLimit();
  private static final CivilLiability CIVIL_LIABILITY =
      CivilLiabilityBuilder.aCivilLiability().withAmount(CIVIL_LIABILITY_LIMIT).build();
  private static final QuoteForm QUOTE_FORM =
      QuoteFormBuilder.aQuoteForm().withCivilLiability(CIVIL_LIABILITY).build();

  @Mock private CivilLiabilityLimitAdjustmentProvider civilLiabilityLimitAdjustmentProvider;
  @Mock private QuotePriceAdjustment quotePriceAdjustment;

  private CivilLiabilityLimitFormulaPart subject;

  @Before
  public void setUp() {
    when(civilLiabilityLimitAdjustmentProvider.getAdjustment(any(CivilLiabilityLimit.class)))
        .thenReturn(quotePriceAdjustment);
    when(quotePriceAdjustment.apply(any(Money.class))).thenReturn(PRICE_ADJUSTMENT);
    subject = new CivilLiabilityLimitFormulaPart(civilLiabilityLimitAdjustmentProvider);
  }

  @Test
  public void computingFormulaPart_shouldGetCivilLiabilityAdjustment() {
    subject.compute(QUOTE_FORM, BASE_PRICE);

    verify(civilLiabilityLimitAdjustmentProvider).getAdjustment(CIVIL_LIABILITY_LIMIT);
  }

  @Test
  public void computingFormulaPart_shouldApplyAdjustmentOnBasePrice() {
    subject.compute(QUOTE_FORM, BASE_PRICE);

    verify(quotePriceAdjustment).apply(BASE_PRICE);
  }

  @Test
  public void computingFormulaPart_shouldReturnAdjustmentAmount() {
    Money adjustmentAmount = subject.compute(QUOTE_FORM, BASE_PRICE);

    assertEquals(PRICE_ADJUSTMENT, adjustmentAmount);
  }
}
