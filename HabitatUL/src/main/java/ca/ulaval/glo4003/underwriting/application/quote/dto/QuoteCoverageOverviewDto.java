package ca.ulaval.glo4003.underwriting.application.quote.dto;

import ca.ulaval.glo4003.shared.domain.money.Amount;

public class QuoteCoverageOverviewDto {
  private Amount personalProperty;
  private Amount civilLiability;

  public QuoteCoverageOverviewDto(Amount personalProperty, Amount civilLiability) {
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
