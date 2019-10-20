package ca.ulaval.glo4003.gateway.presentation.quote.request;

import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiabilityAmount;

public class CivilLiabilityRequest {
  private CivilLiabilityAmount coverageAmount;

  private CivilLiabilityRequest() {}

  public CivilLiabilityRequest(CivilLiabilityAmount coverageAmount) {
    this.coverageAmount = coverageAmount;
  }

  public CivilLiabilityAmount getCoverageAmount() {
    return coverageAmount;
  }
}
