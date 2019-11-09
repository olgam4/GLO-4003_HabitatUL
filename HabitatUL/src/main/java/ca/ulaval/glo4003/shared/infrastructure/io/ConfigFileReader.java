package ca.ulaval.glo4003.shared.infrastructure.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigFileReader {
  public Properties read(String filePath) {
    try {
      InputStream input = new FileInputStream(filePath);
      Properties properties = new Properties();
      properties.load(input);
      return properties;
    } catch (IOException e) {
      return new Properties();
    }
  }
}
