package ca.ulaval.glo4003.insuring.application.policy;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.coverage.application.CoverageAppService;
import ca.ulaval.glo4003.coverage.application.CoverageDto;
import ca.ulaval.glo4003.coverage.domain.form.BicycleEndorsementForm;
import ca.ulaval.glo4003.coverage.domain.form.CoverageModificationForm;
import ca.ulaval.glo4003.coverage.domain.form.CoverageRenewalForm;
import ca.ulaval.glo4003.insuring.application.policy.claimexpiration.ClaimExpirationPeriodProvider;
import ca.ulaval.glo4003.insuring.application.policy.claimexpiration.ClaimExpirationProcessor;
import ca.ulaval.glo4003.insuring.application.policy.dto.*;
import ca.ulaval.glo4003.insuring.application.policy.error.CouldNotOpenClaimError;
import ca.ulaval.glo4003.insuring.application.policy.error.EmptyCoverageModificationRequestError;
import ca.ulaval.glo4003.insuring.application.policy.error.EmptyLossDeclarationsError;
import ca.ulaval.glo4003.insuring.application.policy.event.PolicyPurchasedEvent;
import ca.ulaval.glo4003.insuring.application.policy.renewal.PolicyRenewalProcessor;
import ca.ulaval.glo4003.insuring.domain.claim.*;
import ca.ulaval.glo4003.insuring.domain.claim.exception.ClaimAlreadyCreatedException;
import ca.ulaval.glo4003.insuring.domain.policy.Policy;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyFactory;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyRepository;
import ca.ulaval.glo4003.insuring.domain.policy.error.PolicyNotFoundError;
import ca.ulaval.glo4003.insuring.domain.policy.exception.PolicyAlreadyCreatedException;
import ca.ulaval.glo4003.insuring.domain.policy.exception.PolicyNotFoundException;
import ca.ulaval.glo4003.insuring.domain.policy.lossratio.LossRatio;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModification;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationId;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationValidityPeriodProvider;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyCoveragePeriodProvider;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewal;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalId;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalPeriodProvider;
import ca.ulaval.glo4003.shared.application.logging.Logger;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;

public class PolicyAppServiceImpl implements PolicyAppService {
  private PolicyAssembler policyAssembler;
  private PolicyFactory policyFactory;
  private PolicyRepository policyRepository;
  private CoverageAppService coverageAppService;
  private PolicyModificationValidityPeriodProvider policyModificationValidityPeriodProvider;
  private PolicyRenewalPeriodProvider policyRenewalPeriodProvider;
  private PolicyCoveragePeriodProvider policyCoveragePeriodProvider;
  private PolicyRenewalProcessor policyRenewalProcessor;
  private ClaimFactory claimFactory;
  private ClaimRepository claimRepository;
  private ClaimExpirationPeriodProvider claimExpirationPeriodProvider;
  private ClaimExpirationProcessor claimExpirationProcessor;
  private ClockProvider clockProvider;
  private Logger logger;

  public PolicyAppServiceImpl() {
    this(
        new PolicyAssembler(),
        new PolicyFactory(ServiceLocator.resolve(ClockProvider.class)),
        ServiceLocator.resolve(PolicyRepository.class),
        new CoverageAppService(),
        ServiceLocator.resolve(PolicyModificationValidityPeriodProvider.class),
        ServiceLocator.resolve(PolicyRenewalPeriodProvider.class),
        ServiceLocator.resolve(PolicyCoveragePeriodProvider.class),
        ServiceLocator.resolve(PolicyRenewalProcessor.class),
        new ClaimFactory(ServiceLocator.resolve(ClockProvider.class)),
        ServiceLocator.resolve(ClaimRepository.class),
        ServiceLocator.resolve(ClaimExpirationPeriodProvider.class),
        ServiceLocator.resolve(ClaimExpirationProcessor.class),
        ServiceLocator.resolve(ClockProvider.class),
        ServiceLocator.resolve(Logger.class));
  }

