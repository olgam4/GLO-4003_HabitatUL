package ca.ulaval.glo4003.underwriting.infrastructure.quote.price;

import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.AnimalBreed;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.MultiplicativeQuotePriceAdjustment;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.NoQuotePriceAdjustment;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HardCodedAnimalsAdjustmentProviderTest {
  private static final int COUNT = Faker.instance().number().randomDigit();

  private HardCodedAnimalsAdjustmentProvider subject;

  @Before
  public void setUp() {
    subject = new HardCodedAnimalsAdjustmentProvider();
  }

  @Test
  public void gettingAdjustment_withKnownAnimalBreed_shouldProvideCorrespondingAdjustment() {
    validateScenario(AnimalBreed.CAT, 1, 0.01f);
    validateScenario(AnimalBreed.DOG, 1, 0.05f);
    validateScenario(AnimalBreed.GOLD_FISH, 1, -0.01f);
    validateScenario(AnimalBreed.SNAKE, 1, 1f);
  }

  @Test
  public void gettingAdjustment_withKnownAnimalBreed_shouldAdjustAccordingToCount() {
    validateScenario(AnimalBreed.CAT, COUNT, 0.01f * COUNT);
  }

  @Test
  public void gettingAdjustment_withOtherAnimalBreed_shouldProvideNoAdjustment() {
    QuotePriceAdjustment adjustment = subject.getAdjustment(AnimalBreed.OTHER, COUNT);

    QuotePriceAdjustment expectedAdjustment = new NoQuotePriceAdjustment();
    assertEquals(expectedAdjustment, adjustment);
  }

  private void validateScenario(AnimalBreed animalBreed, int animalCount, float expectedFactor) {
    QuotePriceAdjustment adjustment = subject.getAdjustment(animalBreed, animalCount);

    QuotePriceAdjustment expectedAdjustment =
        new MultiplicativeQuotePriceAdjustment(expectedFactor);
    assertEquals(expectedAdjustment, adjustment);
  }
}
