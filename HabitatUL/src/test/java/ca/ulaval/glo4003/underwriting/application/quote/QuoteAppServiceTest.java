package ca.ulaval.glo4003.underwriting.application.quote;

import ca.ulaval.glo4003.coverage.application.CoverageAppService;
import ca.ulaval.glo4003.coverage.application.CoverageDto;
import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.coverage.helper.CoverageGenerator;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.RequestQuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.error.CouldNotRequestQuoteError;
import ca.ulaval.glo4003.underwriting.application.quote.error.QuoteNotFoundError;
import ca.ulaval.glo4003.underwriting.domain.quote.Quote;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteFactory;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteRepository;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteAlreadyCreatedException;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteNotFoundException;
import ca.ulaval.glo4003.underwriting.helper.quote.QuoteGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.underwriting.matcher.QuoteMatcher.matchesQuoteDto;
import static ca.ulaval.glo4003.underwriting.matcher.QuoteMatcher.matchesQuoteForm;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

@RunWith(MockitoJUnitRunner.class)
public class QuoteAppServiceTest {
  private static final QuoteId QUOTE_ID = QuoteGenerator.createQuoteId();
  private static final RequestQuoteDto QUOTE_FORM_DTO = QuoteGenerator.createRequestQuoteDto();
  private static final CoverageDto COVERAGE_DTO = CoverageGenerator.createCoverageDto();

  @Mock private CoverageAppService coverageAppService;
  @Mock private Quote quote;
  @Mock private QuoteFactory quoteFactory;
  @Mock private QuoteRepository quoteRepository;

  private QuoteAppService subject;
  private QuoteAssembler quoteAssembler;

  @Before
  public void setUp() throws QuoteNotFoundException {
    quoteAssembler = new QuoteAssembler();
    when(quote.getQuoteId()).thenReturn(QUOTE_ID);
    when(coverageAppService.requestQuoteCoverage(any(QuoteForm.class))).thenReturn(COVERAGE_DTO);
    when(quoteFactory.create(
            any(QuoteForm.class), any(CoverageDetails.class), any(PremiumDetails.class)))
        .thenReturn(quote);
    when(quoteRepository.getById(any(QuoteId.class))).thenReturn(quote);

    subject =
        new QuoteAppServiceImpl(quoteAssembler, coverageAppService, quoteFactory, quoteRepository);
  }

  @Test
  public void requestingQuote_shouldRequestQuoteCoverage() {
    subject.requestQuote(QUOTE_FORM_DTO);

    verify(coverageAppService).requestQuoteCoverage(argThat(matchesQuoteForm(QUOTE_FORM_DTO)));
  }

  @Test
  public void requestingQuote_shouldCreateQuote() throws QuoteAlreadyCreatedException {
    subject.requestQuote(QUOTE_FORM_DTO);

    verify(quoteRepository).create(quote);
  }

  @Test
  public void requestingQuote_shouldProduceCorrespondingQuoteDto() {
    QuoteDto quoteDto = subject.requestQuote(QUOTE_FORM_DTO);

    assertThat(quoteDto, matchesQuoteDto(quote));
  }

  @Test(expected = CouldNotRequestQuoteError.class)
  public void requestingQuote_withQuoteAlreadyCreated_shouldThrow()
      throws QuoteAlreadyCreatedException {
    doThrow(QuoteAlreadyCreatedException.class).when(quoteRepository).create(any(Quote.class));

    subject.requestQuote(QUOTE_FORM_DTO);
  }

  @Test
  public void purchasingQuote_shouldGetQuoteById() throws QuoteNotFoundException {
    subject.purchaseQuote(QUOTE_ID);

    verify(quoteRepository).getById(QUOTE_ID);
  }

  @Test
  public void purchasingQuote_shouldPurchaseQuote() {
    subject.purchaseQuote(QUOTE_ID);

    verify(quote).purchase();
  }

  @Test
  public void purchasingQuote_shouldUpdateQuote() throws QuoteNotFoundException {
    subject.purchaseQuote(QUOTE_ID);

    verify(quoteRepository).update(quote);
  }

  @Test(expected = QuoteNotFoundError.class)
  public void purchasingQuote_withNotExistingQuote_shouldThrow() throws QuoteNotFoundException {
    when(quoteRepository.getById(QUOTE_ID)).thenThrow(QuoteNotFoundException.class);

    subject.purchaseQuote(QUOTE_ID);
  }
}
