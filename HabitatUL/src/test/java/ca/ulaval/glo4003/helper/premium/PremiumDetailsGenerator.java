package ca.ulaval.glo4003.helper.premium;

import ca.ulaval.glo4003.calculator.domain.premium.detail.BaseCoveragePremiumDetail;
import ca.ulaval.glo4003.calculator.domain.premium.detail.PremiumDetail;
import ca.ulaval.glo4003.calculator.domain.premium.detail.PremiumDetails;
import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import com.github.javafaker.Faker;

import java.util.stream.IntStream;

public class PremiumDetailsGenerator {
  private PremiumDetailsGenerator() {}

  public static PremiumDetails createPremiumDetails() {
    PremiumDetails premiumDetails = new PremiumDetails(createBaseCoveragePremiumDetail());
    int numberAdditionalCoverages = Faker.instance().number().randomDigit();
    IntStream.range(0, numberAdditionalCoverages)
        .mapToObj(i -> createPremiumDetail())
        .forEach(premiumDetails::addPremiumDetail);
    return premiumDetails;
  }

  public static BaseCoveragePremiumDetail createBaseCoveragePremiumDetail() {
    return new BaseCoveragePremiumDetail(createPremium());
  }

  public static PremiumDetail createPremiumDetail() {
    return new PremiumDetail(createCoverageName(), createPremium());
  }

  private static String createCoverageName() {
    return Faker.instance().commerce().productName();
  }

  private static Money createPremium() {
    return MoneyGenerator.createMoneyGreaterThan(Money.ZERO);
  }
}
