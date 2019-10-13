package ca.ulaval.glo4003.coverage.domain.claim;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class ClaimIdTest {
  private static final UUID UUID_VALUE = UUID.randomUUID();

  private ClaimId subject;

  @Before
  public void setUp() {
    subject = new ClaimId(UUID_VALUE.toString());
  }

  @Test
  public void claimIdRepresentation_shouldReturnStringifiedValue() {
    String representation = subject.toRepresentation();

    assertEquals(UUID_VALUE.toString(), representation);
  }
}
