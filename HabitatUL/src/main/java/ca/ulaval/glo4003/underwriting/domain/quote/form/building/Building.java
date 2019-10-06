package ca.ulaval.glo4003.underwriting.domain.quote.form.building;

import java.util.Optional;
import java.util.Set;

public class Building {
  private int numberOfUnits;
  private Set<PreventionSystem> preventionSystems;
  private Optional<String> commercialUse;

  public Building(
      int numberOfUnits, Set<PreventionSystem> preventionSystems, Optional<String> commercialUse) {
    this.numberOfUnits = numberOfUnits;
    this.preventionSystems = preventionSystems;
    this.commercialUse = commercialUse;
  }

  public Optional<String> getCommercialUse() {
    return commercialUse;
  }

  public Set<PreventionSystem> getPreventionSystems() {
    return preventionSystems;
  }

  public int getNumberOfUnits() {
    return numberOfUnits;
  }
}
