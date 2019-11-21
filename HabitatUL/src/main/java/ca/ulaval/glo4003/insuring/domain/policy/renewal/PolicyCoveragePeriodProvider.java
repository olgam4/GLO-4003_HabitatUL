package ca.ulaval.glo4003.insuring.domain.policy.renewal;

import ca.ulaval.glo4003.shared.domain.temporal.Period;

public interface PolicyCoveragePeriodProvider {
  Period getPolicyCoveragePeriod();
}
