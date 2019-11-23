package ca.ulaval.glo4003.insuring.application.claim.expiration;

import ca.ulaval.glo4003.insuring.application.claim.error.ClaimNotFoundError;
import ca.ulaval.glo4003.insuring.domain.claim.Claim;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimRepository;
import ca.ulaval.glo4003.insuring.domain.claim.exception.ClaimNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.helper.claim.ClaimGenerator.createClaimId;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClaimExpirationTaskTest {
  public static final ClaimId CLAIM_ID = createClaimId();

  @Mock private ClaimRepository claimRepository;
  @Mock private Claim claim;

  private ClaimExpirationTask subject;

  @Before
  public void setUp() throws ClaimNotFoundException {
    when(claimRepository.getById(any(ClaimId.class))).thenReturn(claim);
    subject = new ClaimExpirationTask(claimRepository, CLAIM_ID);
  }

  @Test
  public void runningTask_shouldGetClaimById() throws ClaimNotFoundException {
    subject.run();

    verify(claimRepository).getById(CLAIM_ID);
  }

  @Test
  public void runningTask_shouldExpireClaim() {
    subject.run();

    verify(claim).expire();
  }

  @Test
  public void runningTask_shouldUpdateClaim() throws ClaimNotFoundException {
    subject.run();

    verify(claimRepository).update(claim);
  }

  @Test(expected = ClaimNotFoundError.class)
  public void runningTask_withNotExistingClaim_shouldThrow() throws ClaimNotFoundException {
    when(claimRepository.getById(CLAIM_ID)).thenThrow(ClaimNotFoundException.class);

    subject.run();
  }
}
