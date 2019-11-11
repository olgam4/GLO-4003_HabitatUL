package ca.ulaval.glo4003.helper.calculator;

import ca.ulaval.glo4003.calculator.domain.premium.detail.BasicBlockCoveragePremiumDetail;
import ca.ulaval.glo4003.calculator.domain.premium.detail.PremiumDetail;
import ca.ulaval.glo4003.calculator.domain.premium.detail.PremiumDetails;

import java.util.ArrayList;
import java.util.List;

import static ca.ulaval.glo4003.helper.calculator.PremiumDetailsGenerator.createBasicBlockCoveragePremiumDetail;

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

  public PremiumDetailsBuilder withAdditionalPremiumDetail(PremiumDetail premiumDetail) {
    this.additionalPremiumDetails.add(premiumDetail);
    return this;
  }

  public PremiumDetailsBuilder withoutAdditionalPremiumDetail() {
    this.additionalPremiumDetails = new ArrayList<>();
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
