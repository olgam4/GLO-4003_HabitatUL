package ca.ulaval.glo4003.insuring.application.claim;

import ca.ulaval.glo4003.helper.claim.ClaimGenerator;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.logging.Logger;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ClaimAppServiceLoggingDecoratorTest {
  private static final ClaimId CLAIM_ID = ClaimGenerator.createClaimId();

  @Mock private ClaimAppService claimAppService;
  @Mock private Logger logger;

  private ClaimAppService subject;

  @Before
  public void setUp() {
    subject = new ClaimAppServiceLoggingDecorator(claimAppService, logger);
  }

  @Test
  public void gettingClaim_shouldLogParamsAndReturnAsInfo() {
    subject.getClaim(CLAIM_ID);

    verify(logger, times(2)).info(anyString());
  }

  @Test
  public void gettingClaim_shouldDelegateToPolicyAppService() {
    subject.getClaim(CLAIM_ID);

    verify(claimAppService).getClaim(CLAIM_ID);
  }
}
