package ca.ulaval.glo4003.underwriting.infrastructure.quote;

import ca.ulaval.glo4003.shared.infrastructure.ConfigFileReader;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteValidityPeriodProvider;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Properties;

public class ConfigBasedQuoteValidityPeriodProvider implements QuoteValidityPeriodProvider {
  private Duration quoteValidityPeriod;

  public ConfigBasedQuoteValidityPeriodProvider() {
    Properties properties = ConfigFileReader.readProperties("config.properties");
    int quoteValidityPeriodTemporalAmount =
        Integer.parseInt(properties.getProperty("quote.validityPeriod.temporalAmount"));
    ChronoUnit quoteValidityPeriodTemporalUnit =
        ChronoUnit.valueOf(properties.getProperty("quote.validityPeriod.temporalUnit"));
    Duration quoteValidityPeriod =
        Duration.of(quoteValidityPeriodTemporalAmount, quoteValidityPeriodTemporalUnit);
    this.quoteValidityPeriod = quoteValidityPeriod;
  }

  @Override
  public Duration getQuoteValidityPeriod() {
    return quoteValidityPeriod;
  }
}
