package ca.ulaval.glo4003.underwriting.infrastructure.quote;

import ca.ulaval.glo4003.shared.infrastructure.ConfigFileReader;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteValidityPeriodProvider;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Properties;

public class ConfigBasedQuoteValidityPeriodProvider implements QuoteValidityPeriodProvider {
  static final String FILE_PATH = "config.properties";
  static final String PERIOD_TEMPORAL_AMOUNT_KEY = "quote.validityPeriod.temporalAmount";
  static final String PERIOD_TEMPORAL_AMOUNT_DEFAULT_VALUE = "5";
  static final String PERIOD_TEMPORAL_UNIT_KEY = "quote.validityPeriod.temporalUnit";
  static final String PERIOD_TEMPORAL_UNIT_DEFAULT_VALUE = "DAYS";

  private Duration quoteValidityPeriod;

  public ConfigBasedQuoteValidityPeriodProvider() {
    this(new ConfigFileReader());
  }

  public ConfigBasedQuoteValidityPeriodProvider(ConfigFileReader configFileReader) {
    this.quoteValidityPeriod = parseQuoteValidityPeriod(configFileReader);
  }

  private Duration parseQuoteValidityPeriod(ConfigFileReader configFileReader) {
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
  public Duration getQuoteValidityPeriod() {
    return quoteValidityPeriod;
  }
}
