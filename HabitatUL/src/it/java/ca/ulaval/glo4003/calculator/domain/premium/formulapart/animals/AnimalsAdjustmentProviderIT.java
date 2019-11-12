package ca.ulaval.glo4003.calculator.domain.premium.formulapart.animals;

import ca.ulaval.glo4003.calculator.domain.form.personalproperty.AnimalBreed;
import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.helper.calculator.premium.QuotePremiumInputGenerator.createAnimalBreed;
import static org.junit.Assert.assertNotNull;

public abstract class AnimalsAdjustmentProviderIT {
  private static final AnimalBreed BREED = createAnimalBreed();
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
