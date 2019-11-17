package ca.ulaval.glo4003.helper.policy;

import ca.ulaval.glo4003.insuring.domain.policy.historic.PolicyHistoric;
import ca.ulaval.glo4003.insuring.domain.policy.historic.PolicyView;

import java.util.ArrayList;
import java.util.List;

public class PolicyHistoricBuilder {
  private List<PolicyView> historic = new ArrayList<>();

  private PolicyHistoricBuilder() {}

  public static PolicyHistoricBuilder aPolicyHistoric() {
    return new PolicyHistoricBuilder();
  }

  public PolicyHistoricBuilder withPolicyView(PolicyView policyView) {
    this.historic.add(policyView);
    return this;
  }

  public PolicyHistoric build() {
    return new PolicyHistoric(historic);
  }
}
