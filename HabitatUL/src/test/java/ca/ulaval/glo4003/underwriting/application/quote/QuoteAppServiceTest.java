package ca.ulaval.glo4003.underwriting.application.quote;

import ca.ulaval.glo4003.generator.premium.PremiumGenerator;
import ca.ulaval.glo4003.generator.quote.form.QuoteFormGenerator;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteFormDto;
import ca.ulaval.glo4003.underwriting.domain.QuotePremiumCalculator;
import ca.ulaval.glo4003.underwriting.domain.premium.Premium;
import ca.ulaval.glo4003.underwriting.domain.quote.Quote;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteFactory;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteRepository;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.matcher.quote.QuoteDtoMatcher.matchesQuote;
import static ca.ulaval.glo4003.matcher.quote.QuoteFormMatcher.mockitoQuoteFormMatcher;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class QuoteAppServiceTest {
  private static final Premium A_PREMIUM = PremiumGenerator.create();
  private static final QuoteId QUOTE_ID = new QuoteId();

  private QuoteAppService subject;
  private QuoteFormDto quoteFormDto;

  @Mock private QuotePremiumCalculator quotePremiumCalculator;
  @Mock private Quote quote;
  @Mock private QuoteFactory quoteFactory;
  @Mock private QuoteRepository quoteRepository;

  @Before
  public void setUp() {
    quoteFormDto = QuoteFormGenerator.createQuoteFormDto();

    when(quotePremiumCalculator.computeQuotePremium(any(QuoteForm.class))).thenReturn(A_PREMIUM);
    when(quoteFactory.create(any(Premium.class), any(QuoteForm.class))).thenReturn(quote);
    when(quoteRepository.getById(any(QuoteId.class))).thenReturn(quote);

    subject = new QuoteAppService(quotePremiumCalculator, quoteFactory, quoteRepository);
  }

  @Test
  public void requestingQuote_shouldComputeQuotePrice() {
    subject.requestQuote(quoteFormDto);

    verify(quotePremiumCalculator).computeQuotePremium(mockitoQuoteFormMatcher(quoteFormDto));
  }

  @Test
  public void requestingQuote_shouldCreateQuote() {
    subject.requestQuote(quoteFormDto);

    verify(quoteRepository).create(quote);
  }

  @Test
  public void requestingQuote_shouldProduceCorrespondingQuoteDto() {
    QuoteDto observedQuoteDto = subject.requestQuote(quoteFormDto);

    assertThat(observedQuoteDto, matchesQuote(quote));
  }

  @Test
  public void purchasingQuote_shouldGetQuoteById() {
    subject.purchaseQuote(QUOTE_ID);

    verify(quoteRepository).getById(QUOTE_ID);
  }

  @Test
  public void purchasingQuote_shouldPurchaseQuote() {
    subject.purchaseQuote(QUOTE_ID);

    verify(quote).purchase();
  }

  @Test
  public void purchasingQuote_shouldUpdateQuote() {
    subject.purchaseQuote(QUOTE_ID);

    verify(quoteRepository).update(quote);
  }
}
