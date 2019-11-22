package ca.ulaval.glo4003.insuring.application.claim;

import ca.ulaval.glo4003.helper.claim.ClaimGenerator;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClaimAppServiceTest {
  private static final ClaimId CLAIM_ID = ClaimGenerator.createClaimId();

  @Mock private ClaimRepository claimRepository;
  @Mock private Claim claim;

  private ClaimAppService subject;
  private ClaimAssembler claimAssembler;

  @Before
  public void setUp() throws ClaimNotFoundException {
    claimAssembler = new ClaimAssembler();
    when(claimRepository.getById(any(ClaimId.class))).thenReturn(claim);
    subject = new ClaimAppServiceImpl(claimRepository, claimAssembler);
  }

  @Test
  public void gettingClaim_shouldGetClaimById() throws ClaimNotFoundException {
    subject.getClaim(CLAIM_ID);

    verify(claimRepository).getById(CLAIM_ID);
  }

  @Test(expected = ClaimNotFoundError.class)
  public void gettingClaim_withNotExistingClaimId_shouldThrow() throws ClaimNotFoundException {
    when(claimRepository.getById(any(ClaimId.class))).thenThrow(ClaimNotFoundException.class);

    subject.getClaim(CLAIM_ID);
  }
}
