package ca.ulaval.glo4003.insuring.domain.policy.modification;

public class PolicyModification {
  private PolicyModificationId policyModificationId;

  public PolicyModification(PolicyModificationId policyModificationId) {
    this.policyModificationId = policyModificationId;
  }

  public PolicyModificationId getPolicyModificationId() {
    return policyModificationId;
  }
}
