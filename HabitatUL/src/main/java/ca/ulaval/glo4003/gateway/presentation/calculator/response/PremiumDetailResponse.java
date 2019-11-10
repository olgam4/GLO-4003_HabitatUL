package ca.ulaval.glo4003.gateway.presentation.calculator.response;

import ca.ulaval.glo4003.shared.domain.money.Money;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"coverage", "premium"})
public class PremiumDetailResponse {
  private String coverage;
  private Money premium;

  private PremiumDetailResponse() {}

  public PremiumDetailResponse(String coverage, Money premium) {
    this.coverage = coverage;
    this.premium = premium;
  }

  public String getCoverage() {
    return coverage;
  }

  public Money getPremium() {
    return premium;
  }
}
