package ca.ulaval.glo4003.administration.infrastructure.user;

import ca.ulaval.glo4003.administration.domain.user.PaymentProcessor;
import ca.ulaval.glo4003.shared.domain.money.Money;

public class DummyPaymentProcessor implements PaymentProcessor {
  @Override
  public void process(String userKey, Money payment) {
    System.out.println(
        String.format(
            "Payment processed for user: <%s> with amount of <%s>",
            userKey, payment.getAmount().getValue().toString()));
  }
}
