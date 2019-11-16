package ca.ulaval.glo4003.coverage.domain.premium;

import ca.ulaval.glo4003.coverage.domain.premium.detail.BasicBlockCoveragePremiumDetail;
import ca.ulaval.glo4003.coverage.domain.premium.detail.PremiumDetail;
import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.money.Money;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PremiumDetails extends ValueObject {
  private final List<PremiumDetail> collection;

  public PremiumDetails(BasicBlockCoveragePremiumDetail basicBlockCoveragePremiumDetail) {
    collection = Arrays.asList(basicBlockCoveragePremiumDetail);
  }

  private PremiumDetails(List<PremiumDetail> collection) {
    this.collection = collection;
  }

  public PremiumDetails addPremiumDetail(PremiumDetail premiumDetail) {
    List<PremiumDetail> premiumDetailsCopy = getCollection();
    premiumDetailsCopy.add(premiumDetail);
    return new PremiumDetails(premiumDetailsCopy);
  }

  public List<PremiumDetail> getCollection() {
    return new ArrayList<>(collection);
  }

  public Money getCoveragePremium(PremiumCategory coverageCategory) {
    return collection.stream()
        .filter(x -> x.getCoverage().equals(coverageCategory))
        .findFirst()
        .map(PremiumDetail::getPremium)
        .orElse(Money.ZERO);
  }

  public PremiumDetails update(PremiumDetail updatedPremiumDetail) {
    List<PremiumDetail> updatedCollection =
        getCollection().stream()
            .filter(x -> !x.getCoverage().equals(updatedPremiumDetail.getCoverage()))
            .collect(Collectors.toList());
    updatedCollection.add(updatedPremiumDetail);
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
