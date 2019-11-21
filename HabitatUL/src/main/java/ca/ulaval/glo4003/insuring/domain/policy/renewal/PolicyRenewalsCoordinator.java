package ca.ulaval.glo4003.insuring.domain.policy.renewal;

import java.util.HashMap;
import java.util.Map;

public class PolicyRenewalsCoordinator {
  private Map<PolicyRenewalId, PolicyRenewal> renewals;

  public PolicyRenewalsCoordinator() {
    this(new HashMap<>());
  }

  public PolicyRenewalsCoordinator(Map<PolicyRenewalId, PolicyRenewal> renewals) {
    this.renewals = renewals;
  }
}
