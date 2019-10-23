package ca.ulaval.glo4003.gateway.presentation.quote.request;

import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiabilityLimit;

public class CivilLiabilityRequest {
  private CivilLiabilityLimit limit;

  private CivilLiabilityRequest() {}

  public CivilLiabilityRequest(CivilLiabilityLimit limit) {
    this.limit = limit;
  }

  public CivilLiabilityLimit getLimit() {
    return limit;
  }
}
