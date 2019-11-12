package ca.ulaval.glo4003.helper.coverage.coverage;

import ca.ulaval.glo4003.coverage.domain.CoverageCategory;
import ca.ulaval.glo4003.coverage.domain.coverage.detail.CivilLiabilityCoverageDetail;
import ca.ulaval.glo4003.coverage.domain.coverage.detail.CoverageDetail;
import ca.ulaval.glo4003.coverage.domain.coverage.detail.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.coverage.detail.PersonalPropertyCoverageDetail;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import com.github.javafaker.Faker;

import java.util.stream.IntStream;

import static ca.ulaval.glo4003.helper.coverage.CoverageCategoryGenerator.createCoverageCategory;
import static ca.ulaval.glo4003.helper.coverage.premium.QuotePremiumInputGenerator.createCivilLiabilityLimit;
import static ca.ulaval.glo4003.helper.shared.MoneyGenerator.createAmountGreaterThanZero;

public class CoverageDetailsGenerator {
  private CoverageDetailsGenerator() {}

  public static CoverageDetails createCoverageDetails() {
    CoverageDetails premiumDetails =
        new CoverageDetails(
            createPersonalPropertyCoverageDetail(), createCivilLiabilityCoverageDetail());
    int numberAdditionalCoverages = Faker.instance().number().randomDigit();
    IntStream.range(0, numberAdditionalCoverages)
        .mapToObj(i -> createCoverageDetail())
        .forEach(premiumDetails::addCoverageDetail);
    return premiumDetails;
  }

  public static PersonalPropertyCoverageDetail createPersonalPropertyCoverageDetail() {
    return new PersonalPropertyCoverageDetail(createCoverageAmount());
  }

  public static CivilLiabilityCoverageDetail createCivilLiabilityCoverageDetail() {
    return new CivilLiabilityCoverageDetail(createCivilLiabilityLimit());
  }

  public static CoverageDetail createCoverageDetail() {
    return new DummyCoverageDetail(createCoverageCategory(), createCoverageAmount());
  }

  private static Amount createCoverageAmount() {
    return createAmountGreaterThanZero();
  }

  private static class DummyCoverageDetail extends CoverageDetail {
    DummyCoverageDetail(CoverageCategory coverage, Amount limit) {
      super(coverage, limit);
    }
  }
}
