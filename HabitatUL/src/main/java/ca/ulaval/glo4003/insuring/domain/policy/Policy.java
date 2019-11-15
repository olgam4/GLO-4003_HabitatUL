package ca.ulaval.glo4003.insuring.domain.policy;

import ca.ulaval.glo4003.coverage.domain.coverage.detail.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.detail.PremiumDetails;
import ca.ulaval.glo4003.insuring.domain.claim.Claim;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.insuring.domain.policy.error.ClaimOutsideCoveragePeriodError;
import ca.ulaval.glo4003.mediator.AggregateRoot;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.Period;

import java.util.ArrayList;
import java.util.List;

public class Policy extends AggregateRoot {
  private final List<ClaimId> claims = new ArrayList<>();
  private PolicyId policyId;
  private String quoteKey;
  private Period coveragePeriod;
  private PolicyInformation policyInformation;
  private CoverageDetails coverageDetails;
  private PremiumDetails premiumDetails;
  private ClockProvider clockProvider;

  public Policy(
      PolicyId policyId,
      String quoteKey,
      Period coveragePeriod,
      PolicyInformation policyInformation,
      CoverageDetails coverageDetails,
      PremiumDetails premiumDetails,
      ClockProvider clockProvider) {
    this.policyId = policyId;
    this.quoteKey = quoteKey;
    this.coveragePeriod = coveragePeriod;
    this.policyInformation = policyInformation;
    this.coverageDetails = coverageDetails;
    this.premiumDetails = premiumDetails;
    this.clockProvider = clockProvider;
  }

  public PolicyId getPolicyId() {
    return policyId;
  }

  public String getQuoteKey() {
    return quoteKey;
  }

  public Period getCoveragePeriod() {
    return coveragePeriod;
  }

  public List<ClaimId> getClaims() {
    return claims;
  }

  public CoverageDetails getCoverageDetails() {
    return coverageDetails;
  }

  public PremiumDetails getPremiumDetails() {
    return premiumDetails;
  }

  public void issue() {
    registerEvent(new PolicyIssuedEvent(policyId, quoteKey));
  }

  public void openClaim(Claim claim) {
    checkIfClaimOutsideCoveragePeriod();
    claim.validate(policyInformation, coverageDetails);
    claims.add(claim.getClaimId());
    registerEvent(
        new ClaimOpenedEvent(policyId, claim.getClaimId(), Date.now(clockProvider.getClock())));
  }

  private void checkIfClaimOutsideCoveragePeriod() {
    // TODO: should ask for date of occurrence
    Date now = Date.now(clockProvider.getClock());
    if (!coveragePeriod.isWithin(now)) {
      throw new ClaimOutsideCoveragePeriodError();
    }
  }
}
