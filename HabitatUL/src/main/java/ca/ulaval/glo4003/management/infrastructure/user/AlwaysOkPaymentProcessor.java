package ca.ulaval.glo4003.management.infrastructure.user;

import ca.ulaval.glo4003.management.domain.user.PaymentProcessor;
import ca.ulaval.glo4003.shared.domain.Money;

public class AlwaysOkPaymentProcessor implements PaymentProcessor {
  @Override
  public void process(String userKey, Money payment) {
    System.out.println(
        String.format(
            "Payment processed for user: <%s> with amount of <%s>",
            userKey, payment.getAmount().getValue().toString()));
  }
}
