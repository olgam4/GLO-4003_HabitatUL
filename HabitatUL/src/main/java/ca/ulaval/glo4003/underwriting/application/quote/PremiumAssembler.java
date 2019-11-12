package ca.ulaval.glo4003.underwriting.application.quote;

import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;

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
}
