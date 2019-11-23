package ca.ulaval.glo4003.insuring.application.policy;

import ca.ulaval.glo4003.insuring.application.policy.dto.InsureBicycleDto;
import ca.ulaval.glo4003.insuring.application.policy.dto.ModifyCoverageDto;
import ca.ulaval.glo4003.insuring.application.policy.dto.OpenClaimDto;
import ca.ulaval.glo4003.insuring.application.policy.dto.PolicyDto;
import ca.ulaval.glo4003.insuring.application.policy.dto.PolicyModificationDto;
import ca.ulaval.glo4003.insuring.application.policy.dto.PolicyRenewalDto;
import ca.ulaval.glo4003.insuring.application.policy.dto.TriggerRenewalDto;
import ca.ulaval.glo4003.insuring.application.policy.event.PolicyPurchasedEvent;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationId;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalId;
import ca.ulaval.glo4003.shared.application.concurrency.ConcurrentDecorator;

public class PolicyAppServiceConcurrentDecorator
    extends ConcurrentDecorator<PolicyId> implements PolicyAppService {
  private PolicyAppService policyAppService;

  public PolicyAppServiceConcurrentDecorator(PolicyAppService policyAppService) {
    this.policyAppService = policyAppService;
  }

  @Override
  public void issuePolicy(PolicyPurchasedEvent policyPurchasedEvent) {
    policyAppService.issuePolicy(policyPurchasedEvent);
  }

  @Override
  public PolicyModificationDto insureBicycle(PolicyId policyId, InsureBicycleDto insureBicycleDto) {
    return lockAndCall(policyId, () -> policyAppService.insureBicycle(policyId, insureBicycleDto));
  }

  @Override
  public PolicyModificationDto modifyCoverage(
      PolicyId policyId, ModifyCoverageDto modifyCoverageDto) {
    return lockAndCall(policyId,
        () -> policyAppService.modifyCoverage(policyId, modifyCoverageDto));
  }

  @Override
  public PolicyDto confirmModification(
      PolicyId policyId, PolicyModificationId policyModificationId) {
    return lockAndCall(policyId,
        () -> policyAppService.confirmModification(policyId, policyModificationId));
  }

  @Override
  public PolicyRenewalDto triggerRenewal(PolicyId policyId, TriggerRenewalDto triggerRenewalDto) {
    return lockAndCall(policyId,
        () -> policyAppService.triggerRenewal(policyId, triggerRenewalDto));
  }

  @Override
  public void acceptRenewal(PolicyId policyId, PolicyRenewalId policyRenewalId) {
    lockAndCall(policyId, () -> policyAppService.acceptRenewal(policyId, policyRenewalId));
  }

  @Override
  public void cancelRenewal(PolicyId policyId, PolicyRenewalId policyRenewalId) {
    lockAndCall(policyId, () -> policyAppService.cancelRenewal(policyId, policyRenewalId));
  }

  @Override
  public void confirmRenewal(PolicyId policyId, PolicyRenewalId policyRenewalId) {
    lockAndCall(policyId, () -> policyAppService.confirmRenewal(policyId, policyRenewalId));
  }

  @Override
  public ClaimId openClaim(PolicyId policyId, OpenClaimDto openClaimDto) {
    return policyAppService.openClaim(policyId, openClaimDto);
  }
}
