package ca.ulaval.glo4003.insuring.application.claim;

import ca.ulaval.glo4003.insuring.application.claim.dto.ClaimDto;
import ca.ulaval.glo4003.insuring.application.claim.error.ClaimNotFoundError;
import ca.ulaval.glo4003.insuring.domain.claim.Claim;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimRepository;
import ca.ulaval.glo4003.insuring.domain.claim.exception.ClaimNotFoundException;
import ca.ulaval.glo4003.shared.domain.authority.AuthorityNumber;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.helper.claim.ClaimGenerator.createClaimId;
import static ca.ulaval.glo4003.helper.shared.AuthorityGenerator.createAuthorityNumber;
import static ca.ulaval.glo4003.matcher.ClaimMatcher.matchesClaimDto;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClaimAppServiceTest {
  private static final ClaimId CLAIM_ID = createClaimId();
  private static final AuthorityNumber AUTHORITY_NUMBER = createAuthorityNumber();

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

  @Test
  public void providingAuthorityNumber_shouldGetClaimById() throws ClaimNotFoundException {
    subject.provideAuthorityNumber(CLAIM_ID, AUTHORITY_NUMBER);

    verify(claimRepository).getById(CLAIM_ID);
  }

  @Test
  public void providingAuthorityNumber_shouldProvideAuthorityNumber() {
    subject.provideAuthorityNumber(CLAIM_ID, AUTHORITY_NUMBER);

    verify(claim).provideAuthorityNumber(AUTHORITY_NUMBER);
  }

  @Test
  public void providingAuthorityNumber_shouldUpdateClaim() throws ClaimNotFoundException {
    subject.provideAuthorityNumber(CLAIM_ID, AUTHORITY_NUMBER);

    verify(claimRepository).update(claim);
  }

  @Test
  public void providingAuthorityNumber_shouldReturnCorrespondingClaimDto() {
    ClaimDto claimDto = subject.provideAuthorityNumber(CLAIM_ID, AUTHORITY_NUMBER);

    assertThat(claimDto, matchesClaimDto(claim));
  }

  @Test(expected = ClaimNotFoundError.class)
  public void providingAuthorityNumber_withNotExistingClaim_shouldThrow()
      throws ClaimNotFoundException {
    when(claimRepository.getById(any(ClaimId.class))).thenThrow(ClaimNotFoundException.class);

    subject.provideAuthorityNumber(CLAIM_ID, AUTHORITY_NUMBER);
  }
}
