package ca.ulaval.glo4003.insuring.domain.policy.lossratio;

import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public class LossRatio extends ValueObject {
  private final Float value;

  public LossRatio(Float value) {
    this.value = value;
  }

  public LossRatio(Amount coverageAmount, Amount losses) {
    this.value = losses.getValue().floatValue() / coverageAmount.getValue().floatValue();
  }

  public float getValue() {
    return value;
  }

  public boolean isSmallerThan(LossRatio otherLossRatio) {
    return value.compareTo(otherLossRatio.value) < 0;
  }

  public boolean isGreaterThan(LossRatio otherLossRatio) {
    return value.compareTo(otherLossRatio.value) > 0;
  }

  public boolean isBetween(LossRatio firstLossRatio, LossRatio secondLossRatio) {
    return isGreaterThan(firstLossRatio) && isSmallerThan(secondLossRatio);
  }
}
