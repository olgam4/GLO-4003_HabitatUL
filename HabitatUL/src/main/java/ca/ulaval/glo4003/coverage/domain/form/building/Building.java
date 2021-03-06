package ca.ulaval.glo4003.coverage.domain.form.building;

import ca.ulaval.glo4003.shared.domain.ValueObject;

public class Building extends ValueObject {
  public static final String NO_COMMERCIAL_USE = "NO_COMMERCIAL_USE";

  private final int numberOfUnits;
  private final PreventionSystems preventionSystems;
  private final String commercialUse;

  public Building(int numberOfUnits, PreventionSystems preventionSystems, String commercialUse) {
    this.numberOfUnits = numberOfUnits;
    this.preventionSystems = preventionSystems;
    this.commercialUse = commercialUse;
  }

  public int getNumberOfUnits() {
    return numberOfUnits;
  }

  public PreventionSystems getPreventionSystems() {
    return preventionSystems;
  }

  public String getCommercialUse() {
    return commercialUse;
  }
}
