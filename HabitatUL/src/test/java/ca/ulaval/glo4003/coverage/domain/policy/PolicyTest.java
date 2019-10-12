package ca.ulaval.glo4003.coverage.domain.policy;

import ca.ulaval.glo4003.generator.policy.PolicyGenerator;
import ca.ulaval.glo4003.mediator.Event;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class PolicyTest {
  private Policy subject;

  @Before
  public void setUp() {
    subject = PolicyGenerator.createPolicy();
  }

  @Test
  public void issuingPolicy_shouldRegisterPolicyIssuedEvent() {
    subject.issue();

    List<Event> events = subject.getEvents();

    assertEquals(1, events.size());
    assertEquals(PolicyIssuedEvent.class, events.get(0).getClass());
  }
}
