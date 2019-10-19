package ca.ulaval.glo4003.administration.infrastructure.user;

import ca.ulaval.glo4003.shared.infrastructure.ConfigFileReader;
import com.github.javafaker.Faker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Properties;

import static ca.ulaval.glo4003.administration.infrastructure.user.ConfigBasedTokenValidityPeriodProvider.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConfigBasedTokenValidityPeriodProviderTest {
  private static final Integer PERIOD_TEMPORAL_AMOUNT_OVERRIDDEN =
      Faker.instance().number().randomDigit();
  private static final String PERIOD_TEMPORAL_AMOUNT_OVERRIDDEN_VALUE =
      PERIOD_TEMPORAL_AMOUNT_OVERRIDDEN.toString();
  private static final ChronoUnit PERIOD_TEMPORAL_UNIT_OVERRIDDEN = ChronoUnit.DAYS;
  private static final String PERIOD_TEMPORAL_UNIT_OVERRIDDEN_VALUE =
      PERIOD_TEMPORAL_UNIT_OVERRIDDEN.name();

  @Mock private ConfigFileReader configFileReader;

  private ConfigBasedTokenValidityPeriodProvider subject;

  @Test
  public void
      gettingTokenValidityPeriod_withOverriddenConfiguration_shouldProvideOverriddenValue() {
    Properties properties = new Properties();
    properties.setProperty(PERIOD_TEMPORAL_AMOUNT_KEY, PERIOD_TEMPORAL_AMOUNT_OVERRIDDEN_VALUE);
    properties.setProperty(PERIOD_TEMPORAL_UNIT_KEY, PERIOD_TEMPORAL_UNIT_OVERRIDDEN_VALUE);
    when(configFileReader.read(FILE_PATH)).thenReturn(properties);
    subject = new ConfigBasedTokenValidityPeriodProvider(configFileReader);

    Duration tokenValidityPeriod = subject.getTokenValidityPeriod();

    Duration expected =
        Duration.of(PERIOD_TEMPORAL_AMOUNT_OVERRIDDEN, PERIOD_TEMPORAL_UNIT_OVERRIDDEN);
    assertEquals(expected, tokenValidityPeriod);
  }

  @Test
  public void gettingTokenValidityPeriod_withDefaultConfiguration_shouldProvideDefaultValue() {
    when(configFileReader.read(FILE_PATH)).thenReturn(new Properties());
    subject = new ConfigBasedTokenValidityPeriodProvider(configFileReader);

    Duration tokenValidityPeriod = subject.getTokenValidityPeriod();

    int expectedPeriodTemporalAmount = Integer.parseInt(PERIOD_TEMPORAL_AMOUNT_DEFAULT_VALUE);
    ChronoUnit expectedPeriodTemporalUnit = ChronoUnit.valueOf(PERIOD_TEMPORAL_UNIT_DEFAULT_VALUE);
    Duration expected = Duration.of(expectedPeriodTemporalAmount, expectedPeriodTemporalUnit);
    assertEquals(expected, tokenValidityPeriod);
  }
}
