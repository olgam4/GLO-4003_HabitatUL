package ca.ulaval.glo4003.coverage.domain.premium;

import ca.ulaval.glo4003.coverage.domain.premium.detail.BasicBlockCoveragePremiumDetail;
import ca.ulaval.glo4003.coverage.domain.premium.detail.PremiumDetail;
import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.money.Money;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PremiumDetails extends ValueObject {
  private final Set<PremiumDetail> collection;

  public PremiumDetails(BasicBlockCoveragePremiumDetail basicBlockCoveragePremiumDetail) {
    collection = new HashSet<>(Arrays.asList(basicBlockCoveragePremiumDetail));
  }

  private PremiumDetails(Set<PremiumDetail> collection) {
    this.collection = collection;
  }

  public PremiumDetails addPremiumDetail(PremiumDetail premiumDetail) {
    Set<PremiumDetail> premiumDetailsCopy = getCollection();
    premiumDetailsCopy.add(premiumDetail);
    return new PremiumDetails(premiumDetailsCopy);
  }

  public Set<PremiumDetail> getCollection() {
    return new HashSet<>(collection);
  }

  public Money getCoveragePremium(PremiumCategory coverageCategory) {
    return collection.stream()
        .filter(x -> x.getCoverage().equals(coverageCategory))
        .findFirst()
        .map(PremiumDetail::getPremium)
        .orElse(Money.ZERO);
  }

  public boolean includes(PremiumCategory premiumCategory) {
    return collection.stream().anyMatch(x -> x.getCoverage().equals(premiumCategory));
  }

  public PremiumDetails update(PremiumDetail updatedPremiumDetail) {
    return update(new HashSet<>(Arrays.asList(updatedPremiumDetail)));
  }

  public PremiumDetails update(Set<PremiumDetail> updatedPremiumDetails) {
    List<PremiumCategory> updatedPremiumCategories =
        updatedPremiumDetails.stream().map(PremiumDetail::getCoverage).collect(Collectors.toList());
    Set<PremiumDetail> updatedCollection =
        getCollection().stream()
            .filter(x -> !updatedPremiumCategories.contains(x.getCoverage()))
            .collect(Collectors.toSet());
    updatedCollection.addAll(updatedPremiumDetails);
    return new PremiumDetails(updatedCollection);
  }

  public Money computeTotalPremium() {
    return collection.stream()
        .map(PremiumDetail::getPremium)
        .collect(Collectors.toList())
        .stream()
        .reduce(Money.ZERO, Money::add);
  }
}
