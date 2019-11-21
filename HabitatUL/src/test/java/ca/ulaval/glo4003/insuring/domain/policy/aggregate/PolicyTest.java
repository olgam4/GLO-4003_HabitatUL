package ca.ulaval.glo4003.insuring.domain.policy.aggregate;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.helper.policy.PolicyBuilder;
import ca.ulaval.glo4003.helper.policy.PolicyHistoricBuilder;
import ca.ulaval.glo4003.helper.policy.PolicyViewBuilder;
import ca.ulaval.glo4003.insuring.domain.policy.Policy;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyInformation;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyIssuedEvent;
import ca.ulaval.glo4003.insuring.domain.policy.historic.PolicyHistoric;
import ca.ulaval.glo4003.insuring.domain.policy.historic.PolicyView;
import ca.ulaval.glo4003.mediator.Event;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsGenerator.createPremiumDetails;
import static ca.ulaval.glo4003.helper.policy.PolicyInformationGenerator.createPolicyInformation;
import static ca.ulaval.glo4003.helper.policy.PolicyViewGenerator.createPreviousCoveragePeriod;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.createCurrentPeriod;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.createPastPeriod;
import static ca.ulaval.glo4003.insuring.domain.policy.PolicyStatus.*;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PolicyTest {
  private static final Period CURRENT_COVERAGE_PERIOD = createCurrentPeriod();
  private static final PolicyInformation CURRENT_POLICY_INFORMATION = createPolicyInformation();
  private static final CoverageDetails CURRENT_COVERAGE_DETAILS = createCoverageDetails();
  private static final PremiumDetails CURRENT_PREMIUM_DETAILS = createPremiumDetails();
  private static final PolicyView CURRENT_POLICY_VIEW =
      PolicyViewBuilder.aPolicyView()
          .withCoveragePeriod(CURRENT_COVERAGE_PERIOD)
          .withPolicyInformation(CURRENT_POLICY_INFORMATION)
          .withCoverageDetails(CURRENT_COVERAGE_DETAILS)
          .withPremiumDetails(CURRENT_PREMIUM_DETAILS)
          .build();
  private static final Period PREVIOUS_COVERAGE_PERIOD =
      createPreviousCoveragePeriod(CURRENT_COVERAGE_PERIOD);
  private static final PolicyView PREVIOUS_POLICY_VIEW =
      PolicyViewBuilder.aPolicyView().withCoveragePeriod(PREVIOUS_COVERAGE_PERIOD).build();
  private static final PolicyHistoric POLICY_HISTORIC =
      PolicyHistoricBuilder.aPolicyHistoric()
          .withPolicyView(PREVIOUS_POLICY_VIEW)
          .withPolicyView(CURRENT_POLICY_VIEW)
          .build();

  private Policy subject;

  @Before
  public void setUp() {
    subject = PolicyBuilder.aPolicy().withPolicyHistoric(POLICY_HISTORIC).build();
  }

  @Test
  public void gettingCoveragePeriod_shouldReturnCurrentCoveragePeriod() {
    Period coveragePeriod = subject.getCoveragePeriod();

    assertEquals(CURRENT_COVERAGE_PERIOD, coveragePeriod);
  }

  @Test
  public void gettingPolicyInformation_shouldReturnCurrentPolicyInformation() {
    PolicyInformation policyInformation = subject.getPolicyInformation();

    assertEquals(CURRENT_POLICY_INFORMATION, policyInformation);
  }

  @Test
  public void gettingCoverageDetails_shouldReturnCurrentCoverageDetails() {
    CoverageDetails coverageDetails = subject.getCoverageDetails();

    assertEquals(CURRENT_COVERAGE_DETAILS, coverageDetails);
  }

  @Test
  public void gettingPremiumDetails_shouldReturnCurrentPremiumDetails() {
    PremiumDetails premiumDetails = subject.getPremiumDetails();

    assertEquals(CURRENT_PREMIUM_DETAILS, premiumDetails);
  }

  @Test
  public void issuingPolicy_shouldRegisterPolicyIssuedEvent() {
    subject.issue();
    List<Event> events = subject.getEvents();

    assertEquals(1, events.size());
    assertEquals(PolicyIssuedEvent.class, events.get(0).getClass());
  }

  @Test
  public void updatingStatus_withOutdatedPolicyAndUpdatableStatus_shouldSetPolicyInactive() {
    PolicyHistoric policyHistoric = createOutdatedPolicyHistoric();
    subject = PolicyBuilder.aPolicy().withStatus(ACTIVE).withPolicyHistoric(policyHistoric).build();

    subject.updateStatus();

    assertEquals(INACTIVE, subject.getStatus());
  }

  @Test
  public void updatingStatus_withOutdatedPolicyAndNotUpdatableStatus_shouldNotUpdateStatus() {
    PolicyHistoric policyHistoric = createOutdatedPolicyHistoric();
    subject =
        PolicyBuilder.aPolicy().withStatus(RENEWING).withPolicyHistoric(policyHistoric).build();

    subject.updateStatus();

    assertEquals(RENEWING, subject.getStatus());
  }

  private PolicyHistoric createOutdatedPolicyHistoric() {
    PolicyView policyView =
        PolicyViewBuilder.aPolicyView().withCoveragePeriod(createPastPeriod()).build();
    return PolicyHistoricBuilder.aPolicyHistoric().withPolicyView(policyView).build();
  }
}
