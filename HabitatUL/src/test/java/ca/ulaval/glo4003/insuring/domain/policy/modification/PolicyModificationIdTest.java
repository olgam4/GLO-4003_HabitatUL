package ca.ulaval.glo4003.insuring.domain.policy.modification;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class PolicyModificationIdTest {
  private static final UUID UUID_VALUE = UUID.randomUUID();

  private PolicyModificationId subject;

  @Before
  public void setUp() {
    subject = new PolicyModificationId(UUID_VALUE.toString());
  }

  @Test
  public void policyModificationIdRepresentation_shouldReturnStringifiedValue() {
    String representation = subject.toRepresentation();

    assertEquals(UUID_VALUE.toString(), representation);
  }
}
