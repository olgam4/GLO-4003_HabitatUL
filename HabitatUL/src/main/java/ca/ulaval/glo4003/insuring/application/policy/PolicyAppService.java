package ca.ulaval.glo4003.insuring.application.policy;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.insuring.application.policy.dto.ModifyPolicyDto;
import ca.ulaval.glo4003.insuring.application.policy.dto.OpenClaimDto;
import ca.ulaval.glo4003.insuring.application.policy.error.CouldNotOpenClaimError;
import ca.ulaval.glo4003.insuring.application.policy.event.PolicyPurchasedEvent;
import ca.ulaval.glo4003.insuring.domain.claim.Claim;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimFactory;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimRepository;
import ca.ulaval.glo4003.insuring.domain.claim.exception.ClaimAlreadyCreatedException;
import ca.ulaval.glo4003.insuring.domain.policy.Policy;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyFactory;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyRepository;
import ca.ulaval.glo4003.insuring.domain.policy.error.PolicyNotFoundError;
import ca.ulaval.glo4003.insuring.domain.policy.exception.PolicyAlreadyCreatedException;
import ca.ulaval.glo4003.insuring.domain.policy.exception.PolicyNotFoundException;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;

public class PolicyAppService {
  private PolicyFactory policyFactory;
  private PolicyRepository policyRepository;
  private ClaimFactory claimFactory;
  private ClaimRepository claimRepository;

  public PolicyAppService() {
    this(
        new PolicyFactory(ServiceLocator.resolve(ClockProvider.class)),
        ServiceLocator.resolve(PolicyRepository.class),
        new ClaimFactory(),
        ServiceLocator.resolve(ClaimRepository.class));
  }

  public PolicyAppService(
      PolicyFactory policyFactory,
      PolicyRepository policyRepository,
      ClaimFactory claimFactory,
      ClaimRepository claimRepository) {
    this.policyFactory = policyFactory;
    this.policyRepository = policyRepository;
    this.claimFactory = claimFactory;
    this.claimRepository = claimRepository;
  }

  public void issuePolicy(PolicyPurchasedEvent policyPurchasedEvent) {
    try {
      Policy policy =
          policyFactory.create(
              policyPurchasedEvent.getQuoteKey(),
              policyPurchasedEvent.getCoveragePeriod(),
              policyPurchasedEvent.getPurchaseDate(),
              policyPurchasedEvent.getCoverageDetails(),
              policyPurchasedEvent.getPremiumDetails());
      policy.issue();
      policyRepository.create(policy);
    } catch (PolicyAlreadyCreatedException e) {
      // TODO: log event
      // TODO: put in a queue for later reprocessing
      e.printStackTrace();
    }
  }

  public void modifyPolicy(PolicyId policyId, ModifyPolicyDto modifyPolicyDto) {
    try {
      Policy policy = policyRepository.getById(policyId);
    } catch (PolicyNotFoundException e) {
      throw new PolicyNotFoundError(policyId);
    }
  }

  public ClaimId openClaim(PolicyId policyId, OpenClaimDto openClaimDto) {
    try {
      Policy policy = policyRepository.getById(policyId);
      Claim claim =
          claimFactory.create(openClaimDto.getSinisterType(), openClaimDto.getLossDeclarations());
      policy.openClaim(claim);
      claimRepository.create(claim);
      return claim.getClaimId();
    } catch (PolicyNotFoundException e) {
      throw new PolicyNotFoundError(policyId);
    } catch (ClaimAlreadyCreatedException e) {
      throw new CouldNotOpenClaimError();
    }
  }
}
