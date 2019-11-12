package ca.ulaval.glo4003.insuring.application.policy;

import ca.ulaval.glo4003.helper.policy.PolicyGenerator;
import ca.ulaval.glo4003.helper.shared.MoneyGenerator;
import ca.ulaval.glo4003.helper.shared.TemporalGenerator;
import ca.ulaval.glo4003.insuring.application.policy.dto.OpenClaimDto;
import ca.ulaval.glo4003.insuring.application.policy.error.CouldNotOpenClaimError;
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
import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.helper.claim.ClaimGenerator.createClaimId;
import static ca.ulaval.glo4003.helper.policy.PolicyGenerator.createPolicyId;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PolicyAppServiceTest {
  private static final String QUOTE_KEY = Faker.instance().internet().uuid();
  private static final Period COVERAGE_PERIOD = TemporalGenerator.createPeriod();
  private static final Date PURCHASE_DATE = TemporalGenerator.createDate();
  private static final Amount COVERAGE_AMOUNT = MoneyGenerator.createAmount();
  private static final PolicyId POLICY_ID = createPolicyId();
  private static final ClaimId CLAIM_ID = createClaimId();
  private static final OpenClaimDto OPEN_CLAIM_DTO = PolicyGenerator.createOpenClaimDto();

  @Mock private Policy policy;
  @Mock private PolicyFactory policyFactory;
  @Mock private PolicyRepository policyRepository;
  @Mock private Claim claim;
  @Mock private ClaimFactory claimFactory;
  @Mock private ClaimRepository claimRepository;

  private PolicyAppService subject;

  @Before
  public void setUp() throws PolicyNotFoundException {
    when(policyFactory.create(any(), any(), any(), any())).thenReturn(policy);
    when(policyRepository.getById(any(PolicyId.class))).thenReturn(policy);
    when(claimFactory.create(any(), any())).thenReturn(claim);
    when(claim.getClaimId()).thenReturn(CLAIM_ID);
    subject = new PolicyAppService(policyFactory, policyRepository, claimFactory, claimRepository);
  }

  @Test
  public void issuingPolicy_shouldIssuePolicy() {
    subject.issuePolicy(QUOTE_KEY, COVERAGE_PERIOD, PURCHASE_DATE, COVERAGE_AMOUNT);

    verify(policy).issue();
  }

  @Test
  public void issuingPolicy_shouldCreatePolicy() throws PolicyAlreadyCreatedException {
    subject.issuePolicy(QUOTE_KEY, COVERAGE_PERIOD, PURCHASE_DATE, COVERAGE_AMOUNT);

    verify(policyRepository).create(policy);
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