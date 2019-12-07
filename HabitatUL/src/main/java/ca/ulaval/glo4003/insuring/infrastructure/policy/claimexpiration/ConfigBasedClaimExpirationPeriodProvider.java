package ca.ulaval.glo4003.insuring.infrastructure.policy.claimexpiration;

import ca.ulaval.glo4003.insuring.application.policy.claimexpiration.ClaimExpirationPeriodProvider;
import ca.ulaval.glo4003.shared.infrastructure.io.ConfigFileReader;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Properties;

public class ConfigBasedClaimExpirationPeriodProvider implements ClaimExpirationPeriodProvider {
  static final String FILE_PATH = "config.properties";
  static final String PERIOD_TEMPORAL_AMOUNT_KEY = "claim.expirationPeriod.temporalAmount";
  static final String PERIOD_TEMPORAL_AMOUNT_DEFAULT_VALUE = "5";
  static final String PERIOD_TEMPORAL_UNIT_KEY = "claim.expirationPeriod.temporalUnit";
  static final String PERIOD_TEMPORAL_UNIT_DEFAULT_VALUE = "DAYS";

  private Duration claimExpirationPeriod;

  public ConfigBasedClaimExpirationPeriodProvider() {
    this(new ConfigFileReader());
  }

  public ConfigBasedClaimExpirationPeriodProvider(ConfigFileReader configFileReader) {
    this.claimExpirationPeriod = parseClaimExpirationPeriod(configFileReader);
  }

  private Duration parseClaimExpirationPeriod(ConfigFileReader configFileReader) {
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
  public Duration getClaimExpirationPeriod() {
    return claimExpirationPeriod;
  }
}
