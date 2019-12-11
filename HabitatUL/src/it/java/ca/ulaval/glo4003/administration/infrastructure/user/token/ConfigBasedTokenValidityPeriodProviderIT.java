package ca.ulaval.glo4003.administration.infrastructure.user.token;

import ca.ulaval.glo4003.administration.domain.user.token.TokenValidityPeriodProvider;
import ca.ulaval.glo4003.administration.domain.user.token.TokenValidityPeriodProviderIT;

public class ConfigBasedTokenValidityPeriodProviderIT extends TokenValidityPeriodProviderIT {
  @Override
  public TokenValidityPeriodProvider createSubject() {
    return new ConfigBasedTokenValidityPeriodProvider();
  }
}
