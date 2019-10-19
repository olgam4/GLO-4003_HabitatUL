package ca.ulaval.glo4003.gateway.presentation.common.databind;

public class ConfigBasedLocalZoneIdProviderIT extends LocalZoneIdProviderIT {
  @Override
  public LocalZoneIdProvider createSubject() {
    return new ConfigBasedLocalZoneIdProvider();
  }
}
