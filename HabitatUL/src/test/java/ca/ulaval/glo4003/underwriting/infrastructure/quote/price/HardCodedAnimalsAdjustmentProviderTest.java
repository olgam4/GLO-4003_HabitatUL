package ca.ulaval.glo4003.underwriting.infrastructure.quote.price;

import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.AnimalBreed;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.MultiplicativeQuotePriceAdjustment;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.NullQuotePriceAdjustment;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;
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
  private static final int COUNT = Faker.instance().number().randomDigit();

  private HardCodedAnimalsAdjustmentProvider subject;
  private AnimalBreed animalBreed;
  private int animalCount;
  private QuotePriceAdjustment expectedAdjustment;

  public HardCodedAnimalsAdjustmentProviderTest(
      String title,
      AnimalBreed animalBreed,
      int animalCount,
      QuotePriceAdjustment expectedAdjustment) {
    this.animalBreed = animalBreed;
    this.animalCount = animalCount;
    this.expectedAdjustment = expectedAdjustment;
  }

  @Parameterized.Parameters(name = PARAMETERIZED_TEST_TITLE)
  public static Collection parameters() {
    return Arrays.asList(
        new Object[][] {
          {
            "with 1 cat should compute associated adjustment",
            AnimalBreed.CAT,
            1,
            new MultiplicativeQuotePriceAdjustment(0.01f)
          },
          {
            "with 1 dog should compute associated adjustment",
            AnimalBreed.DOG,
            1,
            new MultiplicativeQuotePriceAdjustment(0.05f)
          },
          {
            "with 1 gold fish should compute associated adjustment",
            AnimalBreed.GOLD_FISH,
            1,
            new MultiplicativeQuotePriceAdjustment(-0.01f)
          },
          {
            "with 1 snake should compute associated adjustment",
            AnimalBreed.SNAKE,
            1,
            new MultiplicativeQuotePriceAdjustment(1f)
          },
          {
            "with multiple animals of the same breed should take into account their multiplicity",
            AnimalBreed.CAT,
            COUNT,
            new MultiplicativeQuotePriceAdjustment(0.01f * COUNT)
          },
          {
            "with other animal should compute null adjustment",
            AnimalBreed.OTHER,
            1,
            new NullQuotePriceAdjustment()
          },
        });
  }

  @Before
  public void setUp() {
    subject = new HardCodedAnimalsAdjustmentProvider();
  }

  @Test
  public void gettingAdjustment() {
    QuotePriceAdjustment adjustment = subject.getAdjustment(animalBreed, animalCount);

    assertEquals(expectedAdjustment, adjustment);
  }
}
