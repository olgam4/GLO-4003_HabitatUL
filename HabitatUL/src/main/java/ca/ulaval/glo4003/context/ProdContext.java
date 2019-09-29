package ca.ulaval.glo4003.context;

import ca.ulaval.glo4003.shared.domain.ClockProvider;
import ca.ulaval.glo4003.shared.infrastructure.SystemUtcClockProvider;
import ca.ulaval.glo4003.underwriting.domain.Premium;
import ca.ulaval.glo4003.underwriting.domain.PremiumCalculator;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteRepository;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteValidityPeriodProvider;
import ca.ulaval.glo4003.underwriting.infrastructure.DummyPremiumCalculator;
import ca.ulaval.glo4003.underwriting.infrastructure.quote.ConfigBasedQuoteValidityPeriodProvider;
import ca.ulaval.glo4003.underwriting.persistence.quote.InMemoryQuoteRepository;

import java.math.BigDecimal;

public class ProdContext implements Context {
  @Override
  public void execute() {
    Premium hardCodedPremium = new Premium(new BigDecimal(534.32423423423));
    ServiceLocator.register(PremiumCalculator.class, new DummyPremiumCalculator(hardCodedPremium));
    ServiceLocator.register(
        QuoteValidityPeriodProvider.class, new ConfigBasedQuoteValidityPeriodProvider());
    ServiceLocator.register(ClockProvider.class, new SystemUtcClockProvider());
    ServiceLocator.register(QuoteRepository.class, new InMemoryQuoteRepository());
  }
}
