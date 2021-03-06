package ca.ulaval.glo4003.shared.domain.money;

import ca.ulaval.glo4003.shared.domain.ValueObject;

import java.math.BigDecimal;

public class Amount extends ValueObject {
  public static final Amount ZERO = new Amount(BigDecimal.ZERO);

  static final int DECIMAL_PRECISION = 2;
  static final int ROUNDING = BigDecimal.ROUND_HALF_UP;

  private final BigDecimal value;

  public Amount(BigDecimal value) {
    this.value = value.setScale(DECIMAL_PRECISION, ROUNDING);
  }

  public static Amount min(Amount first, Amount second) {
    return new Amount(first.value.min(second.value));
  }

  public static Amount max(Amount first, Amount second) {
    return new Amount(first.value.max(second.value));
  }

  public BigDecimal getValue() {
    return value;
  }

  public Amount add(Amount amount) {
    return new Amount(value.add(amount.value));
  }

  public Amount subtract(Amount amount) {
    return new Amount(value.subtract(amount.value));
  }

  public Amount multiply(double factor) {
    return new Amount(value.multiply(BigDecimal.valueOf(factor)));
  }

  public Amount divide(double factor) {
    return new Amount(value.divide(BigDecimal.valueOf(factor), ROUNDING));
  }

  public boolean isSmallerThan(Amount otherAmount) {
    return value.compareTo(otherAmount.value) < 0;
  }

  public boolean isSmallerOrEqual(Amount otherAmount) {
    return isSmallerThan(otherAmount) || equals(otherAmount);
  }

  public boolean isGreaterThan(Amount otherAmount) {
    return value.compareTo(otherAmount.value) > 0;
  }

  public boolean isGreaterOrEqual(Amount otherAmount) {
    return isGreaterThan(otherAmount) || equals(otherAmount);
  }

  public boolean isBetween(Amount firstAmount, Amount secondAmount) {
    return isGreaterThan(firstAmount) && isSmallerThan(secondAmount);
  }
}
