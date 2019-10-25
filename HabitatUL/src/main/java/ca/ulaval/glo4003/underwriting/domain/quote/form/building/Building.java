package ca.ulaval.glo4003.underwriting.domain.quote.form.building;

import java.util.Set;

public class Building {
  public static final String NO_COMMERCIAL_USE = "NO_COMMERCIAL_USE";

  private int numberOfUnits;
  private Set<PreventionSystem> preventionSystems;
  private String commercialUse;

  public Building(
      int numberOfUnits, Set<PreventionSystem> preventionSystems, String commercialUse) {
    this.numberOfUnits = numberOfUnits;
    this.preventionSystems = preventionSystems;
    this.commercialUse = commercialUse;
  }

  public int getNumberOfUnits() {
    return numberOfUnits;
  }

  public Set<PreventionSystem> getPreventionSystems() {
    return preventionSystems;
  }

  public String getCommercialUse() {
    return commercialUse;
  }
}
