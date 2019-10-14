package ca.ulaval.glo4003.gateway.presentation.quote.request;

import ca.ulaval.glo4003.underwriting.domain.quote.form.building.PreventionSystem;

import java.util.Optional;
import java.util.Set;

public class BuildingView {
  private int numberOfUnits;
  private Set<PreventionSystem> preventionSystems;
  private Optional<String> commercialUse;

  private BuildingView() {}

  public BuildingView(
      int numberOfUnits, Set<PreventionSystem> preventionSystems, Optional<String> commercialUse) {
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

  public Optional<String> getCommercialUse() {
    return commercialUse;
  }
}
