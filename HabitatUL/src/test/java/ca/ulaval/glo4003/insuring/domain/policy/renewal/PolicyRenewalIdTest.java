package ca.ulaval.glo4003.insuring.domain.policy.renewal;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class PolicyRenewalIdTest {
  private static final UUID UUID_VALUE = UUID.randomUUID();

  private PolicyRenewalId subject;

  @Before
  public void setUp() {
    subject = new PolicyRenewalId(UUID_VALUE.toString());
  }

  @Test
  public void policyRenewalIdRepresentation_shouldReturnStringifiedValue() {
    String representation = subject.toRepresentation();

    assertEquals(UUID_VALUE.toString(), representation);
  }
}
