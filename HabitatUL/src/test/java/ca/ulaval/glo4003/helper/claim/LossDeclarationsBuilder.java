package ca.ulaval.glo4003.helper.claim;

import ca.ulaval.glo4003.insuring.domain.claim.LossCategory;
import ca.ulaval.glo4003.insuring.domain.claim.LossDeclarations;
import ca.ulaval.glo4003.shared.domain.money.Amount;

import java.util.HashMap;
import java.util.Map;

public class LossDeclarationsBuilder {
  private Map<LossCategory, Amount> losses = new HashMap<>();

  private LossDeclarationsBuilder() {}

  public static LossDeclarationsBuilder aLossDeclaration() {
    return new LossDeclarationsBuilder();
  }

  public LossDeclarationsBuilder withLoss(LossCategory lossCategory, Amount amount) {
    losses.put(lossCategory, amount);
    return this;
  }

  public LossDeclarations build() {
    return new LossDeclarations(losses);
  }
}
