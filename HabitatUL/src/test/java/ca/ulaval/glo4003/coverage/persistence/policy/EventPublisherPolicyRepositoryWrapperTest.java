package ca.ulaval.glo4003.coverage.persistence.policy;

import ca.ulaval.glo4003.coverage.domain.policy.Policy;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyId;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyRepository;
import ca.ulaval.glo4003.generator.EventGenerator;
import ca.ulaval.glo4003.mediator.BoundedContextMediator;
import ca.ulaval.glo4003.mediator.event.Event;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EventPublisherPolicyRepositoryWrapperTest {
  private static final PolicyId POLICY_ID = new PolicyId();

  @Mock private Policy policy;
  @Mock private PolicyRepository policyRepository;
  @Mock private BoundedContextMediator mediator;

  private EventPublisherPolicyRepositoryWrapper subject;

  @Before
  public void setUp() {
    subject = new EventPublisherPolicyRepositoryWrapper(policyRepository, mediator);
  }

  @Test
  public void creatingPolicy_shouldDelegateToPolicyRepository() {
    subject.create(policy);

    verify(policyRepository).create(policy);
  }

  @Test
  public void creatingPolicy_shouldPublishDomainEvents() {
    int randomNumber = Faker.instance().number().randomDigitNotZero();
    List<Event> events = EventGenerator.createList(randomNumber);
    when(policy.getEvents()).thenReturn(events);

    subject.create(policy);

    verify(mediator).publish(events);
  }

  @Test
  public void gettingPolicyById_shouldDelegateToPolicyRepository() {
    subject.getById(POLICY_ID);

    verify(policyRepository).getById(POLICY_ID);
  }
}
