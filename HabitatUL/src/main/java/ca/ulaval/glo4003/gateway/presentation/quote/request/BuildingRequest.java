package ca.ulaval.glo4003.gateway.presentation.quote.request;

import ca.ulaval.glo4003.underwriting.domain.quote.form.building.PreventionSystem;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.Set;

public class BuildingRequest {
  @Min(value = 1L)
  @NotNull
  private Integer numberOfUnits;

  private Set<PreventionSystem> preventionSystems;
  private String commercialUse;

  private BuildingRequest() {}

  public BuildingRequest(
      Integer numberOfUnits, Set<PreventionSystem> preventionSystems, String commercialUse) {
    this.numberOfUnits = numberOfUnits;
    this.preventionSystems = preventionSystems;
    this.commercialUse = commercialUse;
  }

  public Integer getNumberOfUnits() {
    return numberOfUnits;
  }

  public Set<PreventionSystem> getPreventionSystems() {
    return preventionSystems;
  }

  public Optional<String> getCommercialUse() {
    return Optional.ofNullable(commercialUse);
  }
}
