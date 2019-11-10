package ca.ulaval.glo4003.helper.premium;

import ca.ulaval.glo4003.calculator.domain.premium.detail.BaseCoveragePremiumDetail;
import ca.ulaval.glo4003.calculator.domain.premium.detail.PremiumDetail;
import ca.ulaval.glo4003.calculator.domain.premium.detail.PremiumDetails;

import java.util.ArrayList;
import java.util.List;

import static ca.ulaval.glo4003.helper.premium.PremiumDetailsGenerator.createBaseCoveragePremiumDetail;

public class PremiumDetailsBuilder {
  private static final BaseCoveragePremiumDetail DEFAULT_BASE_COVERAGE_PREMIUM_DETAIL =
      createBaseCoveragePremiumDetail();

  private BaseCoveragePremiumDetail baseCoveragePremiumDetail =
      DEFAULT_BASE_COVERAGE_PREMIUM_DETAIL;
  private List<PremiumDetail> additionalPremiumDetails = new ArrayList<>();

  private PremiumDetailsBuilder() {}

  public static PremiumDetailsBuilder aPremiumDetails() {
    return new PremiumDetailsBuilder();
  }

  public PremiumDetailsBuilder withBaseCoveragePremiumDetail(
      BaseCoveragePremiumDetail baseCoveragePremiumDetail) {
    this.baseCoveragePremiumDetail = baseCoveragePremiumDetail;
    return this;
  }

  public PremiumDetailsBuilder withAdditionalPremiumDetail(PremiumDetail premiumDetail) {
    this.additionalPremiumDetails.add(premiumDetail);
    return this;
  }

  public PremiumDetails build() {
    PremiumDetails premiumDetails = new PremiumDetails(baseCoveragePremiumDetail);
    for (PremiumDetail premiumDetail : additionalPremiumDetails) {
      premiumDetails = premiumDetails.addPremiumDetail(premiumDetail);
    }
    return premiumDetails;
  }
}
