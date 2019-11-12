package ca.ulaval.glo4003.gateway.presentation.claim;

import ca.ulaval.glo4003.helper.claim.ClaimGenerator;
import ca.ulaval.glo4003.insuring.application.claim.ClaimAppService;
import ca.ulaval.glo4003.insuring.application.claim.dto.ClaimDto;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClaimResourceTest {
  private static final ClaimId CLAIM_ID = ClaimGenerator.createClaimId();

  @Mock private ClaimAppService claimAppService;

  private ClaimResource subject;
  private ClaimViewAssembler claimViewAssembler;
  private ClaimDto claimDto;

  @Before
  public void setUp() {
    claimViewAssembler = new ClaimViewAssembler();
    claimDto = ClaimGenerator.createClaimDto();
    when(claimAppService.getClaim(any(ClaimId.class))).thenReturn(claimDto);
    subject = new ClaimResource(claimAppService, claimViewAssembler);
  }

  @Test
  public void gettingClaim_shouldDelegateToClaimAppService() {
    subject.getClaim(CLAIM_ID);

    verify(claimAppService).getClaim(CLAIM_ID);
  }
}
