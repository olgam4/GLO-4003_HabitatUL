package ca.ulaval.glo4003.insuring.domain.policy.renewal;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public abstract class PolicyCoveragePeriodProviderIT {
  private PolicyCoveragePeriodProvider subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void gettingPolicyCoveragePeriod_shouldProvidePeriod() {
    assertNotNull(subject.getPolicyCoveragePeriod());
  }

  public abstract PolicyCoveragePeriodProvider createSubject();
}
