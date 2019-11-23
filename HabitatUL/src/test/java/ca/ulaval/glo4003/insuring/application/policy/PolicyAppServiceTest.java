package ca.ulaval.glo4003.insuring.application.policy;

import ca.ulaval.glo4003.coverage.application.CoverageDomainService;
import ca.ulaval.glo4003.coverage.application.CoverageDto;
import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.form.BicycleEndorsementForm;
import ca.ulaval.glo4003.coverage.domain.form.CoverageModificationForm;
import ca.ulaval.glo4003.coverage.domain.form.CoverageRenewalForm;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.Bicycle;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.helper.claim.LossDeclarationsBuilder;
import ca.ulaval.glo4003.helper.policy.OpenClaimDtoBuilder;
import ca.ulaval.glo4003.insuring.application.policy.dto.*;
import ca.ulaval.glo4003.insuring.application.policy.error.CouldNotOpenClaimError;
import ca.ulaval.glo4003.insuring.application.policy.error.EmptyCoverageModificationRequestError;
import ca.ulaval.glo4003.insuring.application.policy.error.EmptyLossDeclarationsError;
import ca.ulaval.glo4003.insuring.application.policy.event.PolicyPurchasedEvent;
import ca.ulaval.glo4003.insuring.application.policy.renewal.PolicyRenewalProcessor;
import ca.ulaval.glo4003.insuring.domain.claim.*;
import ca.ulaval.glo4003.insuring.domain.claim.exception.ClaimAlreadyCreatedException;
import ca.ulaval.glo4003.insuring.domain.policy.*;
import ca.ulaval.glo4003.insuring.domain.policy.error.PolicyNotFoundError;
import ca.ulaval.glo4003.insuring.domain.policy.exception.PolicyAlreadyCreatedException;
import ca.ulaval.glo4003.insuring.domain.policy.exception.PolicyNotFoundException;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModification;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationId;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationValidityPeriodProvider;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyCoveragePeriodProvider;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewal;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalId;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalPeriodProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.logging.Logger;

import static ca.ulaval.glo4003.helper.claim.ClaimGenerator.createClaimId;
import static ca.ulaval.glo4003.helper.coverage.CoverageGenerator.createCoverageDto;
import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsGenerator.createPremiumDetails;
import static ca.ulaval.glo4003.helper.policy.PolicyGenerator.*;
import static ca.ulaval.glo4003.helper.policy.PolicyInformationGenerator.createPolicyInformation;
import static ca.ulaval.glo4003.helper.policy.PolicyModificationGenerator.createPolicyModificationId;
import static ca.ulaval.glo4003.helper.policy.PolicyRenewalGenerator.createPolicyRenewalId;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.createDateTime;
import static ca.ulaval.glo4003.matcher.PolicyMatcher.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

@RunWith(MockitoJUnitRunner.class)
public class PolicyAppServiceTest {
  private static final PolicyId POLICY_ID = createPolicyId();
  private static final PolicyPurchasedEvent POLICY_PURCHASED_EVENT = createPolicyPurchasedEvent();
  private static final PolicyInformation POLICY_INFORMATION = createPolicyInformation();
  private static final CoverageDetails CURRENT_COVERAGE_DETAILS = createCoverageDetails();
  private static final PremiumDetails CURRENT_PREMIUM_DETAILS = createPremiumDetails();
  private static final CoverageDto COVERAGE_DTO = createCoverageDto();
  private static final InsureBicycleDto INSURING_BICYCLE_DTO = createInsureBicycleDto();
  private static final ModifyCoverageDto MODIFY_COVERAGE_DTO = createModifyCoverageDto();
  private static final PolicyModificationId POLICY_MODIFICATION_ID = createPolicyModificationId();
  private static final TriggerRenewalDto TRIGGER_RENEWAL_DTO = createTriggerRenewalDto();
  private static final PolicyRenewalId POLICY_RENEWAL_ID = createPolicyRenewalId();
  private static final DateTime RENEWAL_EFFECTIVE_DATE = createDateTime();
  private static final OpenClaimDto OPEN_CLAIM_DTO = createOpenClaimDto();
  private static final ClaimId CLAIM_ID = createClaimId();

