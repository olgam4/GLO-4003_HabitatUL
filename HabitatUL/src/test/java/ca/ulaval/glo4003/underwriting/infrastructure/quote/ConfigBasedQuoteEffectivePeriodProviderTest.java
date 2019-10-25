package ca.ulaval.glo4003.underwriting.infrastructure.quote;

import ca.ulaval.glo4003.shared.infrastructure.ConfigFileReader;
import com.github.javafaker.Faker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Period;
import java.util.Properties;

import static ca.ulaval.glo4003.underwriting.infrastructure.quote.ConfigBasedQuoteEffectivePeriodProvider.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConfigBasedQuoteEffectivePeriodProviderTest {
  private static final Integer OVERRIDDEN = Faker.instance().number().randomDigit();
  private static final String OVERRIDDEN_VALUE = OVERRIDDEN.toString();

  @Mock private ConfigFileReader configFileReader;

  private ConfigBasedQuoteEffectivePeriodProvider subject;

  @Test
  public void
      gettingQuoteEffectivePeriod_withOverriddenConfiguration_shouldProvideOverriddenValue() {
    Properties properties = new Properties();
    properties.setProperty(KEY, OVERRIDDEN_VALUE);
    when(configFileReader.read(FILE_PATH)).thenReturn(properties);
    subject = new ConfigBasedQuoteEffectivePeriodProvider(configFileReader);

    Period quoteEffectivePeriod = subject.getQuoteEffectivePeriod();

    Period expected = Period.ofMonths(OVERRIDDEN);
    assertEquals(expected, quoteEffectivePeriod);
  }

  @Test
  public void gettingQuoteEffectivePeriod_withDefaultConfiguration_shouldProvideDefaultValue() {
    when(configFileReader.read(FILE_PATH)).thenReturn(new Properties());
    subject = new ConfigBasedQuoteEffectivePeriodProvider(configFileReader);

    Period quoteEffectivePeriod = subject.getQuoteEffectivePeriod();

    Period expected = Period.ofMonths(Integer.parseInt(DEFAULT_VALUE));
    assertEquals(expected, quoteEffectivePeriod);
  }
}
