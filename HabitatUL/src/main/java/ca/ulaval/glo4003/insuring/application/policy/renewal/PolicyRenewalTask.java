package ca.ulaval.glo4003.insuring.application.policy.renewal;

import ca.ulaval.glo4003.insuring.domain.policy.Policy;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyRepository;
import ca.ulaval.glo4003.insuring.domain.policy.exception.PolicyNotFoundException;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalId;
import ca.ulaval.glo4003.shared.application.logging.Logger;

public class PolicyRenewalTask implements Runnable {
  private PolicyRepository policyRepository;
  private PolicyId policyId;
  private PolicyRenewalId policyRenewalId;
  private Logger logger;

  public PolicyRenewalTask(
      PolicyRepository policyRepository,
      PolicyId policyId,
      PolicyRenewalId policyRenewalId,
      Logger logger) {
    this.policyRepository = policyRepository;
    this.policyId = policyId;
    this.policyRenewalId = policyRenewalId;
    this.logger = logger;
  }

  @Override
  public void run() {
    try {
      Policy policy = policyRepository.getById(policyId);
      policy.confirmRenewal(policyRenewalId);
      // TODO: process to payment here
      policyRepository.update(policy);
    } catch (PolicyNotFoundException e) {
      logger.severe(String.format("Could not renew not existing policy with id <%s>", policyId));
    }
  }
}
