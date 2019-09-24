package ca.ulaval.glo4003.context;

import ca.ulaval.glo4003.shared.infrastructure.ConfigFileReader;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Properties;

public class ProdContext implements Context {
  @Override
  public void execute() {
    Properties properties = ConfigFileReader.readProperties("config.properties");
    int quoteValidityPeriodTemporalAmount =
        Integer.parseInt(properties.getProperty("quote.validityPeriod.temporalAmount"));
    ChronoUnit quoteValidityPeriodTemporalUnit =
        ChronoUnit.valueOf(properties.getProperty("quote.validityPeriod.temporalUnit"));
    Duration quoteValidityPeriod =
        Duration.of(quoteValidityPeriodTemporalAmount, quoteValidityPeriodTemporalUnit);
  }
}
