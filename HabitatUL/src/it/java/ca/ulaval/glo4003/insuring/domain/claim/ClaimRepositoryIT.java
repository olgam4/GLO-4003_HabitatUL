package ca.ulaval.glo4003.insuring.domain.claim;

import ca.ulaval.glo4003.insuring.domain.claim.exception.ClaimAlreadyCreatedException;
import ca.ulaval.glo4003.insuring.domain.claim.exception.ClaimNotFoundException;
import ca.ulaval.glo4003.insuring.helper.claim.ClaimBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static ca.ulaval.glo4003.insuring.helper.claim.ClaimGenerator.createClaim;
import static ca.ulaval.glo4003.insuring.helper.claim.ClaimGenerator.createClaimId;
import static ca.ulaval.glo4003.insuring.matcher.ClaimMatcher.matchesClaim;
import static org.junit.Assert.*;

public abstract class ClaimRepositoryIT {
  private static final ClaimId NOT_EXISTING_CLAIM_ID = createClaimId();

  private ClaimRepository subject;
  private Claim claim;
  private Claim updatedClaim;
  private ClaimId claimId;

  @Before
  public void setUp() {
    subject = createSubject();
    claim = createClaim();
    claimId = claim.getClaimId();
  }

  @Test(expected = ClaimNotFoundException.class)
  public void gettingClaimById_withUnknownClaimId_shouldThrow() throws ClaimNotFoundException {
    subject.getById(NOT_EXISTING_CLAIM_ID);
  }

  @Test
  public void findingClaimById_withExistingClaimId_shouldReturnAssociatedClaim()
      throws ClaimAlreadyCreatedException {
    subject.create(claim);

    Optional<Claim> optionalClaim = subject.findById(claimId);

    assertTrue(optionalClaim.isPresent());
    assertThat(optionalClaim.get(), matchesClaim(claim));
  }

  @Test
  public void findingClaimById_withUnknownClaimId_shouldReturnEmptyClaim() {
    Optional<Claim> optionalClaim = subject.findById(NOT_EXISTING_CLAIM_ID);

    assertFalse(optionalClaim.isPresent());
  }

  @Test
  public void creatingClaim_shouldPersistClaimAsIs()
      throws ClaimAlreadyCreatedException, ClaimNotFoundException {
    subject.create(claim);

    assertThat(subject.getById(claimId), matchesClaim(claim));
  }

  @Test(expected = ClaimAlreadyCreatedException.class)
  public void creatingClaim_withAlreadyPersistedClaimId_shouldThrow()
      throws ClaimAlreadyCreatedException {
    subject.create(claim);

    subject.create(claim);
  }

  @Test(expected = ClaimNotFoundException.class)
  public void updatingClaim_withNotYetPersistedClaim_shouldThrow() throws ClaimNotFoundException {
    subject.update(claim);
  }

  @Test
  public void updatingClaim_shouldChangeAssociatedClaim()
      throws ClaimAlreadyCreatedException, ClaimNotFoundException {
    subject.create(claim);
    updatedClaim = ClaimBuilder.aClaim().withClaimId(claim.getClaimId()).build();

    subject.update(updatedClaim);

    assertThat(subject.getById(claimId), matchesClaim(updatedClaim));
  }

  protected abstract ClaimRepository createSubject();
}
