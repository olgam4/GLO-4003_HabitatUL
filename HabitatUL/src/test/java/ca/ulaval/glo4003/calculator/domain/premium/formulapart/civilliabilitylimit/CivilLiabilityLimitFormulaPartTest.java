package ca.ulaval.glo4003.calculator.domain.premium.formulapart.civilliabilitylimit;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.CivilLiabilityLimitInput;
import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.helper.premium.QuotePremiumInputBuilder;
import ca.ulaval.glo4003.shared.domain.money.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.helper.premium.QuotePremiumInputGenerator.createCivilLiabilityLimitInput;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CivilLiabilityLimitFormulaPartTest {
  private static final Money BASE_PREMIUM = MoneyGenerator.createMoney();
  private static final Money PREMIUM_ADJUSTMENT = MoneyGenerator.createMoney();
  private static final CivilLiabilityLimitInput CIVIL_LIABILITY_LIMIT =
      createCivilLiabilityLimitInput();
  private static final QuotePremiumInput QUOTE_PREMIUM_INPUT =
      QuotePremiumInputBuilder.aQuotePremiumInput()
          .withCivilLiabilityLimit(CIVIL_LIABILITY_LIMIT)
          .build();

  @Mock private CivilLiabilityLimitAdjustmentProvider civilLiabilityLimitAdjustmentProvider;
  @Mock private PremiumAdjustment premiumAdjustment;

  private CivilLiabilityLimitFormulaPart subject;

  @Before
  public void setUp() {
    when(civilLiabilityLimitAdjustmentProvider.getAdjustment(any(CivilLiabilityLimitInput.class)))
        .thenReturn(premiumAdjustment);
    when(premiumAdjustment.apply(any(Money.class))).thenReturn(PREMIUM_ADJUSTMENT);
    subject = new CivilLiabilityLimitFormulaPart(civilLiabilityLimitAdjustmentProvider);
  }

  @Test
  public void computingFormulaPart_shouldGetAdjustment() {
    subject.compute(QUOTE_PREMIUM_INPUT, BASE_PREMIUM);

    verify(civilLiabilityLimitAdjustmentProvider).getAdjustment(CIVIL_LIABILITY_LIMIT);
  }

  @Test
  public void computingFormulaPart_shouldApplyAdjustment() {
    subject.compute(QUOTE_PREMIUM_INPUT, BASE_PREMIUM);

    verify(premiumAdjustment).apply(BASE_PREMIUM);
  }

  @Test
  public void computingFormulaPart_shouldReturnPremiumAdjustment() {
    Money premiumAdjustment = subject.compute(QUOTE_PREMIUM_INPUT, BASE_PREMIUM);

    assertEquals(PREMIUM_ADJUSTMENT, premiumAdjustment);
  }
}
