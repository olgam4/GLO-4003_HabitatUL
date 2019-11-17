package ca.ulaval.glo4003.insuring.domain.policy.aggregate;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.helper.policy.PolicyBuilder;
import ca.ulaval.glo4003.helper.policy.PolicyHistoricBuilder;
import ca.ulaval.glo4003.helper.policy.PolicyViewBuilder;
import ca.ulaval.glo4003.insuring.domain.claim.Claim;
import ca.ulaval.glo4003.insuring.domain.policy.ClaimOpenedEvent;
import ca.ulaval.glo4003.insuring.domain.policy.Policy;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyInformation;
import ca.ulaval.glo4003.insuring.domain.policy.error.ClaimOutsideCoveragePeriodError;
import ca.ulaval.glo4003.insuring.domain.policy.historic.PolicyHistoric;
import ca.ulaval.glo4003.insuring.domain.policy.historic.PolicyView;
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
public class PolicyClaimTest {
  private static final Period CURRENT_COVERAGE_PERIOD = createFuturePeriod();
  private static final PolicyInformation CURRENT_POLICY_INFORMATION = createPolicyInformation();
  private static final CoverageDetails CURRENT_COVERAGE_DETAILS = createCoverageDetails();
  private static final PolicyView CURRENT_POLICY_VIEW =
      PolicyViewBuilder.aPolicyView()
          .withCoveragePeriod(CURRENT_COVERAGE_PERIOD)
          .withPolicyInformation(CURRENT_POLICY_INFORMATION)
          .withCoverageDetails(CURRENT_COVERAGE_DETAILS)
          .build();
  private static final PolicyHistoric POLICY_HISTORIC =
      PolicyHistoricBuilder.aPolicyHistoric().withPolicyView(CURRENT_POLICY_VIEW).build();

  @Mock private Claim claim;

  private Policy subject;

  @Before
  public void setUp() {
    subject = PolicyBuilder.aPolicy().withPolicyHistoric(POLICY_HISTORIC).build();
  }

  @Test
  public void openingClaim_shouldValidateClaim() {
    subject.openClaim(claim);

    verify(claim).validate(CURRENT_POLICY_INFORMATION, CURRENT_COVERAGE_DETAILS);
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
    PolicyHistoric policyHistoric =
        PolicyHistoricBuilder.aPolicyHistoric().withPolicyView(policyView).build();
    subject = PolicyBuilder.aPolicy().withPolicyHistoric(policyHistoric).build();

    subject.openClaim(claim);
  }
}
