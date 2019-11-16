package ca.ulaval.glo4003.insuring.domain.policy.modification;

import java.time.Duration;

public interface PolicyModificationValidityPeriodProvider {
  Duration getPolicyModificationValidityPeriod();
}
