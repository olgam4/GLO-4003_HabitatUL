package ca.ulaval.glo4003.insuring.application.policy;

import ca.ulaval.glo4003.coverage.application.CoverageDomainService;
import ca.ulaval.glo4003.coverage.application.CoverageDto;
import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.form.BicycleEndorsementForm;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.helper.claim.LossDeclarationsBuilder;
import ca.ulaval.glo4003.helper.policy.OpenClaimDtoBuilder;
import ca.ulaval.glo4003.insuring.application.policy.dto.InsureBicycleDto;
import ca.ulaval.glo4003.insuring.application.policy.dto.ModifyCoverageDto;
import ca.ulaval.glo4003.insuring.application.policy.dto.OpenClaimDto;
import ca.ulaval.glo4003.insuring.application.policy.dto.PolicyModificationDto;
import ca.ulaval.glo4003.insuring.application.policy.error.CouldNotOpenClaimError;
import ca.ulaval.glo4003.insuring.application.policy.error.EmptyLossDeclarationsError;
import ca.ulaval.glo4003.insuring.application.policy.event.PolicyPurchasedEvent;
import ca.ulaval.glo4003.insuring.domain.claim.*;
import ca.ulaval.glo4003.insuring.domain.claim.exception.ClaimAlreadyCreatedException;
import ca.ulaval.glo4003.insuring.domain.policy.*;
import ca.ulaval.glo4003.insuring.domain.policy.error.PolicyNotFoundError;
import ca.ulaval.glo4003.insuring.domain.policy.exception.PolicyAlreadyCreatedException;
import ca.ulaval.glo4003.insuring.domain.policy.exception.PolicyNotFoundException;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModification;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationFactory;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationId;
import ca.ulaval.glo4003.insuring.domain.policy.modification.modifier.InsureBicyclePolicyInformationModifier;
import ca.ulaval.glo4003.insuring.domain.policy.modification.modifier.PolicyInformationModifier;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.helper.claim.ClaimGenerator.createClaimId;
import static ca.ulaval.glo4003.helper.coverage.CoverageGenerator.createCoverageDto;
import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsGenerator.createPremiumDetails;
import static ca.ulaval.glo4003.helper.policy.PolicyGenerator.*;
import static ca.ulaval.glo4003.helper.policy.PolicyModificationGenerator.createPolicyModificationId;
import static ca.ulaval.glo4003.matcher.PolicyMatcher.matchesBicycleEndorsementForm;
import static ca.ulaval.glo4003.matcher.PolicyMatcher.matchesPolicyModificationDto;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

@RunWith(MockitoJUnitRunner.class)
public class PolicyAppServiceTest {
  private static final PolicyId POLICY_ID = createPolicyId();
  private static final PolicyPurchasedEvent POLICY_PURCHASED_EVENT = createPolicyPurchasedEvent();
  private static final CoverageDetails CURRENT_COVERAGE_DETAILS = createCoverageDetails();
  private static final PremiumDetails CURRENT_PREMIUM_DETAILS = createPremiumDetails();
  private static final CoverageDto COVERAGE_DTO = createCoverageDto();
  private static final PolicyModificationId POLICY_MODIFICATION_ID = createPolicyModificationId();
  private static final InsureBicycleDto INSURING_BICYCLE_DTO = createInsuringBicycleDto();
  private static final ModifyCoverageDto MODIFY_POLICY_DTO = createModifyPolicyDto();
  private static final OpenClaimDto OPEN_CLAIM_DTO = createOpenClaimDto();
  private static final ClaimId CLAIM_ID = createClaimId();

  @Mock private Policy policy;
  @Mock private PolicyFactory policyFactory;
  @Mock private PolicyRepository policyRepository;
  @Mock private CoverageDomainService coverageDomainService;
  @Mock private PolicyModification policyModification;
  @Mock private PolicyModificationFactory policyModificationFactory;
  @Mock private Claim claim;
  @Mock private ClaimFactory claimFactory;
  @Mock private ClaimRepository claimRepository;

  private PolicyAppService subject;
  private PolicyAssembler policyAssembler;

  @Before
  public void setUp() throws PolicyNotFoundException {
    policyAssembler = new PolicyAssembler();
    when(policy.getCurrentCoverageDetails()).thenReturn(CURRENT_COVERAGE_DETAILS);
    when(policy.getCurrentPremiumDetails()).thenReturn(CURRENT_PREMIUM_DETAILS);
    when(policyFactory.create(
            any(String.class),
            any(Period.class),
            any(Date.class),
            any(PolicyInformation.class),
            any(CoverageDetails.class),
            any(PremiumDetails.class)))
        .thenReturn(policy);
    when(policyRepository.getById(any(PolicyId.class))).thenReturn(policy);
    when(coverageDomainService.requestBicycleEndorsementCoverage(any(BicycleEndorsementForm.class)))
        .thenReturn(COVERAGE_DTO);
    when(policyModification.getPolicyModificationId()).thenReturn(POLICY_MODIFICATION_ID);
    when(policyModificationFactory.create(
            any(CoverageDetails.class),
            any(PremiumDetails.class),
            any(PremiumDetails.class),
            any(PolicyInformationModifier.class)))
        .thenReturn(policyModification);
    when(claimFactory.create(any(SinisterType.class), any(LossDeclarations.class)))
        .thenReturn(claim);
    when(claim.getClaimId()).thenReturn(CLAIM_ID);
    subject =
        new PolicyAppService(
            policyAssembler,
            policyFactory,
            policyRepository,
            coverageDomainService,
            policyModificationFactory,
            claimFactory,
            claimRepository);
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
  public void insuringBicycle_shouldCreateAssociatedPolicyModification() {
    subject.insureBicycle(POLICY_ID, INSURING_BICYCLE_DTO);

    verify(policyModificationFactory)
        .create(
            COVERAGE_DTO.getCoverageDetails(),
            CURRENT_PREMIUM_DETAILS,
            COVERAGE_DTO.getPremiumDetails(),
            new InsureBicyclePolicyInformationModifier(INSURING_BICYCLE_DTO.getBicycle()));
  }

  @Test
  public void insuringBicycle_shouldSubmitModification() {
    subject.insureBicycle(POLICY_ID, INSURING_BICYCLE_DTO);

    verify(policy).submitModification(policyModification);
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
  public void modifyingPolicy_shouldGetPolicyById() throws PolicyNotFoundException {
    subject.modifyCoverage(POLICY_ID, MODIFY_POLICY_DTO);

    verify(policyRepository).getById(POLICY_ID);
  }

  @Test(expected = PolicyNotFoundError.class)
  public void modifyingPolicy_withNotExistingPolicy_shouldThrow() throws PolicyNotFoundException {
    when(policyRepository.getById(POLICY_ID)).thenThrow(PolicyNotFoundException.class);

    subject.modifyCoverage(POLICY_ID, MODIFY_POLICY_DTO);
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
