package ca.ulaval.glo4003.management.infrastructure.user;

import ca.ulaval.glo4003.management.domain.user.PaymentProcessor;
import ca.ulaval.glo4003.shared.domain.Money;

public class AlwaysOkPaymentProcessor implements PaymentProcessor {
  @Override
  public void process(String userKey, Money price) {
    System.out.println("Payment processed");
  }
}
