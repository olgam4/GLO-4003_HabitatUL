package ca.ulaval.glo4003.insuring.helper.policy;

import ca.ulaval.glo4003.insuring.domain.policy.historic.PolicyHistoric;
import ca.ulaval.glo4003.insuring.domain.policy.historic.PolicyView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PolicyHistoricBuilder {
  private Set<PolicyView> historic = new HashSet<>();

  private PolicyHistoricBuilder() {}

  public static PolicyHistoricBuilder aPolicyHistoric() {
    return new PolicyHistoricBuilder();
  }

  public PolicyHistoricBuilder withPolicyViews(List<PolicyView> policyViews) {
    policyViews.forEach(this::withPolicyView);
    return this;
  }

  public PolicyHistoricBuilder withPolicyView(PolicyView policyView) {
    this.historic.add(policyView);
    return this;
  }

  public PolicyHistoric build() {
    return new PolicyHistoric(historic);
  }
}
