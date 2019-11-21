package ca.ulaval.glo4003.insuring.domain.policy.modification;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public abstract class PolicyModificationValidityPeriodProviderIT {
  private PolicyModificationValidityPeriodProvider subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void gettingPolicyModificationValidityPeriod_shouldProvidePeriod() {
    assertNotNull(subject.getPolicyModificationValidityPeriod());
  }

  public abstract PolicyModificationValidityPeriodProvider createSubject();
}
