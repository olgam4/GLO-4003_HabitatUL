package ca.ulaval.glo4003.quote.domain;

import ca.ulaval.glo4003.quote.domain.commons.Premium;

public interface PaymentProcessor {
  void processPayment(Premium premium);
}
