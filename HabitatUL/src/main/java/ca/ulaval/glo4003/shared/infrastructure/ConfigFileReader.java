package ca.ulaval.glo4003.shared.infrastructure;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigFileReader {
  public static Properties readProperties(String filePath) {
    Properties properties = new Properties();

    try (InputStream input = new FileInputStream(filePath)) {
      properties.load(input);
    } catch (IOException ex) {
      ex.printStackTrace();
    }

    return properties;
  }
}
