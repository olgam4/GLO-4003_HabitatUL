package ca.ulaval.glo4003.insuring.application.policy.renewal;

import ca.ulaval.glo4003.insuring.application.policy.PolicyAppService;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalId;

public class PolicyRenewalTask implements Runnable {
  private PolicyAppService policyAppService;
  private PolicyId policyId;
  private PolicyRenewalId policyRenewalId;

  public PolicyRenewalTask(
      PolicyAppService policyAppService, PolicyId policyId, PolicyRenewalId policyRenewalId) {
    this.policyAppService = policyAppService;
    this.policyId = policyId;
    this.policyRenewalId = policyRenewalId;
  }

  @Override
  public void run() {
    policyAppService.confirmRenewal(policyId, policyRenewalId);
  }
}
