package ca.ulaval.glo4003.helper.coverage.form.civilliability;

import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiability;
import ca.ulaval.glo4003.gateway.presentation.coverage.request.CivilLiabilityRequest;

import static ca.ulaval.glo4003.helper.coverage.premium.QuotePremiumInputGenerator.createCivilLiabilityLimit;

public class CivilLiabilityGenerator {
  private CivilLiabilityGenerator() {}

  public static CivilLiabilityRequest createCivilLiabilityRequest() {
    return new CivilLiabilityRequest(createCivilLiabilityLimit());
  }

  public static CivilLiability createCivilLiability() {
    return new CivilLiability(createCivilLiabilityLimit());
  }
}
