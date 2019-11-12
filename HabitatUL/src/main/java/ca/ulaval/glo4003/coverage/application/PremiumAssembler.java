package ca.ulaval.glo4003.coverage.application;

import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bike.BikePremiumInput;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuotePremiumInput;

public class PremiumAssembler {
  public QuotePremiumInput toQuotePremiumInput(QuoteForm quoteForm) {
    return new QuotePremiumInput(
        quoteForm.getPersonalInformation().getGender(),
        quoteForm.getPersonalInformation().getUniversityProfile().getProgram(),
        quoteForm.getAdditionalInsured().getGender(),
        quoteForm.getAdditionalInsured().getUniversityProfile().getProgram(),
        quoteForm.getPersonalProperty().getAnimals(),
        quoteForm.getPersonalProperty().getBike().getPrice(),
        quoteForm.getCivilLiability().getLimit());
  }

  public BikePremiumInput toBikePremiumInput(QuoteForm quoteForm) {
    return new BikePremiumInput(quoteForm.getPersonalProperty().getBike().getPrice());
  }
}
