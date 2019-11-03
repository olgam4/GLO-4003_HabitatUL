package ca.ulaval.glo4003.underwriting.domain.quote.price.formulapart;

import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.helper.quote.form.QuoteFormGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.AnimalBreed;
import ca.ulaval.glo4003.underwriting.domain.quote.price.AnimalsAdjustmentLimitsProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.AnimalsAdjustmentProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.NoQuotePriceAdjustment;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AnimalsFormulaPartTest {
  private static final QuoteForm QUOTE_FORM = QuoteFormGenerator.createQuoteForm();
  private static final Map<AnimalBreed, Integer> ANIMAL_COLLECTION =
      QUOTE_FORM.getPersonalProperty().getAnimals().getCollection();
  private static final Money BASE_PRICE = MoneyGenerator.createMoney();
  private static final Money PRICE_ADJUSTMENT = MoneyGenerator.createMoney();
  private static final Money MINIMUM_PRICE_ADJUSTMENT = MoneyGenerator.createMoney();
  private static final Money MAXIMUM_PRICE_ADJUSTMENT = MoneyGenerator.createMoney();

  @Mock private AnimalsAdjustmentProvider animalsAdjustmentProvider;
  @Mock private AnimalsAdjustmentLimitsProvider animalsAdjustmentLimitsProvider;
  @Mock private QuotePriceAdjustment quotePriceAdjustment;
  @Mock private QuotePriceAdjustment minimumPriceAdjustment;
  @Mock private QuotePriceAdjustment maximumPriceAdjustment;

  private AnimalsFormulaPart subject;

  @Before
  public void setUp() {
    when(quotePriceAdjustment.apply(any(Money.class))).thenReturn(PRICE_ADJUSTMENT);
    when(animalsAdjustmentProvider.getAdjustment(any(AnimalBreed.class), any(Integer.class)))
        .thenReturn(quotePriceAdjustment);
    when(minimumPriceAdjustment.apply(any(Money.class))).thenReturn(MINIMUM_PRICE_ADJUSTMENT);
    when(maximumPriceAdjustment.apply(any(Money.class))).thenReturn(MAXIMUM_PRICE_ADJUSTMENT);
    when(animalsAdjustmentLimitsProvider.getMinimumAdjustment(any(Money.class)))
        .thenReturn(minimumPriceAdjustment);
    when(animalsAdjustmentLimitsProvider.getMaximumAdjustment(any(Money.class)))
        .thenReturn(maximumPriceAdjustment);
    subject = new AnimalsFormulaPart(animalsAdjustmentProvider, animalsAdjustmentLimitsProvider);
  }

  @Test
  public void computingFormulaPart_shouldGetAnimalsAdjustments() {
    subject.compute(QUOTE_FORM, BASE_PRICE);

    verify(animalsAdjustmentProvider, times(ANIMAL_COLLECTION.size())).getAdjustment(any(), any());
  }

  @Test
  public void computingFormulaPart_shouldCombineAdjustments() {
    when(animalsAdjustmentLimitsProvider.getMinimumAdjustment(any(Money.class)))
        .thenReturn(new NoQuotePriceAdjustment());
    when(animalsAdjustmentLimitsProvider.getMaximumAdjustment(any(Money.class)))
        .thenReturn(new NoQuotePriceAdjustment());

    Money computedAdjustment = subject.compute(QUOTE_FORM, BASE_PRICE);

    Money expectedAdjustment = PRICE_ADJUSTMENT.multiply(ANIMAL_COLLECTION.size());
    assertEquals(expectedAdjustment, computedAdjustment);
  }

  @Test
  public void computingFormulaPart_shouldCapAdjustmentToMinimumAdjustment() {
    when(animalsAdjustmentProvider.getAdjustment(any(AnimalBreed.class), any(Integer.class)))
        .thenReturn(minimumPriceAdjustment);
    when(animalsAdjustmentLimitsProvider.getMinimumAdjustment(any(Money.class)))
        .thenReturn(maximumPriceAdjustment);

    Money computedAdjustment = subject.compute(QUOTE_FORM, BASE_PRICE);

    assertEquals(MAXIMUM_PRICE_ADJUSTMENT, computedAdjustment);
  }

  @Test
  public void computingFormulaPart_shouldCapAdjustmentToMaximumAdjustment() {
    when(animalsAdjustmentProvider.getAdjustment(any(AnimalBreed.class), any(Integer.class)))
        .thenReturn(maximumPriceAdjustment);
    when(animalsAdjustmentLimitsProvider.getMaximumAdjustment(any(Money.class)))
        .thenReturn(minimumPriceAdjustment);

    Money computedAdjustment = subject.compute(QUOTE_FORM, BASE_PRICE);

    assertEquals(MINIMUM_PRICE_ADJUSTMENT, computedAdjustment);
  }
}
