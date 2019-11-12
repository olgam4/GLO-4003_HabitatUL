package ca.ulaval.glo4003.coverage.infrastructure.premium.formulapart.bicycleprice;

import ca.ulaval.glo4003.coverage.domain.premium.adjustment.MultiplicativePremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.NullPremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.helper.shared.MoneyGenerator;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

import static ca.ulaval.glo4003.coverage.infrastructure.premium.formulapart.bicycleprice.HardCodedBicyclePriceAdjustmentProvider.MINIMUM_BICYCLE_PRICE_BEFORE_SURCHARGE_VALUE;
import static ca.ulaval.glo4003.helper.shared.ParameterizedTestHelper.PARAMETERIZED_TEST_TITLE;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class HardCodedBicyclePriceAdjustmentProviderTest {
  private HardCodedBicyclePriceAdjustmentProvider subject;
  private Amount bicyclePrice;
  private PremiumAdjustment expectedAdjustment;

  public HardCodedBicyclePriceAdjustmentProviderTest(
      String title, Amount bicyclePrice, PremiumAdjustment expectedAdjustment) {
    this.bicyclePrice = bicyclePrice;
    this.expectedAdjustment = expectedAdjustment;
  }

  @Parameterized.Parameters(name = PARAMETERIZED_TEST_TITLE)
  public static Collection parameters() {
    return Arrays.asList(
        new Object[][] {
          {
            "without bicycle price should compute null adjustment",
            null,
            new NullPremiumAdjustment()
          },
          {
            "with bicycle price smaller than 0 should compute null adjustment",
            MoneyGenerator.createAmountSmallerThan(Amount.ZERO),
            new NullPremiumAdjustment()
          },
          {
            "with bicycle price equal to 0 should compute null adjustment",
            MoneyGenerator.createAmountSmallerThan(Amount.ZERO),
            new NullPremiumAdjustment()
          },
          {
            String.format(
                "with bicycle price smaller than %s should compute null adjustment",
                MINIMUM_BICYCLE_PRICE_BEFORE_SURCHARGE_VALUE),
            MoneyGenerator.createAmountSmallerThan(
                new Amount(BigDecimal.valueOf(MINIMUM_BICYCLE_PRICE_BEFORE_SURCHARGE_VALUE))),
            new NullPremiumAdjustment()
          },
          {
            String.format(
                "with bicycle price equal to %s should compute associated adjustment",
                MINIMUM_BICYCLE_PRICE_BEFORE_SURCHARGE_VALUE),
            new Amount(BigDecimal.valueOf(MINIMUM_BICYCLE_PRICE_BEFORE_SURCHARGE_VALUE)),
            new MultiplicativePremiumAdjustment(0.01f)
          },
          {
            String.format(
                "with bicycle price greater than %s should compute associated adjustment",
                MINIMUM_BICYCLE_PRICE_BEFORE_SURCHARGE_VALUE),
            MoneyGenerator.createAmountGreaterThan(
                new Amount(BigDecimal.valueOf(MINIMUM_BICYCLE_PRICE_BEFORE_SURCHARGE_VALUE))),
            new MultiplicativePremiumAdjustment(0.01f)
          },
        });
  }

  @Before
  public void setUp() {
    subject = new HardCodedBicyclePriceAdjustmentProvider();
  }

  @Test
  public void gettingAdjustment() {
    PremiumAdjustment adjustment = subject.getAdjustment(bicyclePrice);

    assertEquals(expectedAdjustment, adjustment);
  }
}
