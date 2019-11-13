package ca.ulaval.glo4003.insuring.domain.policy;

import ca.ulaval.glo4003.coverage.domain.coverage.detail.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.detail.PremiumDetails;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.Period;

import java.time.LocalDate;

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
    return new Policy(
        policyId,
        quoteKey,
        adjustedCoveragePeriod,
        policyInformation,
        coverageDetails,
        premiumDetails,
        clockProvider);
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
