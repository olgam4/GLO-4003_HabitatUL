package ca.ulaval.glo4003.helper.coverage.premium;

import ca.ulaval.glo4003.coverage.domain.CoverageCategory;
import ca.ulaval.glo4003.coverage.domain.premium.detail.BasicBlockCoveragePremiumDetail;
import ca.ulaval.glo4003.coverage.domain.premium.detail.PremiumDetail;
import ca.ulaval.glo4003.coverage.domain.premium.detail.PremiumDetails;
import ca.ulaval.glo4003.shared.domain.money.Money;
import com.github.javafaker.Faker;

import java.util.stream.IntStream;

import static ca.ulaval.glo4003.helper.coverage.CoverageCategoryGenerator.createCoverageCategory;
import static ca.ulaval.glo4003.helper.shared.MoneyGenerator.createMoneyGreaterThanZero;

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

  public static PremiumDetail createPremiumDetail() {
    return new DummyPremiumDetail(createCoverageCategory(), createPremium());
  }

  private static Money createPremium() {
    return createMoneyGreaterThanZero();
  }

  private static class DummyPremiumDetail extends PremiumDetail {
    DummyPremiumDetail(CoverageCategory coverage, Money premium) {
      super(coverage, premium);
    }
  }
}