  @Mock private Policy policy;
  @Mock private PolicyFactory policyFactory;
  @Mock private PolicyRepository policyRepository;
  @Mock private CoverageDomainService coverageDomainService;
  @Mock private PolicyModification policyModification;
  @Mock private PolicyModificationValidityPeriodProvider policyModificationValidityPeriodProvider;
  @Mock private PolicyRenewal policyRenewal;
  @Mock private PolicyRenewalPeriodProvider policyRenewalPeriodProvider;
  @Mock private PolicyCoveragePeriodProvider policyCoveragePeriodProvider;
  @Mock private PolicyRenewalProcessor policyRenewalProcessor;
  @Mock private Claim claim;
  @Mock private ClaimFactory claimFactory;
  @Mock private ClaimRepository claimRepository;
  @Mock private Logger logger;

  private PolicyAppService subject;
  private PolicyAssembler policyAssembler;

  @Before
  public void setUp() throws PolicyNotFoundException {
    policyAssembler = new PolicyAssembler();
    when(policyFactory.create(
            any(String.class),
            any(Period.class),
            any(Date.class),
            any(PolicyInformation.class),
            any(CoverageDetails.class),
            any(PremiumDetails.class)))
        .thenReturn(policy);
    when(policy.getPolicyInformation()).thenReturn(POLICY_INFORMATION);
    when(policy.getCoverageDetails()).thenReturn(CURRENT_COVERAGE_DETAILS);
    when(policy.getPremiumDetails()).thenReturn(CURRENT_PREMIUM_DETAILS);
    when(policy.submitInsureBicycleModification(
            any(Bicycle.class),
            any(CoverageDetails.class),
            any(PremiumDetails.class),
            any(PolicyModificationValidityPeriodProvider.class)))
        .thenReturn(policyModification);
    when(policy.submitCoverageModification(
            any(CoverageDetails.class),
            any(PremiumDetails.class),
            any(PolicyModificationValidityPeriodProvider.class)))
        .thenReturn(policyModification);
    when(policy.submitCoverageRenewal(
            any(CoverageDetails.class),
            any(PremiumDetails.class),
            any(PolicyRenewalPeriodProvider.class),
            any(PolicyCoveragePeriodProvider.class)))
        .thenReturn(policyRenewal);
    when(policy.acceptRenewal(any(PolicyRenewalId.class))).thenReturn(policyRenewal);
    when(policyRenewal.getEffectiveDateTime()).thenReturn(RENEWAL_EFFECTIVE_DATE);
    when(policyModification.getPolicyModificationId()).thenReturn(POLICY_MODIFICATION_ID);
    when(policyRepository.getById(any(PolicyId.class))).thenReturn(policy);
    when(coverageDomainService.requestBicycleEndorsementCoverage(any(BicycleEndorsementForm.class)))
        .thenReturn(COVERAGE_DTO);
    when(coverageDomainService.requestCoverageModification(any(CoverageModificationForm.class)))
        .thenReturn(COVERAGE_DTO);
    when(coverageDomainService.requestCoverageRenewal(any(CoverageRenewalForm.class)))
        .thenReturn(COVERAGE_DTO);
    when(claimFactory.create(any(SinisterType.class), any(LossDeclarations.class)))
        .thenReturn(claim);
    when(claim.getClaimId()).thenReturn(CLAIM_ID);
    subject =
        new PolicyAppServiceImpl(
            policyAssembler,
            policyFactory,
            policyRepository,
            coverageDomainService,
            policyModificationValidityPeriodProvider,
            policyRenewalPeriodProvider,
            policyCoveragePeriodProvider,
            policyRenewalProcessor,
            claimFactory,
            claimRepository,
            logger);
  }

  @Test
  public void issuingPolicy_shouldIssuePolicy() {
    subject.issuePolicy(POLICY_PURCHASED_EVENT);

    verify(policy).issue();
  }

  @Test
  public void issuingPolicy_shouldCreatePolicy() throws PolicyAlreadyCreatedException {
    subject.issuePolicy(POLICY_PURCHASED_EVENT);

    verify(policyRepository).create(policy);
  }

  @Test
  public void issuingPolicy_shouldLogPolicyAlreadyCreatedExceptionsAsSevere()
      throws PolicyAlreadyCreatedException {
    doThrow(new PolicyAlreadyCreatedException()).when(policyRepository).create(any(Policy.class));

    subject.issuePolicy(POLICY_PURCHASED_EVENT);

    verify(logger).severe(anyString());
  }

