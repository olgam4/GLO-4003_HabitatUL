package ca.ulaval.glo4003.context;

import ca.ulaval.glo4003.gateway.presentation.databind.LocalZoneIdProvider;

import java.time.ZoneId;

public class TestContext implements Context {
  public TestContext() {
    ServiceLocator.reset();
  }

  @Override
  public void execute() {
    ServiceLocator.register(LocalZoneIdProvider.class, () -> ZoneId.of("UTC"));
  }
}
