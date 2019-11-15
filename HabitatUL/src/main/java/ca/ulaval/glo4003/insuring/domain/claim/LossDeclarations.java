package ca.ulaval.glo4003.insuring.domain.claim;

import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.money.Amount;

import java.util.Map;

public class LossDeclarations extends ValueObject {
  private Map<LossCategory, Amount> collection;

  public LossDeclarations(Map<LossCategory, Amount> collection) {
    this.collection = collection;
  }

  public Map<LossCategory, Amount> getCollection() {
    return collection;
  }

  public boolean isEmpty() {
    return collection.isEmpty();
  }

  public boolean includes(LossCategory lossCategory) {
    return collection.keySet().contains(lossCategory);
  }

  public Amount getLossAmount(LossCategory lossCategory) {
    return collection.getOrDefault(lossCategory, Amount.ZERO);
  }

  public Amount computePersonalPropertyLosses() {
    return collection.entrySet().stream()
        .filter(x -> !x.getKey().equals(LossCategory.BICYCLE))
        .map(Map.Entry::getValue)
        .reduce(Amount.ZERO, Amount::add);
  }
}
