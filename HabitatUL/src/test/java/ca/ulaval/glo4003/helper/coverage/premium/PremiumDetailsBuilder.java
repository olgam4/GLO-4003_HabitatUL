package ca.ulaval.glo4003.helper.coverage.premium;

import ca.ulaval.glo4003.coverage.domain.premium.detail.BasicBlockCoveragePremiumDetail;
import ca.ulaval.glo4003.coverage.domain.premium.detail.PremiumDetail;
import ca.ulaval.glo4003.coverage.domain.premium.detail.PremiumDetails;
import ca.ulaval.glo4003.shared.domain.money.Money;

import java.util.ArrayList;
import java.util.List;

import static ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsGenerator.createBasicBlockCoveragePremiumDetail;

public class PremiumDetailsBuilder {
  private static final BasicBlockCoveragePremiumDetail DEFAULT_BASE_COVERAGE_PREMIUM_DETAIL =
      createBasicBlockCoveragePremiumDetail();

  private BasicBlockCoveragePremiumDetail basicBlockCoveragePremiumDetail =
      DEFAULT_BASE_COVERAGE_PREMIUM_DETAIL;
  private List<PremiumDetail> additionalPremiumDetails = new ArrayList<>();

  private PremiumDetailsBuilder() {}

  public static PremiumDetailsBuilder aPremiumDetails() {
    return new PremiumDetailsBuilder();
  }

  public PremiumDetailsBuilder withBasicBlockCoveragePremiumDetail(
      BasicBlockCoveragePremiumDetail basicBlockCoveragePremiumDetail) {
    this.basicBlockCoveragePremiumDetail = basicBlockCoveragePremiumDetail;
    return this;
  }

  public PremiumDetailsBuilder withBasicBlockCoveragePremiumDetail(Money basicBlockPremium) {
    this.basicBlockCoveragePremiumDetail = new BasicBlockCoveragePremiumDetail(basicBlockPremium);
    return this;
  }

  public PremiumDetailsBuilder withAdditionalPremiumDetail(PremiumDetail premiumDetail) {
    this.additionalPremiumDetails.add(premiumDetail);
    return this;
  }

  public PremiumDetails build() {
    PremiumDetails premiumDetails = new PremiumDetails(basicBlockCoveragePremiumDetail);
    for (PremiumDetail premiumDetail : additionalPremiumDetails) {
      premiumDetails = premiumDetails.addPremiumDetail(premiumDetail);
    }
    return premiumDetails;
  }
}