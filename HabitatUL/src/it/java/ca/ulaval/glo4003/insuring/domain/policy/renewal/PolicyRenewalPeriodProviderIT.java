package ca.ulaval.glo4003.insuring.domain.policy.renewal;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public abstract class PolicyRenewalPeriodProviderIT {
  private PolicyRenewalPeriodProvider subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void gettingPolicyRenewalPeriod_shouldProvidePeriod() {
    assertNotNull(subject.getPolicyRenewalPeriod());
  }

  public abstract PolicyRenewalPeriodProvider createSubject();
}
