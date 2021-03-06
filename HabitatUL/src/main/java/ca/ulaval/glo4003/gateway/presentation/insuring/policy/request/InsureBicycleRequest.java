package ca.ulaval.glo4003.gateway.presentation.insuring.policy.request;

import ca.ulaval.glo4003.gateway.presentation.coverage.request.BicycleRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class InsureBicycleRequest {
  @NotNull @Valid private BicycleRequest bicycle;

  private InsureBicycleRequest() {}

  public InsureBicycleRequest(BicycleRequest bicycle) {
    this.bicycle = bicycle;
  }

  public BicycleRequest getBicycle() {
    return bicycle;
  }
}