  @Test
  public void insuringBicycle_shouldGetPolicyById() throws PolicyNotFoundException {
    subject.insureBicycle(POLICY_ID, INSURING_BICYCLE_DTO);

    verify(policyRepository).getById(POLICY_ID);
  }

  @Test
  public void insuringBicycle_shouldRequestBicycleEndorsementCoverage() {
    subject.insureBicycle(POLICY_ID, INSURING_BICYCLE_DTO);

    verify(coverageDomainService)
        .requestBicycleEndorsementCoverage(
            argThat(matchesBicycleEndorsementForm(policy, INSURING_BICYCLE_DTO)));
  }

  @Test
  public void insuringBicycle_shouldSubmitModification() {
    subject.insureBicycle(POLICY_ID, INSURING_BICYCLE_DTO);

    verify(policy)
        .submitInsureBicycleModification(
            INSURING_BICYCLE_DTO.getBicycle(),
            COVERAGE_DTO.getCoverageDetails(),
            COVERAGE_DTO.getPremiumDetails(),
            policyModificationValidityPeriodProvider);
  }

  @Test
  public void insuringBicycle_shouldUpdatePolicy() throws PolicyNotFoundException {
    subject.insureBicycle(POLICY_ID, INSURING_BICYCLE_DTO);

    verify(policyRepository).update(policy);
  }

  @Test
  public void insuringBicycle_shouldProduceCorrespondingPolicyModificationDto() {
    PolicyModificationDto policyModificationDto =
        subject.insureBicycle(POLICY_ID, INSURING_BICYCLE_DTO);

    assertThat(policyModificationDto, matchesPolicyModificationDto(policyModification));
  }

  @Test(expected = PolicyNotFoundError.class)
  public void insuringBicycle_withNotExistingPolicy_shouldThrow() throws PolicyNotFoundException {
    when(policyRepository.getById(POLICY_ID)).thenThrow(PolicyNotFoundException.class);

    subject.insureBicycle(POLICY_ID, INSURING_BICYCLE_DTO);
  }

  @Test
  public void modifyingCoverage_shouldGetPolicyById() throws PolicyNotFoundException {
    subject.modifyCoverage(POLICY_ID, MODIFY_COVERAGE_DTO);

    verify(policyRepository).getById(POLICY_ID);
  }

  @Test
  public void modifyingCoverage_shouldRequestCoverageModification() {
    subject.modifyCoverage(POLICY_ID, MODIFY_COVERAGE_DTO);

    verify(coverageDomainService)
        .requestCoverageModification(
            argThat(matchesCoverageModificationForm(policy, MODIFY_COVERAGE_DTO)));
  }

  @Test
  public void modifyingCoverage_shouldSubmitModification() {
    subject.modifyCoverage(POLICY_ID, MODIFY_COVERAGE_DTO);

    verify(policy)
        .submitCoverageModification(
            COVERAGE_DTO.getCoverageDetails(),
            COVERAGE_DTO.getPremiumDetails(),
            policyModificationValidityPeriodProvider);
  }

  @Test
  public void modifyingCoverage_shouldUpdatePolicy() throws PolicyNotFoundException {
    subject.modifyCoverage(POLICY_ID, MODIFY_COVERAGE_DTO);

    verify(policyRepository).update(policy);
  }

  @Test
  public void modifyingCoverage_shouldProduceCorrespondingPolicyModificationDto() {
    PolicyModificationDto policyModificationDto =
        subject.modifyCoverage(POLICY_ID, MODIFY_COVERAGE_DTO);

    assertThat(policyModificationDto, matchesPolicyModificationDto(policyModification));
  }

  @Test(expected = PolicyNotFoundError.class)
  public void modifyingCoverage_withNotExistingPolicy_shouldThrow() throws PolicyNotFoundException {
    when(policyRepository.getById(POLICY_ID)).thenThrow(PolicyNotFoundException.class);

    subject.modifyCoverage(POLICY_ID, MODIFY_COVERAGE_DTO);
  }

  @Test(expected = EmptyCoverageModificationRequestError.class)
  public void modifyingCoverage_withEmptyCoverageModificationRequest_shouldThrow() {
    subject.modifyCoverage(POLICY_ID, createEmptyModifyCoverageDto());
  }

