package ca.ulaval.glo4003.insuring.infrastructure.policy.renewal;

import ca.ulaval.glo4003.shared.infrastructure.io.ConfigFileReader;
import com.github.javafaker.Faker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Period;
import java.util.Properties;

import static ca.ulaval.glo4003.insuring.infrastructure.policy.renewal.ConfigBasedPolicyCoveragePeriodProvider.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConfigBasedPolicyCoveragePeriodProviderTest {
  private static final Integer OVERRIDDEN = Faker.instance().number().randomDigit();
  private static final String OVERRIDDEN_VALUE = OVERRIDDEN.toString();

  @Mock private ConfigFileReader configFileReader;

  private ConfigBasedPolicyCoveragePeriodProvider subject;

  @Test
  public void
      gettingPolicyCoveragePeriod_withOverriddenConfiguration_shouldProvideOverriddenValue() {
    Properties properties = new Properties();
    properties.setProperty(KEY, OVERRIDDEN_VALUE);
    when(configFileReader.read(FILE_PATH)).thenReturn(properties);
    subject = new ConfigBasedPolicyCoveragePeriodProvider(configFileReader);

    Period policyCoveragePeriod = subject.getPolicyCoveragePeriod();

    Period expected = Period.ofMonths(OVERRIDDEN);
    assertEquals(expected, policyCoveragePeriod);
  }

  @Test
  public void gettingPolicyCoveragePeriod_withDefaultConfiguration_shouldProvideDefaultValue() {
    when(configFileReader.read(FILE_PATH)).thenReturn(new Properties());
    subject = new ConfigBasedPolicyCoveragePeriodProvider(configFileReader);

    Period policyCoveragePeriod = subject.getPolicyCoveragePeriod();

    Period expected = Period.ofMonths(Integer.parseInt(DEFAULT_VALUE));
    assertEquals(expected, policyCoveragePeriod);
  }
}
