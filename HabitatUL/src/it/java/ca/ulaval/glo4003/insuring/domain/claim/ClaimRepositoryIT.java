package ca.ulaval.glo4003.insuring.domain.claim;

import ca.ulaval.glo4003.helper.claim.ClaimBuilder;
import ca.ulaval.glo4003.helper.claim.ClaimGenerator;
import ca.ulaval.glo4003.insuring.domain.claim.exception.ClaimAlreadyCreatedException;
import ca.ulaval.glo4003.insuring.domain.claim.exception.ClaimNotFoundException;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.matcher.ClaimMatcher.matchesClaim;
import static org.junit.Assert.assertThat;

public abstract class ClaimRepositoryIT {
  private static final ClaimId CLAIM_ID = ClaimGenerator.createClaimId();

  private ClaimRepository subject;
  private Claim claim;
  private Claim updatedClaim;
  private ClaimId claimId;

  @Before
  public void setUp() {
    subject = createSubject();
    claim = ClaimGenerator.createClaim();
    claimId = claim.getClaimId();
  }

  @Test(expected = ClaimNotFoundException.class)
  public void gettingClaimById_withUnknownClaimId_shouldThrow() throws ClaimNotFoundException {
    subject.getById(CLAIM_ID);
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
