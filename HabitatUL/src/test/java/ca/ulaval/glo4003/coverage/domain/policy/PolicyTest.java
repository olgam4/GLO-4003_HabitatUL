package ca.ulaval.glo4003.coverage.domain.policy;

import ca.ulaval.glo4003.coverage.domain.claim.Claim;
import ca.ulaval.glo4003.coverage.domain.claim.LossCategory;
import ca.ulaval.glo4003.coverage.domain.claim.LossDeclarations;
import ca.ulaval.glo4003.coverage.domain.policy.error.ClaimOutsideCoveragePeriodError;
import ca.ulaval.glo4003.coverage.domain.policy.error.LossDeclarationsExceedCoverageAmountError;
import ca.ulaval.glo4003.coverage.domain.policy.error.NotDeclaredBicycleError;
import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.helper.TemporalGenerator;
import ca.ulaval.glo4003.helper.claim.ClaimBuilder;
import ca.ulaval.glo4003.helper.claim.ClaimGenerator;
import ca.ulaval.glo4003.helper.claim.LossDeclarationsBuilder;
import ca.ulaval.glo4003.helper.policy.PolicyBuilder;
import ca.ulaval.glo4003.mediator.Event;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PolicyTest {
  private static final Amount COVERAGE_AMOUNT = MoneyGenerator.createAmount();

  private Policy subject;

  @Before
  public void setUp() {
    subject =
        PolicyBuilder.aPolicy()
            .withCoveragePeriod(TemporalGenerator.createActivePeriod())
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
  public void openingClaim_shouldCheckThatClaimIsOpenedWithInCoveragePeriod() {
    Period pastPeriod = TemporalGenerator.createPastPeriod();
    subject = PolicyBuilder.aPolicy().withCoveragePeriod(pastPeriod).build();
    Claim claim = ClaimGenerator.createClaim();

    subject.openClaim(claim);
  }

  @Test(expected = NotDeclaredBicycleError.class)
  public void openingClaim_shouldCheckForNotDeclaredBicycle() {
    LossDeclarations lossDeclarations =
        LossDeclarationsBuilder.aLossDeclaration()
            .withLoss(LossCategory.BICYCLE, MoneyGenerator.createAmount())
            .build();
    Claim claim = ClaimBuilder.aClaim().withLossDeclarations(lossDeclarations).build();

    subject.openClaim(claim);
  }

  @Test(expected = LossDeclarationsExceedCoverageAmountError.class)
  public void openingClaim_shouldCheckThatLossDeclarationsDoNotExceedCoverageAmount() {
    LossDeclarations lossDeclarations =
        LossDeclarationsBuilder.aLossDeclaration()
            .withLoss(LossCategory.CLOTHES, COVERAGE_AMOUNT.multiply(2))
            .build();
    Claim claim = ClaimBuilder.aClaim().withLossDeclarations(lossDeclarations).build();

    subject.openClaim(claim);
  }

  @Test
  public void openingClaim_withValidClaim_shouldKeepReferenceOnOpenedClaim() {
    Claim claim = ClaimGenerator.createClaim();

    subject.openClaim(claim);

    assertTrue(subject.getClaims().contains(claim.getClaimId()));
  }

  @Test
  public void openingClaim_withValidClaim_shouldRegisterClaimOpenedEvent() {
    Claim claim = ClaimGenerator.createClaim();

    subject.openClaim(claim);
    List<Event> events = subject.getEvents();

    assertEquals(1, events.size());
    assertEquals(ClaimOpenedEvent.class, events.get(0).getClass());
  }
}
