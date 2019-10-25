package ca.ulaval.glo4003.underwriting.infrastructure.quote;

import ca.ulaval.glo4003.shared.infrastructure.ConfigFileReader;
import ca.ulaval.glo4003.underwriting.domain.quote.EffectivePeriodProvider;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Properties;

public class ConfigBasedEffectivePeriodProvider implements EffectivePeriodProvider {
  static final String FILE_PATH = "config.properties";
  static final String PERIOD_TEMPORAL_AMOUNT_KEY = "quote.coveragePeriod.temporalAmount";
  static final String PERIOD_TEMPORAL_AMOUNT_DEFAULT_VALUE = "12";
  static final String PERIOD_TEMPORAL_UNIT_KEY = "quote.coveragePeriod.temporalUnit";
  static final String PERIOD_TEMPORAL_UNIT_DEFAULT_VALUE = "MONTHS";

  private java.time.Period effectivePeriod;

  public ConfigBasedEffectivePeriodProvider() {
    this(new ConfigFileReader());
  }

  public ConfigBasedEffectivePeriodProvider(ConfigFileReader configFileReader) {
    this.effectivePeriod = parseEffectivePeriod(configFileReader);
  }

  private java.time.Period parseEffectivePeriod(ConfigFileReader configFileReader) {
    Properties properties = configFileReader.read(FILE_PATH);
    String temporalAmountValue =
        Optional.ofNullable(properties.getProperty(PERIOD_TEMPORAL_AMOUNT_KEY))
            .orElse(PERIOD_TEMPORAL_AMOUNT_DEFAULT_VALUE);
    int coveragePeriodTemporalAmount = Integer.parseInt(temporalAmountValue);
    String temporalUnit =
        Optional.ofNullable(properties.getProperty(PERIOD_TEMPORAL_UNIT_KEY))
            .orElse(PERIOD_TEMPORAL_UNIT_DEFAULT_VALUE);
    ChronoUnit coveragePeriodTemporalUnit = ChronoUnit.valueOf(temporalUnit);
    return java.time.Period.from(
        Duration.of(coveragePeriodTemporalAmount, coveragePeriodTemporalUnit));
  }

  @Override
  public java.time.Period getEffectivePeriod() {
    return effectivePeriod;
  }
}
