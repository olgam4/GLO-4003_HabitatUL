package ca.ulaval.glo4003.generator.quote.form;

import ca.ulaval.glo4003.gateway.presentation.quote.request.CivilLiabilityRequest;
import ca.ulaval.glo4003.generator.EnumSampler;
import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiability;
import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiabilityAmount;

public class CivilLiabilityGenerator {
  public static CivilLiabilityRequest createCivilLiabilityRequest() {
    return new CivilLiabilityRequest(createCivilLiabilityAmount());
  }

  public static CivilLiability createCivilLiability() {
    return new CivilLiability(createCivilLiabilityAmount());
  }

  public static CivilLiability createCivilLiabilityWithAmount(
      CivilLiabilityAmount civilLiabilityAmount) {
    return new CivilLiability(civilLiabilityAmount);
  }

  private static CivilLiabilityAmount createCivilLiabilityAmount() {
    return EnumSampler.sample(CivilLiabilityAmount.class);
  }
}
