package ca.ulaval.glo4003.coverage.communication.policy;

import ca.ulaval.glo4003.coverage.application.policy.PolicyAppService;
import ca.ulaval.glo4003.coverage.application.policy.event.PolicyCreationRequestedEvent;
import ca.ulaval.glo4003.helper.shared.MoneyGenerator;
import ca.ulaval.glo4003.helper.shared.TemporalGenerator;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PolicyBoundedContextEventHandlerTest {
  private static final String QUOTE_KEY = Faker.instance().internet().uuid();
  private static final Period COVERAGE_PERIOD = TemporalGenerator.createPeriod();
  private static final Date PURCHASE_DATE = TemporalGenerator.createDate();
  private static final Amount COVERAGE_AMOUNT = MoneyGenerator.createAmount();

  @Mock private PolicyAppService policyAppService;

  private PolicyBoundedContextEventHandler subject;

  @Before
  public void setUp() {
    subject = new PolicyBoundedContextEventHandler(policyAppService);
  }

  @Test
  public void handlingPolicyCreationRequestedEvent_shouldDelegateToPolicyAppService() {
    PolicyCreationRequestedEvent event =
        new PolicyCreationRequestedEvent(
            QUOTE_KEY, COVERAGE_PERIOD, PURCHASE_DATE, COVERAGE_AMOUNT);

    subject.handlePolicyCreationRequestedEvent(event);

    verify(policyAppService)
        .issuePolicy(QUOTE_KEY, COVERAGE_PERIOD, PURCHASE_DATE, COVERAGE_AMOUNT);
  }
}
