package ca.ulaval.glo4003.insuring.domain.policy.historic;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyInformation;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModification;
import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.Period;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PolicyHistoric extends ValueObject {
  private final List<PolicyView> historic;

  public PolicyHistoric(List<PolicyView> historic) {
    this.historic = historic;
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
    Date currentViewUpdatedCoveragePeriodEndDate =
        modifiedView.getCoveragePeriod().getStartDate().minus(java.time.Period.ofDays(1));
    PolicyView currentView = getCurrentView();
    Period currentViewUpdatedCoveragePeriod =
        new Period(
            currentView.getCoveragePeriod().getStartDate(),
            currentViewUpdatedCoveragePeriodEndDate);
    PolicyView updatedCurrentView =
        new PolicyView(
            currentViewUpdatedCoveragePeriod,
            currentView.getPolicyInformation(),
            currentView.getCoverageDetails(),
            currentView.getPremiumDetails());
    historic.remove(currentView);
    historic.add(updatedCurrentView);
    historic.add(modifiedView);
  }
}
