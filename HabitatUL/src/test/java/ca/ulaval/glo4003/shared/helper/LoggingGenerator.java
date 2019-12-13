package ca.ulaval.glo4003.shared.helper;

import ca.ulaval.glo4003.shared.application.logging.Logger;

public class LoggingGenerator {
  private LoggingGenerator() {}

  public static Logger createNullLogger() {
    return new Logger() {
      @Override
      public void info(String message) {}

      @Override
      public void severe(String message) {}
    };
  }
}
