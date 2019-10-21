package ca.ulaval.glo4003.underwriting.application.quote;

import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteCoverageOverviewDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteFormDto;
import ca.ulaval.glo4003.underwriting.domain.quote.Quote;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;

public class QuoteAssembler {
  public QuoteForm from(QuoteFormDto quoteFormDto) {
    return new QuoteForm(
        quoteFormDto.getIdentity(),
        quoteFormDto.getLocation(),
        quoteFormDto.getEffectiveDate(),
        quoteFormDto.getBuilding(),
        quoteFormDto.getPersonalProperty(),
        quoteFormDto.getCivilLiability());
  }

  public QuoteDto from(Quote quote) {
    return new QuoteDto(
        quote.getQuoteId(),
        quote.getPrice(),
        quote.getEffectivePeriod(),
        quote.getExpirationDate(),
        from(quote.getQuoteForm()));
  }

  private QuoteCoverageOverviewDto from(QuoteForm quoteForm) {
    Amount personalPropertyAmount = quoteForm.getPersonalProperty().getCoverageAmount();
    Amount civilLiabilityAmount = quoteForm.getCivilLiability().getCoverageAmount();
    return new QuoteCoverageOverviewDto(personalPropertyAmount, civilLiabilityAmount);
  }
}
