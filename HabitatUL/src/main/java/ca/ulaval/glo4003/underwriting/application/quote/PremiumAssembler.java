package ca.ulaval.glo4003.underwriting.application.quote;

import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuotePremiumInput;

public class PremiumAssembler {
  // TODO: should be moved into calculator
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
}
