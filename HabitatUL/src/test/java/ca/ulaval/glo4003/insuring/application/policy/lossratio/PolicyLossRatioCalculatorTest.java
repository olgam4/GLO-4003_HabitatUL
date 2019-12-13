package ca.ulaval.glo4003.insuring.application.policy.lossratio;

import ca.ulaval.glo4003.coverage.helper.coverage.CoverageDetailsBuilder;
import ca.ulaval.glo4003.insuring.domain.claim.Claim;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimRepository;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimStatus;
import ca.ulaval.glo4003.insuring.domain.policy.Policy;
import ca.ulaval.glo4003.insuring.domain.policy.lossratio.LossRatio;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static ca.ulaval.glo4003.insuring.domain.claim.ClaimStatus.*;
import static ca.ulaval.glo4003.insuring.helper.claim.ClaimGenerator.createClaimId;
import static ca.ulaval.glo4003.insuring.helper.policy.LossRatioGenerator.createLossRatio;
import static ca.ulaval.glo4003.shared.helper.MoneyGenerator.*;
import static ca.ulaval.glo4003.shared.helper.TemporalGenerator.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PolicyLossRatioCalculatorTest {
  private static final Period POLICY_COVERAGE_PERIOD = createPeriod();
  private static final Amount PERSONAL_PROPERTY_MAXIMUM_TOTAL_LOSSES =
      createAmountGreaterThanZero();
  private static final LossRatio MAXIMUM_LOSS_RATIO = createLossRatio();
  private static final Amount PERSONAL_PROPERTY_COVERAGE_AMOUNT =
      PERSONAL_PROPERTY_MAXIMUM_TOTAL_LOSSES.divide(MAXIMUM_LOSS_RATIO.getValue());
  private static final Amount AMOUNT_GREATER_THAN_PERSONAL_PROPERTY_MAXIMUM_TOTAL_LOSSES =
      createAmountGreaterThan(PERSONAL_PROPERTY_MAXIMUM_TOTAL_LOSSES);
  private static final ClaimId FIRST_CLAIM_ID = createClaimId();
  private static final ClaimId SECOND_CLAIM_ID = createClaimId();
  private static final ClaimId EXPIRED_CLAIM_ID = createClaimId();
  private static final ClaimId PREVIOUS_COVERAGE_PERIOD_CLAIM_ID = createClaimId();
  private static final List<ClaimId> CLAIM_IDS =
      Arrays.asList(
          FIRST_CLAIM_ID, SECOND_CLAIM_ID, EXPIRED_CLAIM_ID, PREVIOUS_COVERAGE_PERIOD_CLAIM_ID);

  @Mock private Policy policy;
  @Mock private ClaimRepository claimRepository;
  @Mock private Claim firstClaim;
  @Mock private Claim secondClaim;
  @Mock private Claim expiredClaim;
  @Mock private Claim previousCoveragePeriodClaim;
  @Mock private Claim additionalClaim;

  private PolicyLossRatioCalculator subject;

  @Before
  public void setUp() {
    setUpPolicy();
    setUpClaimsPersonalPropertyLosses(Amount.ZERO, Amount.ZERO, Amount.ZERO, Amount.ZERO);
    setUpClaim(firstClaim, RECEIVED, createDateBetween(POLICY_COVERAGE_PERIOD));
    setUpClaim(secondClaim, UNDER_ANALYSIS, createDateBetween(POLICY_COVERAGE_PERIOD));
    setUpClaim(expiredClaim, EXPIRED, createDateBetween(POLICY_COVERAGE_PERIOD));
    setUpClaim(
        previousCoveragePeriodClaim, PAID, createDateBefore(POLICY_COVERAGE_PERIOD.getStartDate()));
    setUpClaim(additionalClaim, RECEIVED, createDateBetween(POLICY_COVERAGE_PERIOD));
    setUpClaimRepository();
    subject = new PolicyLossRatioCalculator(claimRepository);
  }

  @Test
  public void computingLossRatioWithAdditionalClaim_shouldConsiderAdditionalClaim() {
    Amount firstClaimAmount = createAmountGreaterThanZero();
    Amount secondClaimAmount = createAmountGreaterThanZero();
    setUpClaimsPersonalPropertyLosses(
        firstClaimAmount,
        secondClaimAmount,
        createAmountGreaterThanZero(),
        createAmountGreaterThanZero());
    Amount additionalClaimAmount = createAmountGreaterThanZero();
    when(additionalClaim.computePersonalPropertyLosses()).thenReturn(additionalClaimAmount);

    LossRatio lossRatio = subject.computeLossRatioWithAdditionalClaim(policy, additionalClaim);

    LossRatio expectedLossRatio =
        new LossRatio(
            PERSONAL_PROPERTY_COVERAGE_AMOUNT,
            firstClaimAmount.add(secondClaimAmount).add(additionalClaimAmount));
    assertEquals(expectedLossRatio, lossRatio);
  }

  @Test
  public void listingNotYetAcceptedClaimsExceedingMaximumLossRatio_shouldFindAllPolicyClaimsById() {
    subject.listNotYetAcceptedClaimsExceedingMaximumLossRatio(policy, MAXIMUM_LOSS_RATIO);

    verify(claimRepository).findById(FIRST_CLAIM_ID);
    verify(claimRepository).findById(SECOND_CLAIM_ID);
    verify(claimRepository).findById(EXPIRED_CLAIM_ID);
    verify(claimRepository).findById(PREVIOUS_COVERAGE_PERIOD_CLAIM_ID);
  }

  @Test
  public void
      listingNotYetAcceptedClaimsExceedingMaximumLossRatio_shouldNotConsiderExpiredClaims() {
    setUpClaimsPersonalPropertyLosses(
        Amount.ZERO,
        Amount.ZERO,
        AMOUNT_GREATER_THAN_PERSONAL_PROPERTY_MAXIMUM_TOTAL_LOSSES,
        Amount.ZERO);

    List<Claim> exceedingClaims =
        subject.listNotYetAcceptedClaimsExceedingMaximumLossRatio(policy, MAXIMUM_LOSS_RATIO);

    assertTrue(exceedingClaims.isEmpty());
  }

  @Test
  public void
      listingNotYetAcceptedClaimsExceedingMaximumLossRatio_shouldNotConsiderPreviousCoveragePeriodClaims() {
    setUpClaimsPersonalPropertyLosses(
        Amount.ZERO,
        Amount.ZERO,
        Amount.ZERO,
        AMOUNT_GREATER_THAN_PERSONAL_PROPERTY_MAXIMUM_TOTAL_LOSSES);

    List<Claim> exceedingClaims =
        subject.listNotYetAcceptedClaimsExceedingMaximumLossRatio(policy, MAXIMUM_LOSS_RATIO);

    assertTrue(exceedingClaims.isEmpty());
  }

  @Test
  public void
      listingNotYetAcceptedClaimsExceedingMaximumLossRatio_withPolicyNotExceedingMaximumLossRatio_shouldReturnNoClaim() {
    Amount firstClaimAmount =
        createAmountBetween(Amount.ZERO, PERSONAL_PROPERTY_MAXIMUM_TOTAL_LOSSES);
    Amount secondClaimAmount =
        createAmountBetween(
            Amount.ZERO, PERSONAL_PROPERTY_MAXIMUM_TOTAL_LOSSES.subtract(firstClaimAmount));
    setUpClaimsPersonalPropertyLosses(
        firstClaimAmount,
        secondClaimAmount,
        createAmountGreaterThanZero(),
        createAmountGreaterThanZero());

    List<Claim> exceedingClaims =
        subject.listNotYetAcceptedClaimsExceedingMaximumLossRatio(policy, MAXIMUM_LOSS_RATIO);

    assertTrue(exceedingClaims.isEmpty());
  }

  @Test
  public void
      listingNotYetAcceptedClaimsExceedingMaximumLossRatio_withPolicyExceedingMaximumLossRatio_shouldReturnNotYetAcceptedClaims() {
    Amount firstClaimAmount = createAmountGreaterThan(PERSONAL_PROPERTY_MAXIMUM_TOTAL_LOSSES);
    Amount secondClaimAmount = createAmountGreaterThan(PERSONAL_PROPERTY_MAXIMUM_TOTAL_LOSSES);
    setUpClaimsPersonalPropertyLosses(
        firstClaimAmount,
        secondClaimAmount,
        createAmountGreaterThanZero(),
        createAmountGreaterThanZero());

    List<Claim> exceedingClaims =
        subject.listNotYetAcceptedClaimsExceedingMaximumLossRatio(policy, MAXIMUM_LOSS_RATIO);

    assertEquals(Arrays.asList(firstClaim, secondClaim), exceedingClaims);
  }

  private void setUpPolicy() {
    when(policy.getClaims()).thenReturn(CLAIM_IDS);
    when(policy.getCoveragePeriod()).thenReturn(POLICY_COVERAGE_PERIOD);
    when(policy.getCoverageDetails())
        .thenReturn(
            CoverageDetailsBuilder.aCoverageDetails()
                .withPersonalPropertyCoverageDetail(PERSONAL_PROPERTY_COVERAGE_AMOUNT)
                .build());
  }

  private void setUpClaim(Claim claim, ClaimStatus claimStatus, Date declarationDate) {
    when(claim.getStatus()).thenReturn(claimStatus);
    when(claim.getDeclarationDate()).thenReturn(declarationDate);
  }

  private void setUpClaimRepository() {
    when(claimRepository.findById(FIRST_CLAIM_ID)).thenReturn(Optional.of(firstClaim));
    when(claimRepository.findById(SECOND_CLAIM_ID)).thenReturn(Optional.of(secondClaim));
    when(claimRepository.findById(EXPIRED_CLAIM_ID)).thenReturn(Optional.of(expiredClaim));
    when(claimRepository.findById(PREVIOUS_COVERAGE_PERIOD_CLAIM_ID))
        .thenReturn(Optional.of(previousCoveragePeriodClaim));
  }

  private void setUpClaimsPersonalPropertyLosses(
      Amount firstClaimAmount,
      Amount secondClaimAmount,
      Amount expiredClaimAmount,
      Amount previousCoveragePeriodClaimAmount) {
    when(firstClaim.computePersonalPropertyLosses()).thenReturn(firstClaimAmount);
    when(secondClaim.computePersonalPropertyLosses()).thenReturn(secondClaimAmount);
    when(expiredClaim.computePersonalPropertyLosses()).thenReturn(expiredClaimAmount);
    when(previousCoveragePeriodClaim.computePersonalPropertyLosses())
        .thenReturn(previousCoveragePeriodClaimAmount);
  }
}
