package ca.ulaval.glo4003.helper.calculator.form.civilliability;

import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiability;
import ca.ulaval.glo4003.gateway.presentation.quote.request.CivilLiabilityRequest;

import static ca.ulaval.glo4003.helper.calculator.premium.QuotePremiumInputGenerator.createCivilLiabilityLimit;

public class CivilLiabilityGenerator {
  private CivilLiabilityGenerator() {}

  public static CivilLiabilityRequest createCivilLiabilityRequest() {
    return new CivilLiabilityRequest(createCivilLiabilityLimit());
  }

  public static CivilLiability createCivilLiability() {
    return new CivilLiability(createCivilLiabilityLimit());
  }
}
