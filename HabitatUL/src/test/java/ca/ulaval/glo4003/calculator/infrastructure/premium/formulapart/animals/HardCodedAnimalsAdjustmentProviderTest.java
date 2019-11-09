package ca.ulaval.glo4003.calculator.infrastructure.premium.formulapart.animals;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.MultiplicativePremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.adjustment.NullPremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.AnimalBreedInput;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static ca.ulaval.glo4003.helper.ParameterizedTestHelper.PARAMETERIZED_TEST_TITLE;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class HardCodedAnimalsAdjustmentProviderTest {
  private static final Integer COUNT = Faker.instance().number().randomDigit();

  private HardCodedAnimalsAdjustmentProvider subject;
  private AnimalBreedInput breed;
  private Integer count;
  private PremiumAdjustment expectedAdjustment;

  public HardCodedAnimalsAdjustmentProviderTest(
      String title, AnimalBreedInput breed, Integer count, PremiumAdjustment expectedAdjustment) {
    this.breed = breed;
    this.count = count;
    this.expectedAdjustment = expectedAdjustment;
  }

  @Parameterized.Parameters(name = PARAMETERIZED_TEST_TITLE)
  public static Collection parameters() {
    return Arrays.asList(
        new Object[][] {
          {
            "without animal should compute null adjustment", null, null, new NullPremiumAdjustment()
          },
          {
            "with 1 cat should compute associated adjustment",
            AnimalBreedInput.CAT,
            1,
            new MultiplicativePremiumAdjustment(0.01f)
          },
          {
            "with 1 dog should compute associated adjustment",
            AnimalBreedInput.DOG,
            1,
            new MultiplicativePremiumAdjustment(0.05f)
          },
          {
            "with 1 gold fish should compute associated adjustment",
            AnimalBreedInput.GOLD_FISH,
            1,
            new MultiplicativePremiumAdjustment(-0.01f)
          },
          {
            "with 1 snake should compute associated adjustment",
            AnimalBreedInput.SNAKE,
            1,
            new MultiplicativePremiumAdjustment(1f)
          },
          {
            "with multiple animals of the same breed should take into account their multiplicity",
            AnimalBreedInput.CAT,
            COUNT,
            new MultiplicativePremiumAdjustment(0.01f * COUNT)
          },
          {
            "with other animal should compute null adjustment",
            AnimalBreedInput.OTHER,
            1,
            new NullPremiumAdjustment()
          },
        });
  }

  @Before
  public void setUp() {
    subject = new HardCodedAnimalsAdjustmentProvider();
  }

  @Test
  public void gettingAdjustment() {
    PremiumAdjustment adjustment = subject.getAdjustment(breed, count);

    assertEquals(expectedAdjustment, adjustment);
  }
}