  @Test
  public void confirmingModification_shouldGetPolicyById() throws PolicyNotFoundException {
    subject.confirmModification(POLICY_ID, POLICY_MODIFICATION_ID);

    verify(policyRepository).getById(POLICY_ID);
  }

  @Test
  public void confirmingModification_shouldConfirmModification() {
    subject.confirmModification(POLICY_ID, POLICY_MODIFICATION_ID);

    verify(policy).confirmModification(POLICY_MODIFICATION_ID);
  }

  @Test
  public void confirmingModification_shouldUpdatePolicy() throws PolicyNotFoundException {
    subject.confirmModification(POLICY_ID, POLICY_MODIFICATION_ID);

    verify(policyRepository).update(policy);
  }

  @Test
  public void confirmingModification_shouldProduceCorrespondingPolicyDto() {
    PolicyDto policyDto = subject.confirmModification(POLICY_ID, POLICY_MODIFICATION_ID);

    assertThat(policyDto, matchesPolicyDto(policy));
  }

  @Test(expected = PolicyNotFoundError.class)
  public void confirmingModification_withNotExistingPolicy_shouldThrow()
      throws PolicyNotFoundException {
    when(policyRepository.getById(POLICY_ID)).thenThrow(PolicyNotFoundException.class);

    subject.confirmModification(POLICY_ID, POLICY_MODIFICATION_ID);
  }

  @Test
  public void triggeringRenewal_shouldGetPolicyById() throws PolicyNotFoundException {
    subject.triggerRenewal(POLICY_ID, TRIGGER_RENEWAL_DTO);

    verify(policyRepository).getById(POLICY_ID);
  }

  @Test
  public void triggeringRenewal_shouldRequestCoverageRenewal() {
    subject.triggerRenewal(POLICY_ID, TRIGGER_RENEWAL_DTO);

    verify(coverageDomainService)
        .requestCoverageRenewal(argThat(matchesCoverageRenewalForm(policy, TRIGGER_RENEWAL_DTO)));
  }

  @Test
  public void triggeringRenewal_shouldSubmitRenewal() {
    subject.triggerRenewal(POLICY_ID, TRIGGER_RENEWAL_DTO);

    verify(policy)
        .submitCoverageRenewal(
            COVERAGE_DTO.getCoverageDetails(),
            COVERAGE_DTO.getPremiumDetails(),
            policyRenewalPeriodProvider,
            policyCoveragePeriodProvider);
  }

  @Test
  public void triggeringRenewal_shouldUpdatePolicy() throws PolicyNotFoundException {
    subject.triggerRenewal(POLICY_ID, TRIGGER_RENEWAL_DTO);

    verify(policyRepository).update(policy);
  }

  @Test
  public void triggeringRenewal_shouldProduceCorrespondingPolicyModificationDto() {
    PolicyRenewalDto policyRenewalDto = subject.triggerRenewal(POLICY_ID, TRIGGER_RENEWAL_DTO);

    assertThat(policyRenewalDto, matchesPolicyRenewalDto(policyRenewal));
  }

  @Test(expected = PolicyNotFoundError.class)
  public void triggeringRenewal_withNotExistingPolicy_shouldThrow() throws PolicyNotFoundException {
    when(policyRepository.getById(POLICY_ID)).thenThrow(PolicyNotFoundException.class);

    subject.triggerRenewal(POLICY_ID, TRIGGER_RENEWAL_DTO);
  }

  @Test
  public void acceptingRenewal_shouldGetPolicyById() throws PolicyNotFoundException {
    subject.acceptRenewal(POLICY_ID, POLICY_RENEWAL_ID);

    verify(policyRepository).getById(POLICY_ID);
  }

  @Test
  public void acceptingRenewal_shouldAcceptRenewal() {
    subject.acceptRenewal(POLICY_ID, POLICY_RENEWAL_ID);

    verify(policy).acceptRenewal(POLICY_RENEWAL_ID);
  }

  @Test
  public void acceptingRenewal_shouldScheduleRenewalProcessing() {
    subject.acceptRenewal(POLICY_ID, POLICY_RENEWAL_ID);

    verify(policyRenewalProcessor)
        .scheduleRenewal(POLICY_ID, POLICY_RENEWAL_ID, RENEWAL_EFFECTIVE_DATE);
  }

