package ca.ulaval.glo4003.coverage.domain.claim;

import ca.ulaval.glo4003.shared.domain.money.Amount;

import java.util.Map;

public class LossDeclarations {
  private Map<LossCategory, Amount> lossDeclarations;

  public LossDeclarations(Map<LossCategory, Amount> lossDeclarations) {
    this.lossDeclarations = lossDeclarations;
  }

  public Map<LossCategory, Amount> getLossDeclarations() {
    return lossDeclarations;
  }

  public Amount computeTotalLosses() {
    return lossDeclarations.values().stream().reduce(Amount.ZERO, Amount::add);
  }
}
