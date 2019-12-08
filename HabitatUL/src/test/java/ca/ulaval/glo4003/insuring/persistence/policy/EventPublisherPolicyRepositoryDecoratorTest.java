package ca.ulaval.glo4003.insuring.persistence.policy;

import ca.ulaval.glo4003.helper.policy.PolicyGenerator;
import ca.ulaval.glo4003.helper.shared.EventGenerator;
import ca.ulaval.glo4003.insuring.domain.policy.Policy;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyRepository;
import ca.ulaval.glo4003.insuring.domain.policy.exception.PolicyAlreadyCreatedException;
import ca.ulaval.glo4003.insuring.domain.policy.exception.PolicyNotFoundException;
import ca.ulaval.glo4003.mediator.Event;
import ca.ulaval.glo4003.mediator.Mediator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EventPublisherPolicyRepositoryDecoratorTest {
  private static final PolicyId POLICY_ID = PolicyGenerator.createPolicyId();
  private static final List<Event> EVENTS = EventGenerator.createEvents();

  @Mock private Policy policy;
  @Mock private PolicyRepository policyRepository;
  @Mock private Mediator mediator;

  private EventPublisherPolicyRepositoryDecorator subject;

  @Before
  public void setUp() {
    subject = new EventPublisherPolicyRepositoryDecorator(policyRepository, mediator);
  }

  @Test
  public void gettingAllPolicies_shouldDelegateToPolicyRepository() {
    subject.getAll();

    verify(policyRepository).getAll();
  }

  @Test
  public void gettingPolicyById_shouldDelegateToPolicyRepository() throws PolicyNotFoundException {
    subject.getById(POLICY_ID);

    verify(policyRepository).getById(POLICY_ID);
  }

  @Test
  public void creatingPolicy_shouldDelegateToPolicyRepository()
      throws PolicyAlreadyCreatedException {
    subject.create(policy);

    verify(policyRepository).create(policy);
  }

  @Test
  public void creatingPolicy_shouldPublishDomainEvents() throws PolicyAlreadyCreatedException {
    when(policy.getEvents()).thenReturn(EVENTS);

    subject.create(policy);

    verify(mediator).publish(EVENTS);
  }

  @Test
  public void updatingPolicy_shouldDelegateToPolicyRepository() throws PolicyNotFoundException {
    Policy policy = PolicyGenerator.createPolicy();

    subject.update(policy);

    verify(policyRepository).update(policy);
  }

  @Test
  public void updatingPolicy_shouldPublishDomainEvents() throws PolicyNotFoundException {
    when(policy.getEvents()).thenReturn(EVENTS);

    subject.update(policy);

    verify(mediator).publish(EVENTS);
  }
}
