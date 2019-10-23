package ca.ulaval.glo4003.helper.quote.form;

import ca.ulaval.glo4003.gateway.presentation.quote.request.CivilLiabilityRequest;
import ca.ulaval.glo4003.helper.EnumSampler;
import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiability;
import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiabilityLimit;

public class CivilLiabilityGenerator {
  public static CivilLiabilityRequest createCivilLiabilityRequest() {
    return new CivilLiabilityRequest(createCivilLiabilityLimit());
  }

  public static CivilLiability createCivilLiability() {
    return new CivilLiability(createCivilLiabilityLimit());
  }

  public static CivilLiabilityLimit createCivilLiabilityLimit() {
    return EnumSampler.sample(CivilLiabilityLimit.class);
  }
}
