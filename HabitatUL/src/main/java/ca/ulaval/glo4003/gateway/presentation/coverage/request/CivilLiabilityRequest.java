package ca.ulaval.glo4003.gateway.presentation.coverage.request;

import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit;

import javax.validation.constraints.NotNull;

public class CivilLiabilityRequest {
  @NotNull private CivilLiabilityLimit limit;

  private CivilLiabilityRequest() {}

  public CivilLiabilityRequest(CivilLiabilityLimit limit) {
    this.limit = limit;
  }

  public CivilLiabilityLimit getLimit() {
    return limit;
  }
}
