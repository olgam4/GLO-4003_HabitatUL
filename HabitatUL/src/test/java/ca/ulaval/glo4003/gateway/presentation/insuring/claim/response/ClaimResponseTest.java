package ca.ulaval.glo4003.gateway.presentation.insuring.claim.response;

import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimStatus;
import ca.ulaval.glo4003.insuring.helper.claim.ClaimGenerator;
import ca.ulaval.glo4003.shared.domain.authority.AuthorityNumber;
import org.junit.Test;

import static ca.ulaval.glo4003.shared.domain.authority.AuthorityNumber.UNFILLED_AUTHORITY_NUMBER;
import static ca.ulaval.glo4003.shared.helper.AuthorityGenerator.createAuthorityNumber;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ClaimResponseTest {
  private final ClaimId CLAIM_ID = ClaimGenerator.createClaimId();
  private final AuthorityNumber AUTHORITY_NUMBER = createAuthorityNumber();
  private final ClaimStatus CLAIM_STATUS = ClaimGenerator.createClaimStatus();
  private ClaimResponse subject;

  @Test
  public void gettingAuthorityNumber_withFilledAuthorityNumber_shouldReturnAssociatedValue() {
    subject = new ClaimResponse(CLAIM_ID, CLAIM_STATUS, AUTHORITY_NUMBER);

    assertNotNull(subject.getAuthorityNumber());
  }

  @Test
  public void gettingAuthorityNumber_withNotFilledAuthorityNumber_shouldBeNull() {
    subject = new ClaimResponse(CLAIM_ID, CLAIM_STATUS, UNFILLED_AUTHORITY_NUMBER);

    assertNull(subject.getAuthorityNumber());
  }
}
