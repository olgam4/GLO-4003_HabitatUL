package ca.ulaval.glo4003.shared.presentation.temporal;

public class ConfigBasedLocalZoneIdProviderIT extends LocalZoneIdProviderIT {
  @Override
  public LocalZoneIdProvider createSubject() {
    return new ConfigBasedLocalZoneIdProvider();
  }
}
