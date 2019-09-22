package ca.ulaval.glo4003.quote.domain.quote;

import ca.ulaval.glo4003.shared.ValueObject;

import java.util.UUID;

public class QuoteId extends ValueObject {
  private UUID value;

  public QuoteId() {
    this.value = UUID.randomUUID();
  }
}
