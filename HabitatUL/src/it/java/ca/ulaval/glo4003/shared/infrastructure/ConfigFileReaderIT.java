package ca.ulaval.glo4003.shared.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class ConfigFileReaderIT {
  private ConfigFileReader subject;

  @Before
  public void setUp() {
    subject = new ConfigFileReader();
  }

  @Test
  public void readingConfigFile_withExistingFile_shouldReturnProperties() {
    Properties properties = subject.read("src/it/resources/test.config.properties");

    Properties expectedProperties = getTestProperties();
    assertEquals(expectedProperties, properties);
  }

  @Test
  public void readingConfigFile_withNotExistingFile_shouldReturnEmptyProperties() {
    Properties properties = subject.read("NOT_EXISTING_FILE");

    Properties expected = new Properties();
    assertEquals(expected, properties);
  }

  private Properties getTestProperties() {
    Properties properties = new Properties();
    properties.setProperty("key1", "value1");
    properties.setProperty("key2", "value2");
    return properties;
  }
}
