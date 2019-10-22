package ca.ulaval.glo4003.underwriting.domain.quote.price.formulapart;

import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.helper.quote.form.QuoteFormGenerator;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.AnimalBreed;
import ca.ulaval.glo4003.underwriting.domain.quote.price.AnimalsAdjustmentProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Map;

import static ca.ulaval.glo4003.underwriting.domain.quote.price.formulapart.AnimalsFormulaPart.MAXIMUM_ADJUSTMENT;
import static ca.ulaval.glo4003.underwriting.domain.quote.price.formulapart.AnimalsFormulaPart.MINIMUM_ADJUSTMENT;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AnimalsFormulaPartTest {
  private static final Money BASE_PRICE = MoneyGenerator.create();
  private static final Money PRICE_ADJUSTMENT = new Money(new Amount(BigDecimal.valueOf(0.001f)));
  private static final QuoteForm QUOTE_FORM = QuoteFormGenerator.createQuoteForm();
  private static final Map<AnimalBreed, Integer> ANIMAL_COLLECTION =
      QUOTE_FORM.getPersonalProperty().getAnimals().getCollection();

  @Mock private AnimalsAdjustmentProvider animalsAdjustmentProvider;
  @Mock private QuotePriceAdjustment quotePriceAdjustment;

  private AnimalsFormulaPart subject;

  @Before
  public void setUp() {
    when(animalsAdjustmentProvider.getAdjustment(any(AnimalBreed.class), any(Integer.class)))
        .thenReturn(quotePriceAdjustment);
    when(quotePriceAdjustment.apply(any(Money.class))).thenReturn(PRICE_ADJUSTMENT);
    subject = new AnimalsFormulaPart(animalsAdjustmentProvider);
  }

  @Test
  public void computingFormulaPart_shouldGetAnimalsAdjustments() {
    subject.compute(QUOTE_FORM, BASE_PRICE);

    verify(animalsAdjustmentProvider, times(ANIMAL_COLLECTION.size())).getAdjustment(any(), any());
  }

  @Test
  public void computingFormulaPart_shouldCombineAdjustments() {
    Money computedAdjustment = subject.compute(QUOTE_FORM, BASE_PRICE);

    Money expectedAdjustment = PRICE_ADJUSTMENT.multiply(ANIMAL_COLLECTION.size());
    assertEquals(expectedAdjustment, computedAdjustment);
  }

  @Test
  public void computingFormulaPart_shouldCapAdjustmentToMaximumAdjustment() {
    when(animalsAdjustmentProvider.getAdjustment(any(AnimalBreed.class), any(Integer.class)))
        .thenReturn(MAXIMUM_ADJUSTMENT);

    Money computedAdjustment = subject.compute(QUOTE_FORM, BASE_PRICE);

    Money expectedAdjustment = MAXIMUM_ADJUSTMENT.apply(BASE_PRICE);
    assertEquals(expectedAdjustment, computedAdjustment);
  }

  @Test
  public void computingFormulaPart_shouldCapAdjustmentToMinimumAdjustment() {
    when(animalsAdjustmentProvider.getAdjustment(any(AnimalBreed.class), any(Integer.class)))
        .thenReturn(MINIMUM_ADJUSTMENT);

    Money computedAdjustment = subject.compute(QUOTE_FORM, BASE_PRICE);

    Money expectedAdjustment = MINIMUM_ADJUSTMENT.apply(BASE_PRICE);
    assertEquals(expectedAdjustment, computedAdjustment);
  }
}
