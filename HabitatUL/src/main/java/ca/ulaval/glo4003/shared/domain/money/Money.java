package ca.ulaval.glo4003.shared.domain.money;

import ca.ulaval.glo4003.shared.domain.ValueObject;

public class Money extends ValueObject {
  public static final Money ZERO = new Money(Amount.ZERO);

  private final Amount amount;

  public Money(Amount amount) {
    this.amount = amount;
  }

  public static Money min(Money first, Money second) {
    return new Money(Amount.min(first.amount, second.amount));
  }

  public static Money max(Money first, Money second) {
    return new Money(Amount.max(first.amount, second.amount));
  }

  public Amount getAmount() {
    return amount;
  }

  public Money add(Money money) {
    return new Money(amount.add(money.amount));
  }

  public Money subtract(Money money) {
    return new Money(amount.subtract(money.amount));
  }

  public Money multiply(double factor) {
    return new Money(amount.multiply(factor));
  }

  public Money divide(double factor) {
    return new Money(amount.divide(factor));
  }
}
