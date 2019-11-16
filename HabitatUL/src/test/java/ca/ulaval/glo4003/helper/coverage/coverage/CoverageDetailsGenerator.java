package ca.ulaval.glo4003.helper.coverage.coverage;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageCategory;
import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.coverage.detail.CivilLiabilityCoverageDetail;
import ca.ulaval.glo4003.coverage.domain.coverage.detail.CoverageDetail;
import ca.ulaval.glo4003.coverage.domain.coverage.detail.PersonalPropertyCoverageDetail;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import com.github.javafaker.Faker;

import java.util.stream.IntStream;

import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageCategoryGenerator.createAdditionalCoverageCategory;
import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageCategoryGenerator.createBaseCoverageCategory;
import static ca.ulaval.glo4003.helper.shared.MoneyGenerator.createAmountGreaterThanZero;

public class CoverageDetailsGenerator {
  private CoverageDetailsGenerator() {}

  public static CoverageDetails createCoverageDetails() {
    CoverageDetails premiumDetails =
        new CoverageDetails(
            createPersonalPropertyCoverageDetail(), createCivilLiabilityCoverageDetail());
    int numberAdditionalCoverages = Faker.instance().number().randomDigit();
    IntStream.range(0, numberAdditionalCoverages)
        .mapToObj(i -> createAdditionalCoverageDetail())
        .forEach(premiumDetails::addCoverageDetail);
    return premiumDetails;
  }

  public static PersonalPropertyCoverageDetail createPersonalPropertyCoverageDetail() {
    return new PersonalPropertyCoverageDetail(createCoverageAmount());
  }

  public static CivilLiabilityCoverageDetail createCivilLiabilityCoverageDetail() {
    return new CivilLiabilityCoverageDetail(createCoverageAmount());
  }

  public static CoverageDetail createBaseCoverageDetail() {
    return createCoverageDetail(createBaseCoverageCategory());
  }

  public static CoverageDetail createAdditionalCoverageDetail() {
    return createCoverageDetail(createAdditionalCoverageCategory());
  }

  public static CoverageDetail createCoverageDetail(CoverageCategory coverageCategory) {
    return createCoverageDetail(coverageCategory, createCoverageAmount());
  }

  public static CoverageDetail createCoverageDetail(
      CoverageCategory coverageCategory, Amount coverageAmount) {
    return new DummyCoverageDetail(coverageCategory, coverageAmount);
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
