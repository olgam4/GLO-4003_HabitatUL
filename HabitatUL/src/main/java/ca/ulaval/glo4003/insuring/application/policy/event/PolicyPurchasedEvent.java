package ca.ulaval.glo4003.insuring.application.policy.event;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.detail.PremiumDetails;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyInformation;
import ca.ulaval.glo4003.mediator.Event;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.Period;

public class PolicyPurchasedEvent extends Event {
  private final String quoteKey;
  private final Period coveragePeriod;
  private final Date purchaseDate;
  private final PolicyInformation policyInformation;
  private final CoverageDetails coverageDetails;
  private final PremiumDetails premiumDetails;

  public PolicyPurchasedEvent(
      String quoteKey,
      Period coveragePeriod,
      Date purchaseDate,
      PolicyInformation policyInformation,
      CoverageDetails coverageDetails,
      PremiumDetails premiumDetails) {
    this.quoteKey = quoteKey;
    this.coveragePeriod = coveragePeriod;
    this.purchaseDate = purchaseDate;
    this.policyInformation = policyInformation;
    this.coverageDetails = coverageDetails;
    this.premiumDetails = premiumDetails;
  }

  public String getQuoteKey() {
    return quoteKey;
  }

  public Period getCoveragePeriod() {
    return coveragePeriod;
  }

  public Date getPurchaseDate() {
    return purchaseDate;
  }

  public PolicyInformation getPolicyInformation() {
    return policyInformation;
  }

  public CoverageDetails getCoverageDetails() {
    return coverageDetails;
  }

  public PremiumDetails getPremiumDetails() {
    return premiumDetails;
  }
}
