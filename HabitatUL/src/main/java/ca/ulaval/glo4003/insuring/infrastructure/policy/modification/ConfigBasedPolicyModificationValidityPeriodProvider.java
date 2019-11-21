package ca.ulaval.glo4003.insuring.infrastructure.policy.modification;

import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationValidityPeriodProvider;
import ca.ulaval.glo4003.shared.infrastructure.io.ConfigFileReader;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Properties;

public class ConfigBasedPolicyModificationValidityPeriodProvider
    implements PolicyModificationValidityPeriodProvider {
  static final String FILE_PATH = "config.properties";
  static final String PERIOD_TEMPORAL_AMOUNT_KEY =
      "policy.modificationValidityPeriod.temporalAmount";
  static final String PERIOD_TEMPORAL_AMOUNT_DEFAULT_VALUE = "5";
  static final String PERIOD_TEMPORAL_UNIT_KEY = "policy.modificationValidityPeriod.temporalUnit";
  static final String PERIOD_TEMPORAL_UNIT_DEFAULT_VALUE = "DAYS";
  private Duration policyModificationValidityPeriod;

  public ConfigBasedPolicyModificationValidityPeriodProvider() {
    this(new ConfigFileReader());
  }

  public ConfigBasedPolicyModificationValidityPeriodProvider(ConfigFileReader configFileReader) {
    this.policyModificationValidityPeriod = parsePolicyModificationValidityPeriod(configFileReader);
  }

  private Duration parsePolicyModificationValidityPeriod(ConfigFileReader configFileReader) {
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
  public Duration getPolicyModificationValidityPeriod() {
    return policyModificationValidityPeriod;
  }
}
