package ca.ulaval.glo4003.coverage.domain.premium.formulapart.animals;

import ca.ulaval.glo4003.coverage.domain.form.personalproperty.AnimalBreed;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.NoPremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.helper.shared.MoneyGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Map;

import static ca.ulaval.glo4003.helper.coverage.premium.QuotePremiumInputGenerator.createQuotePremiumInput;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AnimalsFormulaPartTest {
  private static final QuotePremiumInput QUOTE_PREMIUM_INPUT = createQuotePremiumInput();
  private static final Map<AnimalBreed, Integer> ANIMAL_COLLECTION =
      QUOTE_PREMIUM_INPUT.getAnimals().getCollection();
  private static final Money BASE_PREMIUM = MoneyGenerator.createMoney();
  private static final Money PREMIUM_ADJUSTMENT = MoneyGenerator.createMoney();
  private static final Money MINIMUM_PREMIUM_ADJUSTMENT = MoneyGenerator.createMoney();
  private static final Money MAXIMUM_PREMIUM_ADJUSTMENT = MoneyGenerator.createMoney();

  @Mock private AnimalsAdjustmentProvider animalsAdjustmentProvider;
  @Mock private AnimalsAdjustmentLimitsProvider animalsAdjustmentLimitsProvider;
  @Mock private PremiumAdjustment premiumAdjustment;
  @Mock private PremiumAdjustment minimumPremiumAdjustment;
  @Mock private PremiumAdjustment maximumPremiumAdjustment;

  private AnimalsFormulaPart subject;

  @Before
  public void setUp() {
    when(premiumAdjustment.apply(any(Money.class))).thenReturn(PREMIUM_ADJUSTMENT);
    when(animalsAdjustmentProvider.getAdjustment(any(AnimalBreed.class), any(Integer.class)))
        .thenReturn(premiumAdjustment);
    when(minimumPremiumAdjustment.apply(any(Money.class))).thenReturn(MINIMUM_PREMIUM_ADJUSTMENT);
    when(maximumPremiumAdjustment.apply(any(Money.class))).thenReturn(MAXIMUM_PREMIUM_ADJUSTMENT);
    when(animalsAdjustmentLimitsProvider.getMinimumAdjustment(any(Money.class)))
        .thenReturn(minimumPremiumAdjustment);
    when(animalsAdjustmentLimitsProvider.getMaximumAdjustment(any(Money.class)))
        .thenReturn(maximumPremiumAdjustment);
    subject = new AnimalsFormulaPart(animalsAdjustmentProvider, animalsAdjustmentLimitsProvider);
  }

  @Test
  public void computingFormulaPart_shouldGetAdjustments() {
    subject.compute(QUOTE_PREMIUM_INPUT, BASE_PREMIUM);

    verify(animalsAdjustmentProvider, times(ANIMAL_COLLECTION.size())).getAdjustment(any(), any());
  }

  @Test
  public void computingFormulaPart_shouldCombineAdjustments() {
    when(animalsAdjustmentLimitsProvider.getMinimumAdjustment(any(Money.class)))
        .thenReturn(new NoPremiumAdjustment());
    when(animalsAdjustmentLimitsProvider.getMaximumAdjustment(any(Money.class)))
        .thenReturn(new NoPremiumAdjustment());

    Money computedAdjustment = subject.compute(QUOTE_PREMIUM_INPUT, BASE_PREMIUM);

    Money expectedAdjustment = PREMIUM_ADJUSTMENT.multiply(ANIMAL_COLLECTION.size());
    assertEquals(expectedAdjustment, computedAdjustment);
  }

  @Test
  public void computingFormulaPart_shouldCapAdjustmentToMinimumAdjustment() {
    when(animalsAdjustmentProvider.getAdjustment(any(AnimalBreed.class), any(Integer.class)))
        .thenReturn(minimumPremiumAdjustment);
    when(animalsAdjustmentLimitsProvider.getMinimumAdjustment(any(Money.class)))
        .thenReturn(maximumPremiumAdjustment);

    Money computedAdjustment = subject.compute(QUOTE_PREMIUM_INPUT, BASE_PREMIUM);

    assertEquals(MAXIMUM_PREMIUM_ADJUSTMENT, computedAdjustment);
  }

  @Test
  public void computingFormulaPart_shouldCapAdjustmentToMaximumAdjustment() {
    when(animalsAdjustmentProvider.getAdjustment(any(AnimalBreed.class), any(Integer.class)))
        .thenReturn(maximumPremiumAdjustment);
    when(animalsAdjustmentLimitsProvider.getMaximumAdjustment(any(Money.class)))
        .thenReturn(minimumPremiumAdjustment);

    Money computedAdjustment = subject.compute(QUOTE_PREMIUM_INPUT, BASE_PREMIUM);

    assertEquals(MINIMUM_PREMIUM_ADJUSTMENT, computedAdjustment);
  }
}
