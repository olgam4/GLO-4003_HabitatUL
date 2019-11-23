package ca.ulaval.glo4003.insuring.application.policy.renewal;

import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalId;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;

public interface PolicyRenewalProcessor {
  void scheduleRenewal(
      PolicyId policyId, PolicyRenewalId policyRenewalId, DateTime renewalEffectiveDate);

  void cancelRenewal(PolicyId policyId, PolicyRenewalId policyRenewalId);
}
