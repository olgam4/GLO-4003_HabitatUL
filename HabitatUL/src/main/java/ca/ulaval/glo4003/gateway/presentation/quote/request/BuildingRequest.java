package ca.ulaval.glo4003.gateway.presentation.quote.request;

import ca.ulaval.glo4003.coverage.domain.form.building.PreventionSystems;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public class BuildingRequest {
  @Min(value = 1L)
  @NotNull
  private Integer numberOfUnits;

  private PreventionSystems preventionSystems;
  private String commercialUse;

  private BuildingRequest() {}

  public BuildingRequest(
      Integer numberOfUnits, PreventionSystems preventionSystems, String commercialUse) {
    this.numberOfUnits = numberOfUnits;
    this.preventionSystems = preventionSystems;
    this.commercialUse = commercialUse;
  }

  public Integer getNumberOfUnits() {
    return numberOfUnits;
  }

  public Optional<PreventionSystems> getPreventionSystems() {
    return Optional.ofNullable(preventionSystems);
  }

  public Optional<String> getCommercialUse() {
    return Optional.ofNullable(commercialUse);
  }
}
