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

  public Amount computeTotalLosses() {
    return collection.values().stream().reduce(Amount.ZERO, Amount::add);
  }
}
