package ca.ulaval.glo4003.coverage.application.policy;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.coverage.application.claim.dto.ClaimCreationDto;
import ca.ulaval.glo4003.coverage.domain.claim.Claim;
import ca.ulaval.glo4003.coverage.domain.claim.ClaimFactory;
import ca.ulaval.glo4003.coverage.domain.claim.ClaimId;
import ca.ulaval.glo4003.coverage.domain.claim.ClaimRepository;
import ca.ulaval.glo4003.coverage.domain.policy.Policy;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyFactory;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyId;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyRepository;
import ca.ulaval.glo4003.coverage.domain.policy.error.PolicyNotFoundError;
import ca.ulaval.glo4003.coverage.domain.policy.exception.PolicyAlreadyCreatedException;
import ca.ulaval.glo4003.coverage.domain.policy.exception.PolicyNotFoundException;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.Period;

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

  public void issuePolicy(String quoteKey, Period coveragePeriod, Date purchaseDate) {
    try {
      Policy policy = policyFactory.create(quoteKey, coveragePeriod, purchaseDate);
      policy.issue();
      policyRepository.create(policy);
    } catch (PolicyAlreadyCreatedException e) {
      // TODO: log event
      // TODO: put in a queue for later reprocessing
      e.printStackTrace();
    }
  }

  public ClaimId openClaim(PolicyId policyId, ClaimCreationDto claimCreationDto) {
    try {
      Policy policy = policyRepository.getById(policyId);
      Claim claim =
          claimFactory.create(
              claimCreationDto.getSinisterType(), claimCreationDto.getLossDeclarations());
      policy.openClaim(claim);
      claimRepository.create(claim);
      return claim.getClaimId();
    } catch (PolicyNotFoundException e) {
      throw new PolicyNotFoundError(policyId);
    }
  }
}
