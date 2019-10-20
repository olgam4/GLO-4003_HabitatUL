package ca.ulaval.glo4003.underwriting.domain.quote.price;

import ca.ulaval.glo4003.generator.EnumSampler;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.AnimalBreed;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public abstract class AnimalsAdjustmentProviderIT {
  private static final AnimalBreed ANIMAL_BREED = EnumSampler.sample(AnimalBreed.class);
  private static final int COUNT = Faker.instance().number().randomDigit();

  private AnimalsAdjustmentProvider subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void gettingAdjustment_shouldProvideAdjustment() {
    QuotePriceAdjustment adjustment = subject.getAdjustment(ANIMAL_BREED, COUNT);

    assertNotNull(adjustment);
  }

  public abstract AnimalsAdjustmentProvider createSubject();
}
