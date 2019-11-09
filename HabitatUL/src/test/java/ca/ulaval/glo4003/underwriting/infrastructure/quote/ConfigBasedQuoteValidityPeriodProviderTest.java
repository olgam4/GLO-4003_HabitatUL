package ca.ulaval.glo4003.underwriting.infrastructure.quote;

import ca.ulaval.glo4003.shared.infrastructure.io.ConfigFileReader;
import com.github.javafaker.Faker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Properties;

import static ca.ulaval.glo4003.underwriting.infrastructure.quote.ConfigBasedQuoteValidityPeriodProvider.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConfigBasedQuoteValidityPeriodProviderTest {
  private static final Integer PERIOD_TEMPORAL_AMOUNT_OVERRIDDEN =
      Faker.instance().number().randomDigit();
  private static final String PERIOD_TEMPORAL_AMOUNT_OVERRIDDEN_VALUE =
      PERIOD_TEMPORAL_AMOUNT_OVERRIDDEN.toString();
  private static final ChronoUnit PERIOD_TEMPORAL_UNIT_OVERRIDDEN = ChronoUnit.MICROS;
  private static final String PERIOD_TEMPORAL_UNIT_OVERRIDDEN_VALUE =
      PERIOD_TEMPORAL_UNIT_OVERRIDDEN.name();

  @Mock private ConfigFileReader configFileReader;

  private ConfigBasedQuoteValidityPeriodProvider subject;

  @Test
  public void
      gettingQuoteValidityPeriod_withOverriddenConfiguration_shouldProvideOverriddenValue() {
    Properties properties = new Properties();
    properties.setProperty(PERIOD_TEMPORAL_AMOUNT_KEY, PERIOD_TEMPORAL_AMOUNT_OVERRIDDEN_VALUE);
    properties.setProperty(PERIOD_TEMPORAL_UNIT_KEY, PERIOD_TEMPORAL_UNIT_OVERRIDDEN_VALUE);
    when(configFileReader.read(FILE_PATH)).thenReturn(properties);
    subject = new ConfigBasedQuoteValidityPeriodProvider(configFileReader);

    Duration quoteValidityPeriod = subject.getQuoteValidityPeriod();

    Duration expected =
        Duration.of(PERIOD_TEMPORAL_AMOUNT_OVERRIDDEN, PERIOD_TEMPORAL_UNIT_OVERRIDDEN);
    assertEquals(expected, quoteValidityPeriod);
  }

  @Test
  public void gettingQuoteValidityPeriod_withDefaultConfiguration_shouldProvideDefaultValue() {
    when(configFileReader.read(FILE_PATH)).thenReturn(new Properties());
    subject = new ConfigBasedQuoteValidityPeriodProvider(configFileReader);

    Duration quoteValidityPeriod = subject.getQuoteValidityPeriod();

    int expectedPeriodTemporalAmount = Integer.parseInt(PERIOD_TEMPORAL_AMOUNT_DEFAULT_VALUE);
    ChronoUnit expectedPeriodTemporalUnit = ChronoUnit.valueOf(PERIOD_TEMPORAL_UNIT_DEFAULT_VALUE);
    Duration expected = Duration.of(expectedPeriodTemporalAmount, expectedPeriodTemporalUnit);
    assertEquals(expected, quoteValidityPeriod);
  }
}
