package ca.ulaval.glo4003.insuring.application.policy.claimexpiration;

import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;

public interface ClaimExpirationProcessor {
  void scheduleExpiration(ClaimId claimId, DateTime expirationDateTime);

  void cancelExpiration(ClaimId claimId);
}
