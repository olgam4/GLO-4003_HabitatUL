package ca.ulaval.glo4003.insuring.domain.policy.historic;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyInformation;
import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.temporal.Period;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PolicyHistoric extends ValueObject {
  private final List<PolicyView> historic;

  public PolicyHistoric(List<PolicyView> historic) {
    this.historic = historic;
  }

  public Period getCurrentCoveragePeriod() {
    return getCurrentPolicyView().getCoveragePeriod();
  }

  public PolicyInformation getCurrentPolicyInformation() {
    return getCurrentPolicyView().getPolicyInformation();
  }

  public CoverageDetails getCurrentCoverageDetails() {
    return getCurrentPolicyView().getCoverageDetails();
  }

  public PremiumDetails getCurrentPremiumDetails() {
    return getCurrentPolicyView().getPremiumDetails();
  }

  private PolicyView getCurrentPolicyView() {
    return getSortedHistoric().get(historic.size() - 1);
  }

  private List<PolicyView> getSortedHistoric() {
    return historic.stream()
        .sorted(Comparator.comparing(PolicyView::getCoveragePeriod))
        .collect(Collectors.toList());
  }
}
