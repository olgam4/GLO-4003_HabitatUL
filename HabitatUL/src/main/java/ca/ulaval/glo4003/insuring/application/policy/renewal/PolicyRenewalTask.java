package ca.ulaval.glo4003.insuring.application.policy.renewal;

import ca.ulaval.glo4003.insuring.domain.policy.Policy;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyRepository;
import ca.ulaval.glo4003.insuring.domain.policy.error.PolicyNotFoundError;
import ca.ulaval.glo4003.insuring.domain.policy.exception.PolicyNotFoundException;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalId;

public class PolicyRenewalTask implements Runnable {
  private PolicyRepository policyRepository;
  private PolicyId policyId;
  private PolicyRenewalId policyRenewalId;

  public PolicyRenewalTask(
      PolicyRepository policyRepository, PolicyId policyId, PolicyRenewalId policyRenewalId) {
    this.policyRepository = policyRepository;
    this.policyId = policyId;
    this.policyRenewalId = policyRenewalId;
  }

  @Override
  public void run() {
    try {
      Policy policy = policyRepository.getById(policyId);
      policy.confirmRenewal(policyRenewalId);
      policyRepository.update(policy);
    } catch (PolicyNotFoundException e) {
      throw new PolicyNotFoundError(policyId);
    }
  }
}
