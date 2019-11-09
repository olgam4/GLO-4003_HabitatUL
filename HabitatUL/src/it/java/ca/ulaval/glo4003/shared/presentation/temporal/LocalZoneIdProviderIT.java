package ca.ulaval.glo4003.shared.presentation.temporal;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public abstract class LocalZoneIdProviderIT {
  private LocalZoneIdProvider subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void gettingLocalZoneId_shouldProvideLocalZoneId() {
    assertNotNull(subject.getLocalZoneId());
  }

  public abstract LocalZoneIdProvider createSubject();
}
