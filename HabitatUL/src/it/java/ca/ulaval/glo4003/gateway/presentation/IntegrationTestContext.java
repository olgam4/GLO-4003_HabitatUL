package ca.ulaval.glo4003.gateway.presentation;

import ca.ulaval.glo4003.context.Context;
import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.gateway.presentation.common.databind.LocalZoneIdProvider;

import java.time.ZoneId;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IntegrationTestContext implements Context {
  public IntegrationTestContext() {
    ServiceLocator.reset();
  }

  private static void disableLogging() {
    Logger.getLogger("org.glassfish.jersey.internal.inject.Providers").setLevel(Level.SEVERE);
    System.setProperty("org.eclipse.jetty.util.log.class", "org.eclipse.jetty.util.log.StdErrLog");
    System.setProperty("org.eclipse.jetty.LEVEL", "OFF");
  }

  @Override
  public void execute() {
    disableLogging();
    registerServices();
  }

  private void registerServices() {
    ServiceLocator.register(LocalZoneIdProvider.class, () -> ZoneId.of("UTC"));
  }
}
