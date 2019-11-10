package ca.ulaval.glo4003.calculator.domain.premium.detail;

import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.money.Money;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PremiumDetails extends ValueObject {
  private final List<PremiumDetail> collection;

  public PremiumDetails(BaseCoveragePremiumDetail baseCoveragePremiumDetail) {
    collection = Arrays.asList(baseCoveragePremiumDetail);
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

  public Money computeTotalPremium() {
    return collection.stream()
        .map(PremiumDetail::getPremium)
        .collect(Collectors.toList())
        .stream()
        .reduce(Money.ZERO, Money::add);
  }
}
