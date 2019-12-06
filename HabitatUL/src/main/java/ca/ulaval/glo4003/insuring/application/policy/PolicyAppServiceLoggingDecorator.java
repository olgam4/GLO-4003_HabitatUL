package ca.ulaval.glo4003.insuring.application.policy;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.insuring.application.policy.dto.*;
import ca.ulaval.glo4003.insuring.application.policy.event.PolicyPurchasedEvent;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationId;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalId;
import ca.ulaval.glo4003.shared.application.logging.Logger;

public class PolicyAppServiceLoggingDecorator implements PolicyAppService {
  private Logger logger;
  private PolicyAppService policyAppService;

  public PolicyAppServiceLoggingDecorator(PolicyAppService policyAppService) {
    this(policyAppService, ServiceLocator.resolve(Logger.class));
  }

  public PolicyAppServiceLoggingDecorator(PolicyAppService policyAppService, Logger logger) {
    this.policyAppService = policyAppService;
    this.logger = logger;
  }

  @Override
  public void issuePolicy(PolicyPurchasedEvent policyPurchasedEvent) {
    logger.info(String.format("Issue policy with event <%s>", policyPurchasedEvent));
    this.policyAppService.issuePolicy(policyPurchasedEvent);
  }

  @Override
  public PolicyModificationDto insureBicycle(PolicyId policyId, InsureBicycleDto insureBicycleDto) {
    logger.info(String.format("Insure bicyle <%s> on policy <%s>", insureBicycleDto, policyId));
    PolicyModificationDto policyModificationDto =
        this.policyAppService.insureBicycle(policyId, insureBicycleDto);
    logger.info(String.format("Policy <%s> changed with <%s>", policyModificationDto, policyId));
    return policyModificationDto;
  }

  @Override
  public PolicyModificationDto modifyCoverage(
      PolicyId policyId, ModifyCoverageDto modifyCoverageDto) {
    logger.info(
        String.format("Modify coverage of policy <%s> with <%s>", policyId, modifyCoverageDto));
    PolicyModificationDto policyModificationDto =
        this.policyAppService.modifyCoverage(policyId, modifyCoverageDto);
    logger.info(String.format("Policy <%s> changed with <%s>", policyModificationDto, policyId));
    return policyModificationDto;
  }

  @Override
  public PolicyDto confirmModification(
      PolicyId policyId, PolicyModificationId policyModificationId) {
    logger.info(
        String.format(
            "Confirm modification of policy <%s> with modificationId <%s>",
            policyId, policyModificationId));
    PolicyDto policyDto = this.policyAppService.confirmModification(policyId, policyModificationId);
    logger.info(String.format("Policy <%s> changed to <%s>", policyId, policyDto));
    return policyDto;
  }

  @Override
  public PolicyRenewalDto triggerRenewal(PolicyId policyId, TriggerRenewalDto triggerRenewalDto) {
    logger.info(String.format("Trigger renewal <%s> on policy <%s>", triggerRenewalDto, policyId));
    PolicyRenewalDto policyRenewalDto =
        this.policyAppService.triggerRenewal(policyId, triggerRenewalDto);
    logger.info(String.format("Renewal <%s> triggered on policy <%s>", policyRenewalDto, policyId));
    return policyRenewalDto;
  }

  @Override
  public void acceptRenewal(PolicyId policyId, PolicyRenewalId policyRenewalId) {
    logger.info(
        String.format(
            "Accepting renewal of policy <%s> with renewalId <%s>", policyId, policyRenewalId));
    this.policyAppService.acceptRenewal(policyId, policyRenewalId);
  }

  @Override
  public void cancelRenewal(PolicyId policyId, PolicyRenewalId policyRenewalId) {
    logger.info(
        String.format(
            "Canceling renewal of policy <%s> with renewalId <%s>", policyId, policyRenewalId));
    this.policyAppService.cancelRenewal(policyId, policyRenewalId);
  }

  @Override
  public ClaimId openClaim(PolicyId policyId, OpenClaimDto openClaimDto) {
    logger.info(String.format("Open claim <%s> on policy <%s>", openClaimDto, policyId));
    ClaimId claimId = this.policyAppService.openClaim(policyId, openClaimDto);
    logger.info(String.format("Claim <%s> opened", claimId, policyId));
    return claimId;
  }
}
