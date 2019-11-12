package ca.ulaval.glo4003.insuring.domain.policy;

import ca.ulaval.glo4003.helper.claim.ClaimBuilder;
import ca.ulaval.glo4003.helper.claim.ClaimGenerator;
import ca.ulaval.glo4003.helper.policy.PolicyBuilder;
import ca.ulaval.glo4003.helper.shared.TemporalGenerator;
import ca.ulaval.glo4003.insuring.domain.claim.Claim;
import ca.ulaval.glo4003.insuring.domain.policy.error.ClaimOutsideCoveragePeriodError;
import ca.ulaval.glo4003.insuring.domain.policy.error.LossDeclarationsExceedCoverageAmountError;
import ca.ulaval.glo4003.insuring.domain.policy.error.NotDeclaredBicycleError;
import ca.ulaval.glo4003.mediator.Event;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static ca.ulaval.glo4003.helper.shared.MoneyGenerator.createAmountGreaterThanZero;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PolicyTest {
  private static final Amount COVERAGE_AMOUNT = createAmountGreaterThanZero();

  private Policy subject;

  @Before
  public void setUp() {
    subject =
        PolicyBuilder.aPolicy()
            .withCoveragePeriod(TemporalGenerator.createFuturePeriod())
            .withCoverageAmount(COVERAGE_AMOUNT)
            .build();
  }

  @Test
  public void issuingPolicy_shouldRegisterPolicyIssuedEvent() {
    subject.issue();

    List<Event> events = subject.getEvents();

    assertEquals(1, events.size());
    assertEquals(PolicyIssuedEvent.class, events.get(0).getClass());
  }

  @Test(expected = ClaimOutsideCoveragePeriodError.class)
  public void openingClaim_shouldCheckThatClaimIsOpenedWithinCoveragePeriod() {
    Period pastPeriod = TemporalGenerator.createPastPeriod();
    subject = PolicyBuilder.aPolicy().withCoveragePeriod(pastPeriod).build();
    Claim claim = ClaimGenerator.createClaim();

    subject.openClaim(claim);
  }

  @Test(expected = NotDeclaredBicycleError.class)
  public void openingClaim_shouldCheckForNotDeclaredBicycle() {
    Claim claim =
        ClaimBuilder.aClaim()
            .withBicycleLossDeclaration()
            .withMaximumTotalAmount(COVERAGE_AMOUNT)
            .build();

    subject.openClaim(claim);
  }

  @Test(expected = LossDeclarationsExceedCoverageAmountError.class)
  public void openingClaim_shouldCheckThatLossDeclarationsDoNotExceedCoverageAmount() {
    Claim claim =
        ClaimBuilder.aClaim()
            .withoutBicycleLossDeclaration()
            .withMinimumTotalAmount(COVERAGE_AMOUNT)
            .build();

    subject.openClaim(claim);
  }

  @Test
  public void openingClaim_withValidClaim_shouldKeepReferenceOnOpenedClaim() {
    Claim claim =
        ClaimBuilder.aClaim()
            .withoutBicycleLossDeclaration()
            .withMaximumTotalAmount(COVERAGE_AMOUNT)
            .build();

    subject.openClaim(claim);

    assertTrue(subject.getClaims().contains(claim.getClaimId()));
  }

  @Test
  public void openingClaim_withValidClaim_shouldRegisterClaimOpenedEvent() {
    Claim claim =
        ClaimBuilder.aClaim()
            .withoutBicycleLossDeclaration()
            .withMaximumTotalAmount(COVERAGE_AMOUNT)
            .build();

    subject.openClaim(claim);
    List<Event> events = subject.getEvents();

    assertEquals(1, events.size());
    assertEquals(ClaimOpenedEvent.class, events.get(0).getClass());
  }
}
