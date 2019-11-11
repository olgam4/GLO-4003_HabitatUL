package ca.ulaval.glo4003.calculator.application.premium;

import ca.ulaval.glo4003.calculator.domain.premium.detail.BasicBlockCoveragePremiumDetail;
import ca.ulaval.glo4003.calculator.domain.premium.detail.BikeEndorsementPremiumDetail;
import ca.ulaval.glo4003.calculator.domain.premium.detail.PremiumDetail;
import ca.ulaval.glo4003.calculator.domain.premium.detail.PremiumDetails;
import ca.ulaval.glo4003.calculator.domain.premium.formula.bike.BikePremiumInput;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.helper.calculator.BikePremiumInputGenerator;
import ca.ulaval.glo4003.helper.calculator.QuotePremiumInputBuilder;
import ca.ulaval.glo4003.helper.calculator.QuotePremiumInputGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static ca.ulaval.glo4003.helper.ParameterizedTestHelper.PARAMETERIZED_TEST_TITLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

@RunWith(Enclosed.class)
public class PremiumCalculatorTest extends PremiumCalculatorTestSetUp {
  private static final QuotePremiumInput QUOTE_PREMIUM_INPUT = QuotePremiumInputGenerator.create();
  private static final BikePremiumInput BIKE_PREMIUM_INPUT = BikePremiumInputGenerator.create();

  @Test
  public void computingBikeEndorsementPremium_shouldComputeBikeEndorsementPremiumFormula() {
    subject.computeBikeEndorsementPremium(BIKE_PREMIUM_INPUT);

    verify(bikeEndorsementPremiumFormula).compute(BIKE_PREMIUM_INPUT);
  }

  @Test
  public void computingBikeEndorsementPremium_shouldReturnComputedPremium() {
    Money endorsementPremium = subject.computeBikeEndorsementPremium(BIKE_PREMIUM_INPUT);

    assertEquals(BIKE_ENDORSEMENT_PREMIUM, endorsementPremium);
  }

  @RunWith(Parameterized.class)
  public static class computingQuotePremiumTest extends PremiumCalculatorTestSetUp {
    private QuotePremiumInput quotePremiumInput;
    private List<PremiumDetail> expectedAdditionalPremiumDetails;

    public computingQuotePremiumTest(
        String title,
        QuotePremiumInput quotePremiumInput,
        List<PremiumDetail> expectedAdditionalPremiumDetails) {
      this.quotePremiumInput = quotePremiumInput;
      this.expectedAdditionalPremiumDetails = expectedAdditionalPremiumDetails;
    }

    @Parameterized.Parameters(name = PARAMETERIZED_TEST_TITLE)
    public static Collection parameters() {
      return Arrays.asList(
          new Object[][] {
            {
              "without bike price should not add bike endorsement",
              QuotePremiumInputBuilder.aQuotePremiumInput().withoutBikePrice().build(),
              Collections.emptyList()
            },
            {
              "with bike price covered by base coverage should not add bike endorsement",
              QuotePremiumInputBuilder.aQuotePremiumInput()
                  .withBikePrice(
                      MoneyGenerator.createAmountSmallerThan(BASE_COVERAGE_MAXIMUM_BIKE_PRICE))
                  .build(),
              Collections.emptyList()
            },
            {
              "with bike price exceeding base coverage should add bike endorsement",
              QuotePremiumInputBuilder.aQuotePremiumInput()
                  .withBikePrice(
                      MoneyGenerator.createAmountGreaterThan(BASE_COVERAGE_MAXIMUM_BIKE_PRICE))
                  .build(),
              Arrays.asList(new BikeEndorsementPremiumDetail(BIKE_ENDORSEMENT_PREMIUM))
            }
          });
    }

    @Test
    public void computingQuotePremium_shouldComputeQuoteBasicBlockPremiumFormula() {
      subject.computeQuotePremium(QUOTE_PREMIUM_INPUT);

      verify(quoteBasicBlockPremiumFormula).compute(QUOTE_PREMIUM_INPUT);
    }

    @Test
    public void computingQuotePremium_shouldReturnCorrespondingPremiumDetails() {
      PremiumDetails premiumDetails = subject.computeQuotePremium(quotePremiumInput);

      BasicBlockCoveragePremiumDetail basicBlockCoveragePremiumDetail =
          new BasicBlockCoveragePremiumDetail(BASE_COVERAGE_PREMIUM);
      assertTrue(premiumDetails.getCollection().contains(basicBlockCoveragePremiumDetail));
      assertTrue(premiumDetails.getCollection().containsAll(expectedAdditionalPremiumDetails));
    }
  }
}