  @Test
  public void acceptingRenewal_shouldUpdatePolicy() throws PolicyNotFoundException {
    subject.acceptRenewal(POLICY_ID, POLICY_RENEWAL_ID);

    verify(policyRepository).update(policy);
  }

  @Test(expected = PolicyNotFoundError.class)
  public void acceptingRenewal_withNotExistingPolicy_shouldThrow() throws PolicyNotFoundException {
    when(policyRepository.getById(POLICY_ID)).thenThrow(PolicyNotFoundException.class);

    subject.acceptRenewal(POLICY_ID, POLICY_RENEWAL_ID);
  }

  @Test
  public void cancellingRenewal_shouldGetPolicyById() throws PolicyNotFoundException {
    subject.cancelRenewal(POLICY_ID, POLICY_RENEWAL_ID);

    verify(policyRepository).getById(POLICY_ID);
  }

  @Test
  public void cancellingRenewal_shouldCancelRenewal() {
    subject.cancelRenewal(POLICY_ID, POLICY_RENEWAL_ID);

    verify(policy).cancelRenewal(POLICY_RENEWAL_ID);
  }

  @Test
  public void cancellingRenewal_shouldCancelRenewalProcessing() {
    subject.cancelRenewal(POLICY_ID, POLICY_RENEWAL_ID);

    verify(policyRenewalProcessor).cancelRenewal(POLICY_ID, POLICY_RENEWAL_ID);
  }

  @Test
  public void cancellingRenewal_shouldUpdatePolicy() throws PolicyNotFoundException {
    subject.cancelRenewal(POLICY_ID, POLICY_RENEWAL_ID);

    verify(policyRepository).update(policy);
  }

  @Test(expected = PolicyNotFoundError.class)
  public void cancellingRenewal_withNotExistingPolicy_shouldThrow() throws PolicyNotFoundException {
    when(policyRepository.getById(POLICY_ID)).thenThrow(PolicyNotFoundException.class);

    subject.cancelRenewal(POLICY_ID, POLICY_RENEWAL_ID);
  }

  @Test
  public void openingClaim_shouldGetPolicyById() throws PolicyNotFoundException {
    subject.openClaim(POLICY_ID, OPEN_CLAIM_DTO);

    verify(policyRepository).getById(POLICY_ID);
  }

  @Test
  public void openingClaim_shouldOpenClaim() {
    subject.openClaim(POLICY_ID, OPEN_CLAIM_DTO);

    verify(policy).openClaim(claim);
  }

  @Test
  public void openingClaim_shouldCreateClaim() throws ClaimAlreadyCreatedException {
    subject.openClaim(POLICY_ID, OPEN_CLAIM_DTO);

    verify(claimRepository).create(claim);
  }

  @Test
  public void openingClaim_shouldReturnClaimId() {
    ClaimId claimId = subject.openClaim(POLICY_ID, OPEN_CLAIM_DTO);

    assertEquals(CLAIM_ID, claimId);
  }

  @Test(expected = EmptyLossDeclarationsError.class)
  public void openingClaim_withEmptyLossDeclarations_shouldThrow() {
    LossDeclarations emptyLossDeclarations = LossDeclarationsBuilder.aLossDeclaration().build();
    OpenClaimDto openClaimDtoWithEmptyLossDeclarations =
        OpenClaimDtoBuilder.anOpenClaimDto().withLossDeclarations(emptyLossDeclarations).build();

    subject.openClaim(POLICY_ID, openClaimDtoWithEmptyLossDeclarations);
  }

  @Test(expected = PolicyNotFoundError.class)
  public void openingClaim_withNotExistingPolicy_shouldThrow() throws PolicyNotFoundException {
    when(policyRepository.getById(POLICY_ID)).thenThrow(PolicyNotFoundException.class);

    subject.openClaim(POLICY_ID, OPEN_CLAIM_DTO);
  }

  @Test(expected = CouldNotOpenClaimError.class)
  public void openingClaim_withAlreadyCreatedClaim_shouldThrow()
      throws ClaimAlreadyCreatedException {
    Mockito.doThrow(ClaimAlreadyCreatedException.class)
        .when(claimRepository)
        .create(any(Claim.class));

    subject.openClaim(POLICY_ID, OPEN_CLAIM_DTO);
  }
}
