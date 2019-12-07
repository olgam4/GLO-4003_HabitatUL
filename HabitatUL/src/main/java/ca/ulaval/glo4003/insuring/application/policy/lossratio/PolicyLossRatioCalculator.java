package ca.ulaval.glo4003.insuring.application.policy.lossratio;

import ca.ulaval.glo4003.insuring.domain.claim.Claim;
import ca.ulaval.glo4003.insuring.domain.policy.Policy;
import ca.ulaval.glo4003.insuring.domain.policy.lossratio.LossRatio;

import java.util.List;

public interface PolicyLossRatioCalculator {
  LossRatio computeLossRatioWithAdditionalClaim(Policy policy, Claim claim);

  List<Claim> getNotYetAcceptedClaimsExceedingLossRatio(Policy policy, LossRatio lossRatio);
}
