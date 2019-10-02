package ca.ulaval.glo4003.context;

import ca.ulaval.glo4003.gateway.domain.user.PasswordValidator;
import ca.ulaval.glo4003.gateway.domain.user.UserRepository;
import ca.ulaval.glo4003.gateway.infrastructure.user.DummyPasswordValidator;
import ca.ulaval.glo4003.gateway.persistence.user.InMemoryUserRepository;
import ca.ulaval.glo4003.mediator.BoundedContextMediator;
import ca.ulaval.glo4003.mediator.ConcreteBoundedContextMediator;
import ca.ulaval.glo4003.shared.domain.ClockProvider;
import ca.ulaval.glo4003.shared.infrastructure.SystemUtcClockProvider;
import ca.ulaval.glo4003.underwriting.domain.QuotePremiumCalculator;
import ca.ulaval.glo4003.underwriting.domain.premium.Premium;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteRepository;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteValidityPeriodProvider;
import ca.ulaval.glo4003.underwriting.infrastructure.DummyQuotePremiumCalculator;
import ca.ulaval.glo4003.underwriting.infrastructure.quote.ConfigBasedQuoteValidityPeriodProvider;
import ca.ulaval.glo4003.underwriting.persistence.quote.EventPublisherQuoteRepositoryWrapper;
import ca.ulaval.glo4003.underwriting.persistence.quote.InMemoryQuoteRepository;

import java.math.BigDecimal;

public class ProdContext implements Context {
  @Override
  public void execute() {
    BoundedContextMediator mediator = new ConcreteBoundedContextMediator();
    registerGeneralServices();
    registerGatewayServices();
    registerUnderwritingServices(mediator);
  }

  private void registerGeneralServices() {
    ServiceLocator.register(ClockProvider.class, new SystemUtcClockProvider());
  }

  private void registerGatewayServices() {
    ServiceLocator.register(PasswordValidator.class, new DummyPasswordValidator());
    ServiceLocator.register(UserRepository.class, new InMemoryUserRepository());
  }

  private void registerUnderwritingServices(BoundedContextMediator mediator) {
    Premium hardCodedPremium = new Premium(BigDecimal.valueOf(534.32423423423));
    ServiceLocator.register(
        QuotePremiumCalculator.class, new DummyQuotePremiumCalculator(hardCodedPremium));
    ServiceLocator.register(
        QuoteValidityPeriodProvider.class, new ConfigBasedQuoteValidityPeriodProvider());
    ServiceLocator.register(
        QuoteRepository.class,
        new EventPublisherQuoteRepositoryWrapper(new InMemoryQuoteRepository(), mediator));
  }
}
