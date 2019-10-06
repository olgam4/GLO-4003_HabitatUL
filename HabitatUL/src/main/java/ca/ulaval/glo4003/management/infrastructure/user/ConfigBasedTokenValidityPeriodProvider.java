package ca.ulaval.glo4003.management.infrastructure.user;

import ca.ulaval.glo4003.management.domain.user.token.TokenValidityPeriodProvider;
import ca.ulaval.glo4003.shared.infrastructure.ConfigFileReader;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Properties;

public class ConfigBasedTokenValidityPeriodProvider implements TokenValidityPeriodProvider {
  private Duration tokenValidityPeriod;

  public ConfigBasedTokenValidityPeriodProvider() {
    Properties properties = ConfigFileReader.readProperties("config.properties");
    int tokenValidityPeriodTemporalAmount =
        Integer.parseInt(properties.getProperty("token.validityPeriod.temporalAmount"));
    ChronoUnit tokenValidityPeriodTemporalUnit =
        ChronoUnit.valueOf(properties.getProperty("token.validityPeriod.temporalUnit"));
    Duration tokenValidityPeriod =
        Duration.of(tokenValidityPeriodTemporalAmount, tokenValidityPeriodTemporalUnit);
    this.tokenValidityPeriod = tokenValidityPeriod;
  }

  @Override
  public Duration getTokenValidityPeriod() {
    return tokenValidityPeriod;
  }
}
