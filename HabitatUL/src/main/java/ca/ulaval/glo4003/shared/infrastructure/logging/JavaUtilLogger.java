package ca.ulaval.glo4003.shared.infrastructure.logging;

import ca.ulaval.glo4003.shared.application.logging.Logger;

import java.io.IOException;
import java.util.logging.FileHandler;

public class JavaUtilLogger implements Logger {
  private java.util.logging.Logger logger;

  public JavaUtilLogger(String descriptor, String outputFileName) {
    this.logger = java.util.logging.Logger.getLogger(descriptor);
    addFileHandler(logger, outputFileName);
  }

  private void addFileHandler(java.util.logging.Logger logger, String outputFileName) {
    try {
      logger.addHandler(new FileHandler(outputFileName));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void info(String message) {
    logger.info(message);
  }

  @Override
  public void severe(String message) {
    logger.severe(message);
  }
}
