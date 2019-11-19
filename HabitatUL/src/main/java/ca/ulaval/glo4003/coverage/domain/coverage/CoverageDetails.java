package ca.ulaval.glo4003.coverage.domain.coverage;

import ca.ulaval.glo4003.coverage.domain.coverage.detail.CivilLiabilityCoverageDetail;
import ca.ulaval.glo4003.coverage.domain.coverage.detail.CoverageDetail;
import ca.ulaval.glo4003.coverage.domain.coverage.detail.PersonalPropertyCoverageDetail;
import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.money.Amount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

  public boolean includes(CoverageCategory coverageCategory) {
    return collection.stream().anyMatch(x -> x.getCoverage().equals(coverageCategory));
  }

  public CoverageDetails update(CoverageDetail updatedCoverageDetail) {
    return update(Arrays.asList(updatedCoverageDetail));
  }

  public CoverageDetails update(List<CoverageDetail> updatedCoverageDetails) {
    List<CoverageCategory> updatedCoverageCategories =
        updatedCoverageDetails.stream()
            .map(CoverageDetail::getCoverage)
            .collect(Collectors.toList());
    List<CoverageDetail> updatedCollection =
        getCollection().stream()
            .filter(x -> !updatedCoverageCategories.contains(x.getCoverage()))
            .collect(Collectors.toList());
    updatedCollection.addAll(updatedCoverageDetails);
    return new CoverageDetails(updatedCollection);
  }
}
