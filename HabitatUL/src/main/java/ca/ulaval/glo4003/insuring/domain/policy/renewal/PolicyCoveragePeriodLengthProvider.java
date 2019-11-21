package ca.ulaval.glo4003.insuring.domain.policy.renewal;

import java.time.Period;

public interface PolicyCoveragePeriodLengthProvider {
  Period getPolicyCoveragePeriod();
}
