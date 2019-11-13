package ca.ulaval.glo4003.gateway.presentation.policy.request;

import ca.ulaval.glo4003.gateway.presentation.coverage.request.BicycleRequest;

import javax.validation.Valid;
import java.util.Optional;

public class ModificationRequest {
  @Valid private BicycleRequest bicycle;

  private ModificationRequest() {}

  public ModificationRequest(BicycleRequest bicycle) {
    this.bicycle = bicycle;
  }

  public Optional<BicycleRequest> getBicycle() {
    return Optional.ofNullable(bicycle);
  }
}
