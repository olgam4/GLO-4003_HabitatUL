package ca.ulaval.glo4003.coverage.application.premium;

import ca.ulaval.glo4003.coverage.domain.premium.formula.bike.BasicBlockCoverageMaximumBikePriceProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bike.BikeEndorsementPremiumFormula;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bike.BikePremiumInput;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuoteBasicBlockPremiumFormula;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.helper.shared.MoneyGenerator;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.domain.money.Money;
import org.junit.Before;
import org.junit.Rule;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PremiumCalculatorTestSetUp {
  static final Money BASE_COVERAGE_PREMIUM = MoneyGenerator.createMoney();
  static final Money BIKE_ENDORSEMENT_PREMIUM = MoneyGenerator.createMoney();
  static final Amount BASE_COVERAGE_MAXIMUM_BIKE_PRICE = MoneyGenerator.createAmount();

  @Rule public MockitoRule mockitoRule = MockitoJUnit.rule().silent();
  @Mock protected QuoteBasicBlockPremiumFormula quoteBasicBlockPremiumFormula;
  @Mock protected BikeEndorsementPremiumFormula bikeEndorsementPremiumFormula;

  @Mock
  protected BasicBlockCoverageMaximumBikePriceProvider basicBlockCoverageMaximumBikePriceProvider;

  protected PremiumCalculator subject;

  @Before
  public void setUp() {
    when(quoteBasicBlockPremiumFormula.compute(any(QuotePremiumInput.class)))
        .thenReturn(BASE_COVERAGE_PREMIUM);
    when(bikeEndorsementPremiumFormula.compute(any(BikePremiumInput.class)))
        .thenReturn(BIKE_ENDORSEMENT_PREMIUM);
    when(basicBlockCoverageMaximumBikePriceProvider.getMaximumBikePrice())
        .thenReturn(BASE_COVERAGE_MAXIMUM_BIKE_PRICE);
    subject =
        new PremiumCalculator(
            quoteBasicBlockPremiumFormula,
            bikeEndorsementPremiumFormula,
            basicBlockCoverageMaximumBikePriceProvider);
  }
}
