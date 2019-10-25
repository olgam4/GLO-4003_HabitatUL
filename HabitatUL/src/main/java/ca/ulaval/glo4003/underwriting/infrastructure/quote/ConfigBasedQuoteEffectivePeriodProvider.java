package ca.ulaval.glo4003.underwriting.infrastructure.quote;

import ca.ulaval.glo4003.shared.infrastructure.ConfigFileReader;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteEffectivePeriodProvider;

import java.time.Period;
import java.util.Optional;
import java.util.Properties;

public class ConfigBasedQuoteEffectivePeriodProvider implements QuoteEffectivePeriodProvider {
  static final String FILE_PATH = "config.properties";
  static final String KEY = "quote.effectivePeriod.inMonths";
  static final String DEFAULT_VALUE = "12";

  private Period effectivePeriod;

  public ConfigBasedQuoteEffectivePeriodProvider() {
    this(new ConfigFileReader());
  }

  public ConfigBasedQuoteEffectivePeriodProvider(ConfigFileReader configFileReader) {
    Properties properties = configFileReader.read(FILE_PATH);
    String temporalAmountValue =
        Optional.ofNullable(properties.getProperty(KEY)).orElse(DEFAULT_VALUE);
    int effectivePeriodInMonths = Integer.parseInt(temporalAmountValue);
    this.effectivePeriod = Period.ofMonths(effectivePeriodInMonths);
  }

  @Override
  public Period getQuoteEffectivePeriod() {
    return effectivePeriod;
  }
}
