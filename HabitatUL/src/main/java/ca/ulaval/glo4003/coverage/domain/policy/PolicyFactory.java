package ca.ulaval.glo4003.coverage.domain.policy;

import ca.ulaval.glo4003.shared.domain.money.Amount;
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
      String quoteKey, Period coveragePeriod, Date purchaseDate, Amount coverageAmount) {
    PolicyId policyId = new PolicyId();
    Period adjustedCoveragePeriod = adjustCoveragePeriod(coveragePeriod, purchaseDate);
    return new Policy(policyId, quoteKey, adjustedCoveragePeriod, coverageAmount, clockProvider);
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
