package ca.ulaval.glo4003.calculator.domain.premium.formulapart.animals;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.input.AnimalBreedInput;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.helper.premium.QuotePremiumInputGenerator.createAnimalBreedInput;
import static org.junit.Assert.assertNotNull;

public abstract class AnimalsAdjustmentProviderIT {
  private static final AnimalBreedInput BREED = createAnimalBreedInput();
  private static final int COUNT = Faker.instance().number().randomDigit();

  private AnimalsAdjustmentProvider subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void gettingAdjustment_shouldProvideAdjustment() {
    PremiumAdjustment adjustment = subject.getAdjustment(BREED, COUNT);

    assertNotNull(adjustment);
  }

  public abstract AnimalsAdjustmentProvider createSubject();
}
