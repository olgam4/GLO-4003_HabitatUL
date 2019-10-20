package ca.ulaval.glo4003.gateway.presentation.quote.response;

import ca.ulaval.glo4003.shared.domain.money.Amount;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"personalProperty", "civilLiability"})
public class QuoteCoverageOverviewResponse {
  private Amount personalProperty;
  private Amount civilLiability;

  public QuoteCoverageOverviewResponse(Amount personalProperty, Amount civilLiability) {
    this.personalProperty = personalProperty;
    this.civilLiability = civilLiability;
  }

  public Amount getPersonalProperty() {
    return personalProperty;
  }

  public Amount getCivilLiability() {
    return civilLiability;
  }
}
