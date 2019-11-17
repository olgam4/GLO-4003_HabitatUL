package ca.ulaval.glo4003.helper.policy;

import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationId;

public class PolicyModificationGenerator {
  private PolicyModificationGenerator() {}

  public static PolicyModificationId createPolicyModificationId() {
    return new PolicyModificationId();
  }
}
