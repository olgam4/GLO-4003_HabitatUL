package ca.ulaval.glo4003.administration.domain.user.token;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public abstract class TokenValidityPeriodProviderIT {
  private TokenValidityPeriodProvider subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void gettingTokenValidityPeriod_shouldProvideValidityPeriod() {
    assertNotNull(subject.getTokenValidityPeriod());
  }

  public abstract TokenValidityPeriodProvider createSubject();
}
