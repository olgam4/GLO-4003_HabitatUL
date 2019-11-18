package ca.ulaval.glo4003.insuring.domain.policy.historic;

import ca.ulaval.glo4003.helper.policy.PolicyHistoricBuilder;
import ca.ulaval.glo4003.helper.policy.PolicyViewBuilder;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.helper.policy.PolicyViewGenerator.createCoveragePeriod;
import static ca.ulaval.glo4003.helper.policy.PolicyViewGenerator.createPreviousCoveragePeriod;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.*;
import static org.junit.Assert.assertEquals;

public class PolicyHistoricTest {
  private static final Period CURRENT_COVERAGE_PERIOD = createCoveragePeriod();
  private static final PolicyView CURRENT_POLICY_VIEW =
      PolicyViewBuilder.aPolicyView().withCoveragePeriod(CURRENT_COVERAGE_PERIOD).build();
  private static final Period PREVIOUS_COVERAGE_PERIOD =
      createPreviousCoveragePeriod(CURRENT_COVERAGE_PERIOD);
  private static final PolicyView PREVIOUS_POLICY_VIEW =
      PolicyViewBuilder.aPolicyView().withCoveragePeriod(PREVIOUS_COVERAGE_PERIOD).build();

  private PolicyHistoric subject;

  @Before
  public void setUp() {
    subject =
        PolicyHistoricBuilder.aPolicyHistoric()
            .withPolicyView(PREVIOUS_POLICY_VIEW)
            .withPolicyView(CURRENT_POLICY_VIEW)
            .build();
  }

  @Test
  public void
      gettingPolicyViewOnSpecificDate_withDateWithinCurrentCoveragePeriod_shouldReturnCurrentView() {
    PolicyView policyView =
        subject.getViewOn(
            createDateBetween(
                CURRENT_COVERAGE_PERIOD.getStartDate(), CURRENT_COVERAGE_PERIOD.getEndDate()));

    assertEquals(CURRENT_POLICY_VIEW, policyView);
  }

  @Test
  public void
      gettingPolicyViewOnSpecificDate_withDateAfterPolicyCreationAndBeforeCurrentCoveragePeriod_shouldReturnCorrespondingView() {
    PolicyView policyView =
        subject.getViewOn(
            createDateBetween(
                PREVIOUS_COVERAGE_PERIOD.getStartDate(), PREVIOUS_COVERAGE_PERIOD.getEndDate()));

    assertEquals(PREVIOUS_POLICY_VIEW, policyView);
  }

  @Test
  public void
      gettingPolicyViewOnSpecificDate_withDateBeforePolicyCreation_shouldReturnOriginalView() {
    PolicyView policyView =
        subject.getViewOn(createDateBefore(PREVIOUS_COVERAGE_PERIOD.getStartDate()));

    assertEquals(PREVIOUS_POLICY_VIEW, policyView);
  }

  @Test
  public void
      gettingPolicyViewOnSpecificDate_withDateAfterCurrentCoveragePeriod_shouldReturnCurrentView() {
    PolicyView policyView =
        subject.getViewOn(createDateAfter(CURRENT_COVERAGE_PERIOD.getEndDate()));

    assertEquals(CURRENT_POLICY_VIEW, policyView);
  }
}
