package ca.ulaval.glo4003.coverage.application.premium.assembler;

import ca.ulaval.glo4003.coverage.domain.form.BicycleEndorsementForm;
import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BicycleEndorsementPremiumInput;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuotePremiumInput;

public class PremiumAssembler {
  public QuotePremiumInput toQuotePremiumInput(QuoteForm quoteForm) {
    return new QuotePremiumInput(
        quoteForm.getPersonalInformation().getGender(),
        quoteForm.getPersonalInformation().getUniversityProfile().getProgram(),
        quoteForm.getAdditionalInsured().getGender(),
        quoteForm.getAdditionalInsured().getUniversityProfile().getProgram(),
        quoteForm.getPersonalProperty().getAnimals(),
        quoteForm.getPersonalProperty().getBicycle().getPrice(),
        quoteForm.getCivilLiability().getLimit());
  }

  public BicycleEndorsementPremiumInput toBicycleEndorsementPremiumInput(QuoteForm quoteForm) {
    return new BicycleEndorsementPremiumInput(
        quoteForm.getPersonalProperty().getBicycle().getPrice());
  }

  public BicycleEndorsementPremiumInput toBicycleEndorsementPremiumInput(
      BicycleEndorsementForm bicycleEndorsementForm) {
    return new BicycleEndorsementPremiumInput(bicycleEndorsementForm.getBicycle().getPrice());
  }
}
