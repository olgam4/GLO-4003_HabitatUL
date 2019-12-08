package ca.ulaval.glo4003.insuring.application.policy;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.insuring.application.policy.dto.*;
import ca.ulaval.glo4003.insuring.application.policy.event.PolicyPurchasedEvent;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import ca.ulaval.glo4003.insuring.domain.policy.lossratio.LossRatio;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationId;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalId;
import ca.ulaval.glo4003.shared.application.logging.Logger;

import java.util.List;
import java.util.Map;

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
    logger.info(String.format("Issuing policy with event <%s>", policyPurchasedEvent));
    policyAppService.issuePolicy(policyPurchasedEvent);
  }

  @Override
  public PolicyModificationDto insureBicycle(PolicyId policyId, InsureBicycleDto insureBicycleDto) {
    logger.info(String.format("Insuring bicycle <%s> on policy <%s>", insureBicycleDto, policyId));
    PolicyModificationDto policyModificationDto =
        policyAppService.insureBicycle(policyId, insureBicycleDto);
    logger.info(String.format("Policy <%s> modified with <%s>", policyId, policyModificationDto));
    return policyModificationDto;
  }

  @Override
  public PolicyModificationDto modifyCoverage(
      PolicyId policyId, ModifyCoverageDto modifyCoverageDto) {
    logger.info(
        String.format("Modifying coverage of policy <%s> with <%s>", policyId, modifyCoverageDto));
    PolicyModificationDto policyModificationDto =
        policyAppService.modifyCoverage(policyId, modifyCoverageDto);
    logger.info(String.format("Policy <%s> modified for <%s>", policyId, policyModificationDto));
    return policyModificationDto;
  }

  @Override
  public PolicyDto confirmModification(
      PolicyId policyId, PolicyModificationId policyModificationId) {
    logger.info(
        String.format(
            "Confirming modification of policy <%s> with modificationId <%s>",
            policyId, policyModificationId));
    PolicyDto policyDto = policyAppService.confirmModification(policyId, policyModificationId);
    logger.info(String.format("Policy <%s> modified to <%s>", policyId, policyDto));
    return policyDto;
  }

  @Override
  public PolicyRenewalDto triggerRenewal(PolicyId policyId, TriggerRenewalDto triggerRenewalDto) {
    logger.info(
        String.format("Triggering renewal <%s> on policy <%s>", triggerRenewalDto, policyId));
    PolicyRenewalDto policyRenewalDto =
        policyAppService.triggerRenewal(policyId, triggerRenewalDto);
    logger.info(String.format("Renewal <%s> triggered on policy <%s>", policyRenewalDto, policyId));
    return policyRenewalDto;
  }

  @Override
  public void acceptRenewal(PolicyId policyId, PolicyRenewalId policyRenewalId) {
    logger.info(
        String.format(
            "Accepting renewal of policy <%s> with renewalId <%s>", policyId, policyRenewalId));
    policyAppService.acceptRenewal(policyId, policyRenewalId);
  }

  @Override
  public void cancelRenewal(PolicyId policyId, PolicyRenewalId policyRenewalId) {
    logger.info(
        String.format(
            "Canceling renewal of policy <%s> with renewalId <%s>", policyId, policyRenewalId));
    policyAppService.cancelRenewal(policyId, policyRenewalId);
  }

  @Override
  public ClaimId openClaim(PolicyId policyId, OpenClaimDto openClaimDto) {
    logger.info(String.format("Opening claim <%s> for policy <%s>", openClaimDto, policyId));
    ClaimId claimId = policyAppService.openClaim(policyId, openClaimDto);
    logger.info(String.format("Claim <%s> opened for policy <%s>", claimId, policyId));
    return claimId;
  }

  @Override
  public Map<PolicyId, List<ClaimId>> configureMaximumLossRatio(LossRatio maximumLossRatio) {
    logger.info(
        String.format(
            "Configuring maximum loss ratio to a value of <%s>", maximumLossRatio.getValue()));
    return policyAppService.configureMaximumLossRatio(maximumLossRatio);
  }
}
