package ca.ulaval.glo4003.insuring.helper.claim;

import ca.ulaval.glo4003.insuring.domain.claim.LossCategory;
import ca.ulaval.glo4003.insuring.domain.claim.LossDeclarations;
import ca.ulaval.glo4003.shared.domain.money.Amount;

import java.util.HashMap;
import java.util.Map;

import static ca.ulaval.glo4003.insuring.domain.claim.LossCategory.BICYCLE;
import static ca.ulaval.glo4003.shared.helper.MoneyGenerator.createAmountGreaterThanZero;

public class LossDeclarationsBuilder {
  private Map<LossCategory, Amount> losses = new HashMap<>();

  private LossDeclarationsBuilder() {}

  public static LossDeclarationsBuilder aLossDeclaration() {
    return new LossDeclarationsBuilder();
  }

  public LossDeclarationsBuilder withBicycleLoss() {
    withLoss(BICYCLE);
    return this;
  }

  public LossDeclarationsBuilder withLoss(LossCategory lossCategory) {
    withLoss(lossCategory, createAmountGreaterThanZero());
    return this;
  }

  public LossDeclarationsBuilder withLoss(LossCategory lossCategory, Amount amount) {
    losses.put(lossCategory, amount);
    return this;
  }

  public LossDeclarations build() {
    return new LossDeclarations(losses);
  }
}
