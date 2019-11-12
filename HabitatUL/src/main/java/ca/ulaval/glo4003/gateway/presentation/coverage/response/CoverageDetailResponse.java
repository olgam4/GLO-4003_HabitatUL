package ca.ulaval.glo4003.gateway.presentation.coverage.response;

import ca.ulaval.glo4003.shared.domain.money.Amount;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"coverage", "amount"})
public class CoverageDetailResponse {
  private String coverage;
  private Amount amount;

  private CoverageDetailResponse() {}

  public CoverageDetailResponse(String coverage, Amount amount) {
    this.coverage = coverage;
    this.amount = amount;
  }

  public String getCoverage() {
    return coverage;
  }

  public Amount getAmount() {
    return amount;
  }
}
