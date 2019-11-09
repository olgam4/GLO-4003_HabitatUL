package ca.ulaval.glo4003.calculator.domain.premium.formulapart.bikeprice;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.formula.bike.BikePremiumInput;
import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.helper.premium.BikePremiumInputGenerator;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.domain.money.Money;
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
public class BikePriceFormulaPartTest {
  private static final Money BASE_PREMIUM = MoneyGenerator.createMoney();
  private static final Money PREMIUM_ADJUSTMENT = MoneyGenerator.createMoney();
  private static final BikePremiumInput BIKE_PREMIUM_INPUT = BikePremiumInputGenerator.create();
  private static final Amount BIKE_PRICE = BIKE_PREMIUM_INPUT.getPrice();

  @Mock private BikePriceAdjustmentProvider bikePriceAdjustmentProvider;
  @Mock private PremiumAdjustment premiumAdjustment;

  private BikePriceFormulaPart subject;

  @Before
  public void setUp() {
    when(bikePriceAdjustmentProvider.getAdjustment(any(Amount.class)))
        .thenReturn(premiumAdjustment);
    when(premiumAdjustment.apply(any(Money.class))).thenReturn(PREMIUM_ADJUSTMENT);
    subject = new BikePriceFormulaPart(bikePriceAdjustmentProvider);
  }

  @Test
  public void computingFormulaPart_shouldGetAdjustment() {
    subject.compute(BIKE_PREMIUM_INPUT, BASE_PREMIUM);

    verify(bikePriceAdjustmentProvider).getAdjustment(BIKE_PRICE);
  }

  @Test
  public void computingFormulaPart_shouldApplyAdjustment() {
    subject.compute(BIKE_PREMIUM_INPUT, BASE_PREMIUM);

    verify(premiumAdjustment).apply(new Money(BIKE_PRICE));
  }

  @Test
  public void computingFormulaPart_shouldReturnPremiumAdjustment() {
    Money premiumAdjustment = subject.compute(BIKE_PREMIUM_INPUT, BASE_PREMIUM);

    assertEquals(PREMIUM_ADJUSTMENT, premiumAdjustment);
  }
}
