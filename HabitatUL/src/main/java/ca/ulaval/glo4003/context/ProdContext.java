package ca.ulaval.glo4003.context;

import ca.ulaval.glo4003.gateway.domain.user.PasswordValidator;
import ca.ulaval.glo4003.gateway.domain.user.UserRepository;
import ca.ulaval.glo4003.gateway.infrastructure.user.DummyPasswordValidator;
import ca.ulaval.glo4003.gateway.persistence.user.InMemoryUserRepository;
import ca.ulaval.glo4003.shared.domain.ClockProvider;
import ca.ulaval.glo4003.shared.infrastructure.SystemUtcClockProvider;
import ca.ulaval.glo4003.underwriting.domain.premium.Premium;
import ca.ulaval.glo4003.underwriting.domain.premium.PremiumCalculator;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteRepository;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteValidityPeriodProvider;
import ca.ulaval.glo4003.underwriting.infrastructure.premium.DummyPremiumCalculator;
import ca.ulaval.glo4003.underwriting.infrastructure.quote.ConfigBasedQuoteValidityPeriodProvider;
import ca.ulaval.glo4003.underwriting.persistence.quote.InMemoryQuoteRepository;

import java.math.BigDecimal;

public class ProdContext implements Context {
  @Override
  public void execute() {
    // General
    ServiceLocator.register(ClockProvider.class, new SystemUtcClockProvider());

    // Gateway
    ServiceLocator.register(PasswordValidator.class, new DummyPasswordValidator());
    ServiceLocator.register(UserRepository.class, new InMemoryUserRepository());

    // Underwriting
    Premium hardCodedPremium = new Premium(new BigDecimal(534.32423423423));
    ServiceLocator.register(PremiumCalculator.class, new DummyPremiumCalculator(hardCodedPremium));
    ServiceLocator.register(
        QuoteValidityPeriodProvider.class, new ConfigBasedQuoteValidityPeriodProvider());
    ServiceLocator.register(QuoteRepository.class, new InMemoryQuoteRepository());
  }
}
