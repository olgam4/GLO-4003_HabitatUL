package ca.ulaval.glo4003.coverage.application.policy;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.coverage.application.claim.dto.ClaimCreationDto;
import ca.ulaval.glo4003.coverage.application.policy.dto.QuotePurchasedDto;
import ca.ulaval.glo4003.coverage.domain.claim.Claim;
import ca.ulaval.glo4003.coverage.domain.claim.ClaimFactory;
import ca.ulaval.glo4003.coverage.domain.claim.ClaimId;
import ca.ulaval.glo4003.coverage.domain.claim.ClaimRepository;
import ca.ulaval.glo4003.coverage.domain.policy.Policy;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyFactory;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyId;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyRepository;
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

  public void issuePolicy(QuotePurchasedDto quotePurchasedDto) {
    Policy policy = policyFactory.create(quotePurchasedDto.getQuoteId());
    policy.issue();
    policyRepository.create(policy);
  }

  public ClaimId openClaim(PolicyId policyId, ClaimCreationDto claimCreationDto) {
    Policy policy = policyRepository.getById(policyId);
    Claim claim =
        policy.openClaim(
            claimCreationDto.getSinisterType(),
            claimCreationDto.getLossDeclarations(),
            claimFactory);
    claimRepository.create(claim);
    return claim.getClaimId();
  }
}
