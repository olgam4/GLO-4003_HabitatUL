package ca.ulaval.glo4003.underwriting.domain.quote.price.formulapart;

import ca.ulaval.glo4003.helper.MoneyBuilder;
import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.helper.quote.form.QuoteFormGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.AnimalBreed;
import ca.ulaval.glo4003.underwriting.domain.quote.price.AnimalsAdjustmentLimitsProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.AnimalsAdjustmentProvider;
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
  private static final Money BASE_PRICE = MoneyGenerator.create();
  private static final Money PRICE_ADJUSTMENT = MoneyGenerator.create();
  private static final Money MIN_PRICE_ADJUSTMENT =
      MoneyBuilder.aMoney().withSmallerAmountThan(PRICE_ADJUSTMENT).build();
  private static final Money MAX_PRICE_ADJUSTMENT =
      MoneyBuilder.aMoney().withBiggerAmountThan(PRICE_ADJUSTMENT).build();
  private static final QuoteForm QUOTE_FORM = QuoteFormGenerator.createQuoteForm();
  private static final Map<AnimalBreed, Integer> ANIMAL_COLLECTION =
      QUOTE_FORM.getPersonalProperty().getAnimals().getCollection();

  @Mock private AnimalsAdjustmentProvider animalsAdjustmentProvider;
  @Mock private AnimalsAdjustmentLimitsProvider animalsAdjustmentLimitsProvider;
  @Mock private QuotePriceAdjustment quotePriceAdjustment;
  @Mock private QuotePriceAdjustment minPriceAdjustment;
  @Mock private QuotePriceAdjustment maxPriceAdjustment;

  private AnimalsFormulaPart subject;

  @Before
  public void setUp() {
    when(animalsAdjustmentProvider.getAdjustment(any(AnimalBreed.class), any(Integer.class)))
        .thenReturn(quotePriceAdjustment);
    when(animalsAdjustmentLimitsProvider.getMin()).thenReturn(minPriceAdjustment);
    when(animalsAdjustmentLimitsProvider.getMax()).thenReturn(maxPriceAdjustment);
    when(quotePriceAdjustment.apply(any(Money.class))).thenReturn(PRICE_ADJUSTMENT);
    when(minPriceAdjustment.apply(any(Money.class))).thenReturn(MIN_PRICE_ADJUSTMENT);
    when(maxPriceAdjustment.apply(any(Money.class))).thenReturn(MAX_PRICE_ADJUSTMENT);
    subject = new AnimalsFormulaPart(animalsAdjustmentProvider, animalsAdjustmentLimitsProvider);
  }

  @Test
  public void computingFormulaPart_shouldGetAnimalsAdjustments() {
    subject.compute(QUOTE_FORM, BASE_PRICE);

    verify(animalsAdjustmentProvider, times(ANIMAL_COLLECTION.size())).getAdjustment(any(), any());
  }

  @Test
  public void computingFormulaPart_shouldCombineAdjustments() {
    when(minPriceAdjustment.apply(any(Money.class))).thenReturn(Money.ZERO);
    when(maxPriceAdjustment.apply(any(Money.class))).thenReturn(MoneyGenerator.createMaxValue());

    Money computedAdjustment = subject.compute(QUOTE_FORM, BASE_PRICE);

    Money expectedAdjustment = PRICE_ADJUSTMENT.multiply(ANIMAL_COLLECTION.size());
    assertEquals(expectedAdjustment, computedAdjustment);
  }

  @Test
  public void computingFormulaPart_shouldCapAdjustmentToMinimumAdjustment() {
    when(animalsAdjustmentProvider.getAdjustment(any(AnimalBreed.class), any(Integer.class)))
        .thenReturn(minPriceAdjustment);
    when(animalsAdjustmentLimitsProvider.getMin()).thenReturn(maxPriceAdjustment);

    Money computedAdjustment = subject.compute(QUOTE_FORM, BASE_PRICE);

    assertEquals(MAX_PRICE_ADJUSTMENT, computedAdjustment);
  }

  @Test
  public void computingFormulaPart_shouldCapAdjustmentToMaximumAdjustment() {
    when(animalsAdjustmentProvider.getAdjustment(any(AnimalBreed.class), any(Integer.class)))
        .thenReturn(maxPriceAdjustment);
    when(animalsAdjustmentLimitsProvider.getMax()).thenReturn(minPriceAdjustment);

    Money computedAdjustment = subject.compute(QUOTE_FORM, BASE_PRICE);

    assertEquals(MIN_PRICE_ADJUSTMENT, computedAdjustment);
  }
}
