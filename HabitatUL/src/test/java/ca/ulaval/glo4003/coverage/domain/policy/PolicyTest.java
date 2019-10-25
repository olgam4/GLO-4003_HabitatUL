package ca.ulaval.glo4003.coverage.domain.policy;

import ca.ulaval.glo4003.coverage.domain.claim.Claim;
import ca.ulaval.glo4003.coverage.domain.claim.LossCategory;
import ca.ulaval.glo4003.coverage.domain.claim.LossDeclarations;
import ca.ulaval.glo4003.coverage.domain.policy.error.NotDeclaredBicycleError;
import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.helper.claim.ClaimBuilder;
import ca.ulaval.glo4003.helper.claim.ClaimGenerator;
import ca.ulaval.glo4003.helper.claim.LossDeclarationsBuilder;
import ca.ulaval.glo4003.helper.policy.PolicyGenerator;
import ca.ulaval.glo4003.mediator.Event;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PolicyTest {
  private Policy subject;

  @Before
  public void setUp() {
    subject = PolicyGenerator.createPolicy();
  }

  @Test
  public void issuingPolicy_shouldRegisterPolicyIssuedEvent() {
    subject.issue();

    List<Event> events = subject.getEvents();

    assertEquals(1, events.size());
    assertEquals(PolicyIssuedEvent.class, events.get(0).getClass());
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

  @Test
  public void openingClaim_withValidClaim_shouldKeepReferenceOnOpenedClaim() {
    Claim claim = ClaimGenerator.createClaim();

    subject.openClaim(claim);

    assertTrue(subject.getClaims().contains(claim.getClaimId()));
  }
}
