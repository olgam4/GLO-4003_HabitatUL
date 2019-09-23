package ca.ulaval.glo4003.underwriting.domain.quote;

public interface QuoteRepository {
  Quote getById(QuoteId quoteId);

  void create(Quote quote);

  void update(Quote quote);
}
