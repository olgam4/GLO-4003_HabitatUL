package ca.ulaval.glo4003.helper.quote.form;

import ca.ulaval.glo4003.gateway.presentation.quote.request.CivilLiabilityRequest;
import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiability;

import static ca.ulaval.glo4003.helper.calculator.QuotePremiumInputGenerator.createCivilLiabilityLimit;

public class CivilLiabilityGenerator {
  private CivilLiabilityGenerator() {}

  public static CivilLiabilityRequest createCivilLiabilityRequest() {
    return new CivilLiabilityRequest(createCivilLiabilityLimit());
  }

  public static CivilLiability createCivilLiability() {
    return new CivilLiability(createCivilLiabilityLimit());
  }
}
