package ca.ulaval.glo4003.insuring.helper.policy;

import ca.ulaval.glo4003.insuring.domain.policy.historic.PolicyHistoric;

import static ca.ulaval.glo4003.insuring.helper.policy.PolicyViewGenerator.createPolicyViews;

public class PolicyHistoricGenerator {
  private PolicyHistoricGenerator() {}

  public static PolicyHistoric createPolicyHistoric() {
    return new PolicyHistoric(createPolicyViews());
  }
}
