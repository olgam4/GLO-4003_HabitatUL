package ca.ulaval.glo4003.calculator.application.premium;

import ca.ulaval.glo4003.calculator.domain.premium.formula.bike.BaseCoverageMaximumBikePriceProvider;
import ca.ulaval.glo4003.calculator.domain.premium.formula.bike.BikeAddendaPremiumFormula;
import ca.ulaval.glo4003.calculator.domain.premium.formula.bike.BikePremiumInput;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.QuoteBaseCoveragePremiumFormula;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.helper.MoneyGenerator;
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
  static final Money BIKE_ADDENDA_PREMIUM = MoneyGenerator.createMoney();
  static final Amount BASE_COVERAGE_MAXIMUM_BIKE_PRICE = MoneyGenerator.createAmount();

  @Rule public MockitoRule mockitoRule = MockitoJUnit.rule().silent();
  @Mock protected QuoteBaseCoveragePremiumFormula quoteBaseCoveragePremiumFormula;
  @Mock protected BikeAddendaPremiumFormula bikeAddendaPremiumFormula;
  @Mock protected BaseCoverageMaximumBikePriceProvider baseCoverageMaximumBikePriceProvider;

  protected PremiumCalculator subject;

  @Before
  public void setUp() {
    when(quoteBaseCoveragePremiumFormula.compute(any(QuotePremiumInput.class)))
        .thenReturn(BASE_COVERAGE_PREMIUM);
    when(bikeAddendaPremiumFormula.compute(any(BikePremiumInput.class)))
        .thenReturn(BIKE_ADDENDA_PREMIUM);
    when(baseCoverageMaximumBikePriceProvider.getMaximumBikePrice())
        .thenReturn(BASE_COVERAGE_MAXIMUM_BIKE_PRICE);
    subject =
        new PremiumCalculator(
            quoteBaseCoveragePremiumFormula,
            bikeAddendaPremiumFormula,
            baseCoverageMaximumBikePriceProvider);
  }
}
