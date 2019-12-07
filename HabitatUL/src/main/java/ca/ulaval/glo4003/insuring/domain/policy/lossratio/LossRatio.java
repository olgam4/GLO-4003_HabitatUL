package ca.ulaval.glo4003.insuring.domain.policy.lossratio;

import ca.ulaval.glo4003.shared.domain.ValueObject;

public class LossRatio extends ValueObject {
  private final float value;

  public LossRatio(Float value) {
    this.value = value;
  }

  public float getValue() {
    return value;
  }
}
