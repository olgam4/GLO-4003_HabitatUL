package ca.ulaval.glo4003.insuring.domain.policy;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.insuring.domain.policy.historic.PolicyHistoric;
import ca.ulaval.glo4003.insuring.domain.policy.historic.PolicyView;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationsCoordinator;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.Period;

import java.time.LocalDate;
import java.util.Arrays;

import static ca.ulaval.glo4003.insuring.domain.policy.PolicyStatus.ACTIVE;

public class PolicyFactory {
  private ClockProvider clockProvider;

  public PolicyFactory(ClockProvider clockProvider) {
    this.clockProvider = clockProvider;
  }

  public Policy create(
      String quoteKey,
      Period coveragePeriod,
      Date purchaseDate,
      PolicyInformation policyInformation,
      CoverageDetails coverageDetails,
      PremiumDetails premiumDetails) {
    PolicyId policyId = new PolicyId();
    Period adjustedCoveragePeriod = adjustCoveragePeriod(coveragePeriod, purchaseDate);
    PolicyView currentPolicyView =
        new PolicyView(adjustedCoveragePeriod, policyInformation, coverageDetails, premiumDetails);
    PolicyHistoric policyHistoric = new PolicyHistoric(Arrays.asList(currentPolicyView));
    PolicyModificationsCoordinator policyModificationsCoordinator =
        new PolicyModificationsCoordinator();
    return new Policy(
        policyId, quoteKey, ACTIVE, policyHistoric, policyModificationsCoordinator, clockProvider);
  }

  private Period adjustCoveragePeriod(Period coveragePeriod, Date purchaseDate) {
    Date adjustedCoveragePeriodStartDate = Date.latest(coveragePeriod.getStartDate(), purchaseDate);
    LocalDate coveragePeriodStartDate = coveragePeriod.getStartDate().getValue();
    LocalDate coveragePeriodEndDate = coveragePeriod.getEndDate().getValue();
    java.time.Period coveragePeriodLength =
        java.time.Period.between(coveragePeriodStartDate, coveragePeriodEndDate);
    Date adjustedCoveragePeriodEndDate = adjustedCoveragePeriodStartDate.plus(coveragePeriodLength);
    return new Period(adjustedCoveragePeriodStartDate, adjustedCoveragePeriodEndDate);
  }
}
