package ca.ulaval.glo4003.insuring.domain.policy;

import ca.ulaval.glo4003.coverage.domain.coverage.detail.CoverageDetails;
import ca.ulaval.glo4003.helper.policy.PolicyBuilder;
import ca.ulaval.glo4003.helper.policy.PolicyViewBuilder;
import ca.ulaval.glo4003.insuring.domain.claim.Claim;
import ca.ulaval.glo4003.insuring.domain.policy.error.ClaimOutsideCoveragePeriodError;
import ca.ulaval.glo4003.mediator.Event;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.helper.policy.PolicyInformationGenerator.createPolicyInformation;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.createFuturePeriod;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.createPastPeriod;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PolicyTest {
  private static final Period COVERAGE_PERIOD = createFuturePeriod();
  private static final PolicyInformation POLICY_INFORMATION = createPolicyInformation();
  private static final CoverageDetails COVERAGE_DETAILS = createCoverageDetails();
  private static final PolicyView POLICY_VIEW =
      PolicyViewBuilder.aPolicyView()
          .withCoveragePeriod(COVERAGE_PERIOD)
          .withPolicyInformation(POLICY_INFORMATION)
          .withCoverageDetails(COVERAGE_DETAILS)
          .build();

  @Mock private Claim claim;

  private Policy subject;

  @Before
  public void setUp() {
    subject = PolicyBuilder.aPolicy().withPolicyView(POLICY_VIEW).build();
  }

  @Test
  public void issuingPolicy_shouldRegisterPolicyIssuedEvent() {
    subject.issue();
    List<Event> events = subject.getEvents();

    assertEquals(1, events.size());
    assertEquals(PolicyIssuedEvent.class, events.get(0).getClass());
  }

  @Test
  public void openingClaim_shouldValidateClaim() {
    subject.openClaim(claim);

    verify(claim).validate(POLICY_INFORMATION, COVERAGE_DETAILS);
  }

  @Test
  public void openingClaim_shouldKeepReferenceOnOpenedClaim() {
    subject.openClaim(claim);

    assertTrue(subject.getClaims().contains(claim.getClaimId()));
  }

  @Test
  public void openingClaim_shouldRegisterClaimOpenedEvent() {
    subject.openClaim(claim);
    List<Event> events = subject.getEvents();

    assertEquals(1, events.size());
    assertEquals(ClaimOpenedEvent.class, events.get(0).getClass());
  }

  @Test(expected = ClaimOutsideCoveragePeriodError.class)
  public void openingClaim_withExpiredPolicy_shouldThrow() {
    Period pastPeriod = createPastPeriod();
    PolicyView policyView = PolicyViewBuilder.aPolicyView().withCoveragePeriod(pastPeriod).build();
    subject = PolicyBuilder.aPolicy().withPolicyView(policyView).build();

    subject.openClaim(claim);
  }
}
