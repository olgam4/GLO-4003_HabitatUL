package ca.ulaval.glo4003.insuring.application.policy;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.coverage.application.CoverageDomainService;
import ca.ulaval.glo4003.coverage.application.CoverageDto;
import ca.ulaval.glo4003.coverage.domain.form.BicycleEndorsementForm;
import ca.ulaval.glo4003.insuring.application.policy.dto.InsureBicycleDto;
import ca.ulaval.glo4003.insuring.application.policy.dto.ModifyCoverageDto;
import ca.ulaval.glo4003.insuring.application.policy.dto.OpenClaimDto;
import ca.ulaval.glo4003.insuring.application.policy.error.CouldNotOpenClaimError;
import ca.ulaval.glo4003.insuring.application.policy.error.EmptyLossDeclarationsError;
import ca.ulaval.glo4003.insuring.application.policy.event.PolicyPurchasedEvent;
import ca.ulaval.glo4003.insuring.domain.claim.*;
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
  private PolicyAssembler policyAssembler;
  private PolicyFactory policyFactory;
  private PolicyRepository policyRepository;
  private CoverageDomainService coverageDomainService;
  private ClaimFactory claimFactory;
  private ClaimRepository claimRepository;

  public PolicyAppService() {
    this(
        new PolicyAssembler(),
        new PolicyFactory(ServiceLocator.resolve(ClockProvider.class)),
        ServiceLocator.resolve(PolicyRepository.class),
        new CoverageDomainService(),
        new ClaimFactory(),
        ServiceLocator.resolve(ClaimRepository.class));
  }

  public PolicyAppService(
      PolicyAssembler policyAssembler,
      PolicyFactory policyFactory,
      PolicyRepository policyRepository,
      CoverageDomainService coverageDomainService,
      ClaimFactory claimFactory,
      ClaimRepository claimRepository) {
    this.policyAssembler = policyAssembler;
    this.policyFactory = policyFactory;
    this.policyRepository = policyRepository;
    this.coverageDomainService = coverageDomainService;
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
              policyPurchasedEvent.getPolicyInformation(),
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

  public void insureBicycle(PolicyId policyId, InsureBicycleDto insureBicycleDto) {
    try {
      Policy policy = policyRepository.getById(policyId);
      BicycleEndorsementForm bicycleEndorsementForm =
          policyAssembler.from(insureBicycleDto, policy);
      CoverageDto coverageDto =
          coverageDomainService.requestBicycleEndorsementCoverage(bicycleEndorsementForm);

    } catch (PolicyNotFoundException e) {
      throw new PolicyNotFoundError(policyId);
    }
  }

  public void modifyCoverage(PolicyId policyId, ModifyCoverageDto modifyCoverageDto) {
    try {
      Policy policy = policyRepository.getById(policyId);
    } catch (PolicyNotFoundException e) {
      throw new PolicyNotFoundError(policyId);
    }
  }

  // TODO: complete the modification with this use case
  // public void confirmModification(PolicyId policyId, ModificationId
  // policyModificationId) {}

  public ClaimId openClaim(PolicyId policyId, OpenClaimDto openClaimDto) {
    try {
      LossDeclarations lossDeclarations = openClaimDto.getLossDeclarations();
      checkIfEmptyLossDeclarations(lossDeclarations);
      Policy policy = policyRepository.getById(policyId);
      Claim claim = claimFactory.create(openClaimDto.getSinisterType(), lossDeclarations);
      policy.openClaim(claim);
      claimRepository.create(claim);
      return claim.getClaimId();
    } catch (PolicyNotFoundException e) {
      throw new PolicyNotFoundError(policyId);
    } catch (ClaimAlreadyCreatedException e) {
      throw new CouldNotOpenClaimError();
    }
  }

  private void checkIfEmptyLossDeclarations(LossDeclarations lossDeclarations) {
    if (lossDeclarations.isEmpty()) {
      throw new EmptyLossDeclarationsError();
    }
  }
}
