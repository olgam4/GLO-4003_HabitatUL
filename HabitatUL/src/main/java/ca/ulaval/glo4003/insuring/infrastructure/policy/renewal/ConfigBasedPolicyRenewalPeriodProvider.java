package ca.ulaval.glo4003.insuring.infrastructure.policy.renewal;

import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalPeriodProvider;
import ca.ulaval.glo4003.shared.infrastructure.io.ConfigFileReader;

import java.time.Period;
import java.util.Optional;
import java.util.Properties;

public class ConfigBasedPolicyRenewalPeriodProvider implements PolicyRenewalPeriodProvider {
  static final String FILE_PATH = "config.properties";
  static final String KEY = "policy.renewalPeriod.inDays";
  static final String DEFAULT_VALUE = "30";

  private Period renewalPeriod;

  public ConfigBasedPolicyRenewalPeriodProvider() {
    this(new ConfigFileReader());
  }

  public ConfigBasedPolicyRenewalPeriodProvider(ConfigFileReader configFileReader) {
    Properties properties = configFileReader.read(FILE_PATH);
    String temporalAmountValue =
        Optional.ofNullable(properties.getProperty(KEY)).orElse(DEFAULT_VALUE);
    int periodInDays = Integer.parseInt(temporalAmountValue);
    this.renewalPeriod = Period.ofDays(periodInDays);
  }

  @Override
  public Period getPolicyRenewalPeriod() {
    return renewalPeriod;
  }
}
