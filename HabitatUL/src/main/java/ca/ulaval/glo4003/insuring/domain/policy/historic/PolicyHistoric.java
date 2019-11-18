package ca.ulaval.glo4003.insuring.domain.policy.historic;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyInformation;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModification;
import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.Period;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PolicyHistoric extends ValueObject {
  private final List<PolicyView> historic;

  public PolicyHistoric(List<PolicyView> historic) {
    this.historic = new ArrayList<>(historic);
  }

  public List<PolicyView> getHistoric() {
    return historic;
  }

  public Period getCurrentCoveragePeriod() {
    return getCurrentView().getCoveragePeriod();
  }

  public PolicyInformation getCurrentPolicyInformation() {
    return getCurrentView().getPolicyInformation();
  }

  public CoverageDetails getCurrentCoverageDetails() {
    return getCurrentView().getCoverageDetails();
  }

  public PremiumDetails getCurrentPremiumDetails() {
    return getCurrentView().getPremiumDetails();
  }

  public PolicyView getViewOn(Date date) {
    List<PolicyView> sortedHistoric = getSortedHistoric();
    PolicyView originalView = sortedHistoric.get(0);
    if (isBeforePolicyCreation(date, originalView)) return originalView;

    return sortedHistoric.stream()
        .filter(x -> x.getCoveragePeriod().isWithin(date))
        .findFirst()
        .orElse(getCurrentView());
  }

  private boolean isBeforePolicyCreation(Date date, PolicyView originalView) {
    return date.isBefore(originalView.getCoveragePeriod().getStartDate());
  }

  public PolicyView getCurrentView() {
    return getSortedHistoric().get(historic.size() - 1);
  }

  private List<PolicyView> getSortedHistoric() {
    return historic.stream()
        .sorted(Comparator.comparing(PolicyView::getCoveragePeriod))
        .collect(Collectors.toList());
  }

  public void updatePolicyHistory(PolicyModification policyModification) {
    PolicyView updatedPolicyView = policyModification.updatePolicyView(getCurrentView());
    modifyCurrentPolicyView(updatedPolicyView);
  }

  private void modifyCurrentPolicyView(PolicyView modifiedView) {
    PolicyView currentView = getCurrentView();
    PolicyView updatedCurrentView = createUpdatedCurrentView(currentView, modifiedView);
    historic.remove(currentView);
    historic.add(updatedCurrentView);
    historic.add(modifiedView);
  }

  private PolicyView createUpdatedCurrentView(PolicyView currentView, PolicyView modifiedView) {
    Period currentViewUpdatedCoveragePeriod =
        computeCurrentViewUpdateCoveragePeriod(currentView, modifiedView);
    return new PolicyView(
        currentViewUpdatedCoveragePeriod,
        currentView.getPolicyInformation(),
        currentView.getCoverageDetails(),
        currentView.getPremiumDetails());
  }

  private Period computeCurrentViewUpdateCoveragePeriod(
      PolicyView currentView, PolicyView modifiedView) {
    Date currentViewCoveragePeriodStartDate = currentView.getCoveragePeriod().getStartDate();
    Date modifiedViewCoveragePeriodStartDate = modifiedView.getCoveragePeriod().getStartDate();
    Date currentViewUpdatedCoveragePeriodEndDate =
        Date.latest(
            currentViewCoveragePeriodStartDate,
            modifiedViewCoveragePeriodStartDate.minus(java.time.Period.ofDays(1)));
    return new Period(currentViewCoveragePeriodStartDate, currentViewUpdatedCoveragePeriodEndDate);
  }
}
