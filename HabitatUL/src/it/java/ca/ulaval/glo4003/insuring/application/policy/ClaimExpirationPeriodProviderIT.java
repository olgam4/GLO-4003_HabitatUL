package ca.ulaval.glo4003.insuring.application.policy;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public abstract class ClaimExpirationPeriodProviderIT {
  private ClaimExpirationPeriodProvider subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void gettingClaimExpirationPeriod_shouldProvidePeriod() {
    assertNotNull(subject.getClaimExpirationPeriod());
  }

  public abstract ClaimExpirationPeriodProvider createSubject();
}
