package ca.ulaval.glo4003.gateway.presentation.policy.response;

import java.util.List;

public class PoliciesResponse {
  private List<String> policies;

  public PoliciesResponse(List<String> policies) {
    this.policies = policies;
  }

  public List<String> getPolicies() {
    return policies;
  }
}