  public PolicyAppServiceImpl(
      PolicyAssembler policyAssembler,
      PolicyFactory policyFactory,
      PolicyRepository policyRepository,
      CoverageAppService coverageAppService,
      PolicyModificationValidityPeriodProvider policyModificationValidityPeriodProvider,
      PolicyRenewalPeriodProvider policyRenewalPeriodProvider,
      PolicyCoveragePeriodProvider policyCoveragePeriodProvider,
      PolicyRenewalProcessor policyRenewalProcessor,
      ClaimFactory claimFactory,
      ClaimRepository claimRepository,
      ClaimExpirationPeriodProvider claimExpirationPeriodProvider,
      ClaimExpirationProcessor claimExpirationProcessor,
      ClockProvider clockProvider,
      Logger logger) {
    this.policyAssembler = policyAssembler;
    this.policyFactory = policyFactory;
    this.policyRepository = policyRepository;
    this.coverageAppService = coverageAppService;
    this.policyModificationValidityPeriodProvider = policyModificationValidityPeriodProvider;
    this.policyRenewalPeriodProvider = policyRenewalPeriodProvider;
    this.policyCoveragePeriodProvider = policyCoveragePeriodProvider;
    this.policyRenewalProcessor = policyRenewalProcessor;
    this.claimFactory = claimFactory;
    this.claimRepository = claimRepository;
    this.claimExpirationPeriodProvider = claimExpirationPeriodProvider;
    this.claimExpirationProcessor = claimExpirationProcessor;
    this.clockProvider = clockProvider;
    this.logger = logger;
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
      logger.severe(e.toString());
    }
  }

  public PolicyModificationDto insureBicycle(PolicyId policyId, InsureBicycleDto insureBicycleDto) {
    try {
      Policy policy = policyRepository.getById(policyId);
      BicycleEndorsementForm bicycleEndorsementForm =
          policyAssembler.from(insureBicycleDto, policy);
      CoverageDto coverageDto =
          coverageAppService.requestBicycleEndorsementCoverage(bicycleEndorsementForm);
      PolicyModification policyModification =
          policy.submitInsureBicycleModification(
              insureBicycleDto.getBicycle(),
              coverageDto.getCoverageDetails(),
              coverageDto.getPremiumDetails(),
              policyModificationValidityPeriodProvider);
      policyRepository.update(policy);
      return policyAssembler.from(policyModification);
    } catch (PolicyNotFoundException e) {
      throw new PolicyNotFoundError(policyId);
    }
  }

  public PolicyModificationDto modifyCoverage(
      PolicyId policyId, ModifyCoverageDto modifyCoverageDto) {
    try {
      Policy policy = policyRepository.getById(policyId);
      CoverageModificationForm coverageModificationForm =
          policyAssembler.from(modifyCoverageDto, policy);
      checkIfEmptyCoverageModificationRequest(coverageModificationForm);
      CoverageDto coverageDto =
          coverageAppService.requestCoverageModification(coverageModificationForm);
      PolicyModification policyModification =
          policy.submitCoverageModification(
              coverageDto.getCoverageDetails(),
              coverageDto.getPremiumDetails(),
              policyModificationValidityPeriodProvider);
      policyRepository.update(policy);
      return policyAssembler.from(policyModification);
    } catch (PolicyNotFoundException e) {
      throw new PolicyNotFoundError(policyId);
    }
  }

  private void checkIfEmptyCoverageModificationRequest(
      CoverageModificationForm coverageModificationForm) {
    if (!coverageModificationForm.isFilled()) {
      throw new EmptyCoverageModificationRequestError();
    }
  }

  public PolicyDto confirmModification(
      PolicyId policyId, PolicyModificationId policyModificationId) {
    try {
      Policy policy = policyRepository.getById(policyId);
      policy.confirmModification(policyModificationId);
      // TODO: process to payment here
      policyRepository.update(policy);
      return policyAssembler.from(policy);
    } catch (PolicyNotFoundException e) {
      throw new PolicyNotFoundError(policyId);
    }
  }

  public PolicyRenewalDto triggerRenewal(PolicyId policyId, TriggerRenewalDto triggerRenewalDto) {
    try {
      Policy policy = policyRepository.getById(policyId);
      CoverageRenewalForm coverageRenewalForm = policyAssembler.from(triggerRenewalDto, policy);
      CoverageDto coverageDto = coverageAppService.requestCoverageRenewal(coverageRenewalForm);
      PolicyRenewal policyRenewal =
          policy.submitCoverageRenewal(
              coverageDto.getCoverageDetails(),
              coverageDto.getPremiumDetails(),
              policyRenewalPeriodProvider,
              policyCoveragePeriodProvider);
      policyRepository.update(policy);
      return policyAssembler.from(policyRenewal);
    } catch (PolicyNotFoundException e) {
      throw new PolicyNotFoundError(policyId);
    }
  }

  public void acceptRenewal(PolicyId policyId, PolicyRenewalId policyRenewalId) {
    try {
      Policy policy = policyRepository.getById(policyId);
      PolicyRenewal policyRenewal = policy.acceptRenewal(policyRenewalId);
      policyRenewalProcessor.scheduleRenewal(
          policyId, policyRenewalId, policyRenewal.getEffectiveDateTime());
      policyRepository.update(policy);
    } catch (PolicyNotFoundException e) {
      throw new PolicyNotFoundError(policyId);
    }
  }

  public void cancelRenewal(PolicyId policyId, PolicyRenewalId policyRenewalId) {
    try {
      Policy policy = policyRepository.getById(policyId);
      policy.cancelRenewal(policyRenewalId);
      policyRenewalProcessor.cancelRenewal(policyId, policyRenewalId);
      policyRepository.update(policy);
    } catch (PolicyNotFoundException e) {
      throw new PolicyNotFoundError(policyId);
    }
  }

  public ClaimId openClaim(PolicyId policyId, OpenClaimDto openClaimDto) {
    try {
      LossDeclarations lossDeclarations = openClaimDto.getLossDeclarations();
      checkIfEmptyLossDeclarations(lossDeclarations);
      Policy policy = policyRepository.getById(policyId);
      Claim claim = claimFactory.create(openClaimDto.getSinisterType(), lossDeclarations);
      policy.openClaim(claim);
      claimExpirationProcessor.scheduleExpiration(claim.getClaimId(), computeClaimExpirationDate());
      claimRepository.create(claim);
      return claim.getClaimId();
    } catch (PolicyNotFoundException e) {
      throw new PolicyNotFoundError(policyId);
    } catch (ClaimAlreadyCreatedException e) {
      throw new CouldNotOpenClaimError();
    }
  }

  private DateTime computeClaimExpirationDate() {
    return DateTime.now(clockProvider.getClock())
        .plus(claimExpirationPeriodProvider.getClaimExpirationPeriod());
  }

  private void checkIfEmptyLossDeclarations(LossDeclarations lossDeclarations) {
    if (lossDeclarations.isEmpty()) {
      throw new EmptyLossDeclarationsError();
    }
  }

  public void configureMaximumLossRatio(LossRatio maximumLossRatio) {
    // TODO: validate new loss ratio between 1 and 4
    // TODO: use configurer to set new value
    // TODO: once value updated, iterate over all policies to get
    // TODO: the list of claims not yet accepted exceeding the new loss ratio
    // TODO: basically iterates over all policies and compute loss ratio
    // TODO: if > new value, return list of claims not yet accepted
  }
}
