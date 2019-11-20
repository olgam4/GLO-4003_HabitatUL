package ca.ulaval.glo4003.coverage.domain.coverage;

import ca.ulaval.glo4003.coverage.domain.coverage.detail.CivilLiabilityCoverageDetail;
import ca.ulaval.glo4003.coverage.domain.coverage.detail.CoverageDetail;
import ca.ulaval.glo4003.coverage.domain.coverage.detail.PersonalPropertyCoverageDetail;
import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.money.Amount;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CoverageDetails extends ValueObject {
  private final Set<CoverageDetail> collection;

  public CoverageDetails(
      PersonalPropertyCoverageDetail personalPropertyCoverageDetail,
      CivilLiabilityCoverageDetail civilLiabilityCoverageDetail) {
    this.collection =
        new HashSet<>(Arrays.asList(personalPropertyCoverageDetail, civilLiabilityCoverageDetail));
  }

  private CoverageDetails(Set<CoverageDetail> collection) {
    this.collection = collection;
  }

  public CoverageDetails addCoverageDetail(CoverageDetail coverageDetail) {
    Set<CoverageDetail> coverageDetailsCopy = getCollection();
    coverageDetailsCopy.add(coverageDetail);
    return new CoverageDetails(coverageDetailsCopy);
  }

  public Set<CoverageDetail> getCollection() {
    return new HashSet<>(collection);
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
    return update(new HashSet<>(Arrays.asList(updatedCoverageDetail)));
  }

  public CoverageDetails update(Set<CoverageDetail> updatedCoverageDetails) {
    List<CoverageCategory> updatedCoverageCategories =
        updatedCoverageDetails.stream()
            .map(CoverageDetail::getCoverage)
            .collect(Collectors.toList());
    Set<CoverageDetail> updatedCollection =
        getCollection().stream()
            .filter(x -> !updatedCoverageCategories.contains(x.getCoverage()))
            .collect(Collectors.toSet());
    updatedCollection.addAll(updatedCoverageDetails);
    return new CoverageDetails(updatedCollection);
  }
}
