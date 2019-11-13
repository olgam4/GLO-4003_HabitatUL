package ca.ulaval.glo4003.coverage.domain.coverage.detail;

import ca.ulaval.glo4003.coverage.domain.CoverageCategory;
import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.money.Amount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CoverageDetails extends ValueObject {
  private final List<CoverageDetail> collection;

  public CoverageDetails(
      PersonalPropertyCoverageDetail personalPropertyCoverageDetail,
      CivilLiabilityCoverageDetail civilLiabilityCoverageDetail) {
    this.collection = Arrays.asList(personalPropertyCoverageDetail, civilLiabilityCoverageDetail);
  }

  private CoverageDetails(List<CoverageDetail> collection) {
    this.collection = collection;
  }

  public CoverageDetails addCoverageDetail(CoverageDetail coverageDetail) {
    List<CoverageDetail> coverageDetailsCopy = getCollection();
    coverageDetailsCopy.add(coverageDetail);
    return new CoverageDetails(coverageDetailsCopy);
  }

  public List<CoverageDetail> getCollection() {
    return new ArrayList<>(collection);
  }

  public Amount getCoverageAmount(CoverageCategory coverageCategory) {
    return collection.stream()
        .filter(x -> x.getCoverage().equals(coverageCategory))
        .findFirst()
        .map(CoverageDetail::getAmount)
        .orElse(Amount.ZERO);
  }
}
