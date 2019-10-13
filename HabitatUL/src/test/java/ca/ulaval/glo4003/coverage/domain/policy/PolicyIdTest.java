package ca.ulaval.glo4003.coverage.domain.policy;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class PolicyIdTest {
  private static final UUID UUID_VALUE = UUID.randomUUID();

  private PolicyId subject;

  @Before
  public void setUp() {
    subject = new PolicyId(UUID_VALUE.toString());
  }

  @Test
  public void policyIdRepresentation_shouldReturnStringifiedValue() {
    String representation = subject.toRepresentation();

    assertEquals(UUID_VALUE.toString(), representation);
  }
}
