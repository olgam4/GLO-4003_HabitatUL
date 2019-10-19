package ca.ulaval.glo4003.administration.infrastructure.user;

import ca.ulaval.glo4003.administration.domain.user.token.TokenValidityPeriodProvider;
import ca.ulaval.glo4003.shared.infrastructure.ConfigFileReader;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Properties;

public class ConfigBasedTokenValidityPeriodProvider implements TokenValidityPeriodProvider {
  static final String FILE_PATH = "config.properties";
  static final String PERIOD_TEMPORAL_AMOUNT_KEY = "token.validityPeriod.temporalAmount";
  static final String PERIOD_TEMPORAL_AMOUNT_DEFAULT_VALUE = "3";
  static final String PERIOD_TEMPORAL_UNIT_KEY = "token.validityPeriod.temporalUnit";
  static final String PERIOD_TEMPORAL_UNIT_DEFAULT_VALUE = "HOURS";

  private final Duration tokenValidityPeriod;

  public ConfigBasedTokenValidityPeriodProvider() {
    this(new ConfigFileReader());
  }

  public ConfigBasedTokenValidityPeriodProvider(ConfigFileReader configFileReader) {
    this.tokenValidityPeriod = parseTokenValidityPeriod(configFileReader);
  }

  private Duration parseTokenValidityPeriod(ConfigFileReader configFileReader) {
    Properties properties = configFileReader.read(FILE_PATH);
    String temporalAmountValue =
        Optional.ofNullable(properties.getProperty(PERIOD_TEMPORAL_AMOUNT_KEY))
            .orElse(PERIOD_TEMPORAL_AMOUNT_DEFAULT_VALUE);
    int tokenValidityPeriodTemporalAmount = Integer.parseInt(temporalAmountValue);
    String temporalUnit =
        Optional.ofNullable(properties.getProperty(PERIOD_TEMPORAL_UNIT_KEY))
            .orElse(PERIOD_TEMPORAL_UNIT_DEFAULT_VALUE);
    ChronoUnit tokenValidityPeriodTemporalUnit = ChronoUnit.valueOf(temporalUnit);
    return Duration.of(tokenValidityPeriodTemporalAmount, tokenValidityPeriodTemporalUnit);
  }

  @Override
  public Duration getTokenValidityPeriod() {
    return tokenValidityPeriod;
  }
}
