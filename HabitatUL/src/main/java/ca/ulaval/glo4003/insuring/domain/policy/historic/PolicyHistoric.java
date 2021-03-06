package ca.ulaval.glo4003.insuring.domain.policy.historic;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyInformation;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModification;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewal;
import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.Period;

import java.util.*;

public class PolicyHistoric extends ValueObject {
  private final Set<PolicyView> historic;

  public PolicyHistoric(Set<PolicyView> historic) {
    this.historic = new HashSet<>(historic);
  }

  public Set<PolicyView> getHistoric() {
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
    List<PolicyView> reversedHistoric = getReversedHistoric();
    PolicyView originalView = reversedHistoric.get(historic.size() - 1);
    if (isBeforePolicyCreation(date, originalView)) return originalView;

    return reversedHistoric.stream()
        .filter(x -> x.getCoveragePeriod().isWithin(date))
        .findFirst()
        .orElse(getCurrentView());
  }

  private boolean isBeforePolicyCreation(Date date, PolicyView originalView) {
    return date.isBefore(originalView.getCoveragePeriod().getStartDate());
  }

  public PolicyView getCurrentView() {
    return getReversedHistoric().get(0);
  }

  private List<PolicyView> getReversedHistoric() {
    ArrayList<PolicyView> reversedHistoric = new ArrayList<>(historic);
    reversedHistoric.sort(Comparator.reverseOrder());
    return reversedHistoric;
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
    return new Period(currentViewCoveragePeriodStartDate, modifiedViewCoveragePeriodStartDate);
  }

  public void updatePolicyHistory(PolicyRenewal policyRenewal) {
    PolicyView updatedPolicyView = policyRenewal.updatePolicyView(getCurrentView());
    historic.add(updatedPolicyView);
  }
}
