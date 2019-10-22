package ca.ulaval.glo4003.helper.quote.form;

import ca.ulaval.glo4003.gateway.presentation.quote.request.CivilLiabilityRequest;
import ca.ulaval.glo4003.helper.EnumSampler;
import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiability;
import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiabilityAmount;

public class CivilLiabilityGenerator {
  public static CivilLiabilityRequest createCivilLiabilityRequest() {
    return new CivilLiabilityRequest(createCivilLiabilityAmount());
  }

  public static CivilLiability createCivilLiability() {
    return new CivilLiability(createCivilLiabilityAmount());
  }

  public static CivilLiabilityAmount createCivilLiabilityAmount() {
    return EnumSampler.sample(CivilLiabilityAmount.class);
  }
}
