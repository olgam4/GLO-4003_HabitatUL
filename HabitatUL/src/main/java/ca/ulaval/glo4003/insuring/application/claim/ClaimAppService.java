package ca.ulaval.glo4003.insuring.application.claim;

import ca.ulaval.glo4003.insuring.application.claim.dto.ClaimDto;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.shared.domain.authority.AuthorityNumber;

public interface ClaimAppService {
  ClaimDto getClaim(ClaimId claimId);

  ClaimDto provideAuthorityNumber(ClaimId claimId, AuthorityNumber authorityNumber);
}
