package ca.ulaval.glo4003.management.domain.user.exception;

public class PolicyKeyNotFoundException extends UserException {
  private String policyKey;

  public PolicyKeyNotFoundException(String policyKey) {
    this.policyKey = policyKey;
  }

  public String getPolicyKey() {
    return policyKey;
  }
}
