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
import ca.ulaval.glo4003.insuring.application.policy.error.*;
import ca.ulaval.glo4003.insuring.application.policy.event.PolicyPurchasedEvent;
import ca.ulaval.glo4003.insuring.application.policy.lossratio.MaximumLossRatioConfigurer;
import ca.ulaval.glo4003.insuring.application.policy.lossratio.MaximumLossRatioProvider;
import ca.ulaval.glo4003.insuring.application.policy.lossratio.PolicyLossRatioCalculator;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PolicyAppServiceImpl implements PolicyAppService {
  public static final LossRatio SMALLEST_MAXIMUM_LOSS_RATIO = new LossRatio(1f);
  public static final LossRatio GREATEST_MAXIMUM_LOSS_RATIO = new LossRatio(4f);

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
  private MaximumLossRatioProvider maximumLossRatioProvider;
  private MaximumLossRatioConfigurer maximumLossRatioConfigurer;
  private PolicyLossRatioCalculator policyLossRatioCalculator;
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
        ServiceLocator.resolve(MaximumLossRatioProvider.class),
        ServiceLocator.resolve(MaximumLossRatioConfigurer.class),
        new PolicyLossRatioCalculator(),
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
      MaximumLossRatioProvider maximumLossRatioProvider,
      MaximumLossRatioConfigurer maximumLossRatioConfigurer,
      PolicyLossRatioCalculator policyLossRatioCalculator,
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
    this.maximumLossRatioProvider = maximumLossRatioProvider;
    this.maximumLossRatioConfigurer = maximumLossRatioConfigurer;
    this.policyLossRatioCalculator = policyLossRatioCalculator;
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
      checkIfPolicyExceedingMaximumLossRatio(policy, claim);
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

  private void checkIfEmptyLossDeclarations(LossDeclarations lossDeclarations) {
    if (lossDeclarations.isEmpty()) {
      throw new EmptyLossDeclarationsError();
    }
  }

  private void checkIfPolicyExceedingMaximumLossRatio(Policy policy, Claim claim) {
    if (isPolicyExceedingMaximumLossRatio(policy, claim)) {
      throw new PolicyExceedingMaximumLossRatioError();
    }
  }

  private boolean isPolicyExceedingMaximumLossRatio(Policy policy, Claim claim) {
    LossRatio policyLossRatio =
        policyLossRatioCalculator.computeLossRatioWithAdditionalClaim(policy, claim);
    LossRatio maximumLossRatio = maximumLossRatioProvider.getMaximumLossRatio();
    return policyLossRatio.isGreaterThan(maximumLossRatio);
  }

  private DateTime computeClaimExpirationDate() {
    return DateTime.now(clockProvider.getClock())
        .plus(claimExpirationPeriodProvider.getClaimExpirationPeriod());
  }

  public Map<PolicyId, List<ClaimId>> configureMaximumLossRatio(LossRatio maximumLossRatio) {
    checkIfValidMaximumLossRatioConfiguration(maximumLossRatio);
    maximumLossRatioConfigurer.configureMaximumLossRatio(maximumLossRatio);
    Map<PolicyId, List<ClaimId>> exceedingClaimsByPolicy = new HashMap<>();
    policyRepository
        .getAll()
        .forEach(x -> appendExceedingClaims(exceedingClaimsByPolicy, maximumLossRatio, x));
    return exceedingClaimsByPolicy;
  }

  private void checkIfValidMaximumLossRatioConfiguration(LossRatio maximumLossRatio) {
    if (!isValidMaximumLossRatioConfiguration(maximumLossRatio)) {
      throw new OutOfBoundMaximumLossRatioError();
    }
  }

  private boolean isValidMaximumLossRatioConfiguration(LossRatio maximumLossRatio) {
    return maximumLossRatio.equals(SMALLEST_MAXIMUM_LOSS_RATIO)
        || maximumLossRatio.equals(GREATEST_MAXIMUM_LOSS_RATIO)
        || maximumLossRatio.isBetween(SMALLEST_MAXIMUM_LOSS_RATIO, GREATEST_MAXIMUM_LOSS_RATIO);
  }

  private void appendExceedingClaims(
      Map<PolicyId, List<ClaimId>> exceedingClaimsByPolicy,
      LossRatio maximumLossRatio,
      Policy policy) {
    List<ClaimId> claimIds =
        policyLossRatioCalculator
            .listNotYetAcceptedClaimsExceedingMaximumLossRatio(policy, maximumLossRatio).stream()
            .map(Claim::getClaimId)
            .collect(Collectors.toList());
    if (!claimIds.isEmpty()) exceedingClaimsByPolicy.put(policy.getPolicyId(), claimIds);
  }
}
