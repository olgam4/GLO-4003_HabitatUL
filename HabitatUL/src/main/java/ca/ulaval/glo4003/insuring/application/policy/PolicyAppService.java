package ca.ulaval.glo4003.insuring.application.policy;

import ca.ulaval.glo4003.insuring.application.policy.dto.*;
import ca.ulaval.glo4003.insuring.application.policy.event.PolicyPurchasedEvent;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import ca.ulaval.glo4003.insuring.domain.policy.lossratio.LossRatio;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationId;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalId;

public interface PolicyAppService {
  void issuePolicy(PolicyPurchasedEvent policyPurchasedEvent);

  PolicyModificationDto insureBicycle(PolicyId policyId, InsureBicycleDto insureBicycleDto);

  PolicyModificationDto modifyCoverage(PolicyId policyId, ModifyCoverageDto modifyCoverageDto);

  PolicyDto confirmModification(PolicyId policyId, PolicyModificationId policyModificationId);

  PolicyRenewalDto triggerRenewal(PolicyId policyId, TriggerRenewalDto triggerRenewalDto);

  void acceptRenewal(PolicyId policyId, PolicyRenewalId policyRenewalId);

  void cancelRenewal(PolicyId policyId, PolicyRenewalId policyRenewalId);

  ClaimId openClaim(PolicyId policyId, OpenClaimDto openClaimDto);

  void configureMaximumLossRatio(LossRatio maximumLossRatio);
}
