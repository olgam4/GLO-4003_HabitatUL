package ca.ulaval.glo4003.coverage.domain.premium.formulapart.bicycleprice;

import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BicycleEndorsementPremiumInput;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.domain.money.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.helper.coverage.premium.BicycleEndorsementPremiumInputGenerator.createBicycleEndorsementPremiumInput;
import static ca.ulaval.glo4003.helper.shared.MoneyGenerator.createMoney;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BicyclePriceFormulaPartTest {
  private static final Money BASE_PREMIUM = createMoney();
  private static final Money PREMIUM_ADJUSTMENT = createMoney();
  private static final BicycleEndorsementPremiumInput BICYCLE_PREMIUM_INPUT =
      createBicycleEndorsementPremiumInput();
  private static final Amount BICYCLE_PRICE = BICYCLE_PREMIUM_INPUT.getPrice();

  @Mock private BicyclePriceAdjustmentProvider bicyclePriceAdjustmentProvider;
  @Mock private PremiumAdjustment premiumAdjustment;

  private BicycleEndorsementPriceFormulaPart subject;

  @Before
  public void setUp() {
    when(bicyclePriceAdjustmentProvider.getAdjustment(any(Amount.class)))
        .thenReturn(premiumAdjustment);
    when(premiumAdjustment.apply(any(Money.class))).thenReturn(PREMIUM_ADJUSTMENT);
    subject = new BicycleEndorsementPriceFormulaPart(bicyclePriceAdjustmentProvider);
  }

  @Test
  public void computingFormulaPart_shouldGetAdjustment() {
    subject.compute(BICYCLE_PREMIUM_INPUT, BASE_PREMIUM);

    verify(bicyclePriceAdjustmentProvider).getAdjustment(BICYCLE_PRICE);
  }

  @Test
  public void computingFormulaPart_shouldApplyAdjustment() {
    subject.compute(BICYCLE_PREMIUM_INPUT, BASE_PREMIUM);

    verify(premiumAdjustment).apply(new Money(BICYCLE_PRICE));
  }

  @Test
  public void computingFormulaPart_shouldReturnPremiumAdjustment() {
    Money premiumAdjustment = subject.compute(BICYCLE_PREMIUM_INPUT, BASE_PREMIUM);

    assertEquals(PREMIUM_ADJUSTMENT, premiumAdjustment);
  }
}
