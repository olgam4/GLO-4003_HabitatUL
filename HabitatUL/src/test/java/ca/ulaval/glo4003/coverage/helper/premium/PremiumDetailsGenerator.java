package ca.ulaval.glo4003.coverage.helper.premium;

import ca.ulaval.glo4003.coverage.domain.premium.PremiumCategory;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.coverage.domain.premium.detail.BasicBlockCoveragePremiumDetail;
import ca.ulaval.glo4003.coverage.domain.premium.detail.PremiumDetail;
import ca.ulaval.glo4003.shared.domain.money.Money;
import com.github.javafaker.Faker;

import java.util.stream.IntStream;

import static ca.ulaval.glo4003.coverage.helper.premium.PremiumCategoryGenerator.createAdditionalPremiumCategory;
import static ca.ulaval.glo4003.coverage.helper.premium.PremiumCategoryGenerator.createPremiumCategory;
import static ca.ulaval.glo4003.shared.helper.MoneyGenerator.createMoneyGreaterThanZero;

public class PremiumDetailsGenerator {
  private PremiumDetailsGenerator() {}

  public static PremiumDetails createPremiumDetails() {
    PremiumDetails premiumDetails = new PremiumDetails(createBasicBlockCoveragePremiumDetail());
    int numberAdditionalCoverages = Faker.instance().number().randomDigit();
    IntStream.range(0, numberAdditionalCoverages)
        .mapToObj(i -> createPremiumDetail())
        .forEach(premiumDetails::addPremiumDetail);
    return premiumDetails;
  }

  public static BasicBlockCoveragePremiumDetail createBasicBlockCoveragePremiumDetail() {
    return new BasicBlockCoveragePremiumDetail(createPremium());
  }

  public static PremiumDetail createAdditionalPremiumDetail() {
    return createPremiumDetail(createAdditionalPremiumCategory());
  }

  public static PremiumDetail createPremiumDetail() {
    return createPremiumDetail(createPremiumCategory(), createPremium());
  }

  public static PremiumDetail createPremiumDetail(PremiumCategory premiumCategory) {
    return createPremiumDetail(premiumCategory, createPremium());
  }

  public static PremiumDetail createPremiumDetail(
      PremiumCategory premiumCategory, Money coveragePremium) {
    return new DummyPremiumDetail(premiumCategory, coveragePremium);
  }

  private static Money createPremium() {
    return createMoneyGreaterThanZero();
  }

  private static class DummyPremiumDetail extends PremiumDetail {
    DummyPremiumDetail(PremiumCategory premiumCategory, Money premium) {
      super(premiumCategory, premium);
    }
  }
}
