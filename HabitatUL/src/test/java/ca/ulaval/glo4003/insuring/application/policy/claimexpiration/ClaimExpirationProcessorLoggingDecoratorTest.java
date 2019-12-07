package ca.ulaval.glo4003.insuring.application.policy.claimexpiration;

import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.shared.application.logging.Logger;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.helper.claim.ClaimGenerator.createClaimId;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.createDateTime;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ClaimExpirationProcessorLoggingDecoratorTest {
  private static final ClaimId TASK_KEY = createClaimId();
  private static final DateTime CLAIM_EXPIRATION_DATE = createDateTime();

  @Mock private ClaimExpirationProcessor claimExppirationProcessor;
  @Mock private Logger logger;

  private ClaimExpirationProcessorLoggingDecorator subject;

  @Before
  public void setUp() {
    subject = new ClaimExpirationProcessorLoggingDecorator(claimExppirationProcessor, logger);
  }

  @Test
  public void schedulingTask_shouldLogParamsAsInfo() {
    subject.scheduleExpiration(TASK_KEY, CLAIM_EXPIRATION_DATE);

    verify(logger).info(contains(TASK_KEY.toRepresentation()));
  }

  @Test
  public void schedulingTask_shouldDelegateToClaimExpirationProcessor() {
    subject.scheduleExpiration(TASK_KEY, CLAIM_EXPIRATION_DATE);

    verify(claimExppirationProcessor).scheduleExpiration(TASK_KEY, CLAIM_EXPIRATION_DATE);
  }

  @Test
  public void cancelingTask_shouldLogParamsAsInfo() {
    subject.cancelExpiration(TASK_KEY);

    verify(logger).info(contains(TASK_KEY.toRepresentation()));
  }

  @Test
  public void cancelingTask_shouldDelegateToClaimExpirationProcessor() {
    subject.cancelExpiration(TASK_KEY);

    verify(claimExppirationProcessor).cancelExpiration(TASK_KEY);
  }
}
