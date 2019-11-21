package ca.ulaval.glo4003.insuring.infrastructure.policy.renewal;

import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyCoveragePeriodProvider;
import ca.ulaval.glo4003.shared.infrastructure.io.ConfigFileReader;

import java.time.Period;
import java.util.Optional;
import java.util.Properties;

public class ConfigBasedPolicyCoveragePeriodProvider implements PolicyCoveragePeriodProvider {
  static final String FILE_PATH = "config.properties";
  static final String KEY = "policy.coveragePeriod.inMonths";
  static final String DEFAULT_VALUE = "12";

  private Period coveragePeriod;

  public ConfigBasedPolicyCoveragePeriodProvider() {
    this(new ConfigFileReader());
  }

  public ConfigBasedPolicyCoveragePeriodProvider(ConfigFileReader configFileReader) {
    Properties properties = configFileReader.read(FILE_PATH);
    String temporalAmountValue =
        Optional.ofNullable(properties.getProperty(KEY)).orElse(DEFAULT_VALUE);
    int periodInMonths = Integer.parseInt(temporalAmountValue);
    this.coveragePeriod = Period.ofMonths(periodInMonths);
  }

  @Override
  public Period getPolicyCoveragePeriod() {
    return coveragePeriod;
  }
}
