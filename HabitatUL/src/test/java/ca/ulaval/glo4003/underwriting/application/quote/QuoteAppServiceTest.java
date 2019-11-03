package ca.ulaval.glo4003.underwriting.application.quote;

import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.helper.quote.form.QuoteFormGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteFormDto;
import ca.ulaval.glo4003.underwriting.application.quote.error.CouldNotRequestQuoteError;
import ca.ulaval.glo4003.underwriting.application.quote.error.QuoteNotFoundError;
import ca.ulaval.glo4003.underwriting.domain.quote.Quote;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteFactory;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteRepository;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteAlreadyCreatedException;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteNotFoundException;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.matcher.QuoteMatcher.matchesQuoteDto;
import static ca.ulaval.glo4003.matcher.QuoteMatcher.matchesQuoteForm;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

@RunWith(MockitoJUnitRunner.class)
public class QuoteAppServiceTest {
  private static final Money A_PRICE = MoneyGenerator.createMoney();
  private static final QuoteId QUOTE_ID = new QuoteId();

  @Mock private QuoteFormValidator quoteFormValidator;
  @Mock private QuotePriceCalculator quotePriceCalculator;
  @Mock private Quote quote;
  @Mock private QuoteFactory quoteFactory;
  @Mock private QuoteRepository quoteRepository;

  private QuoteAppService subject;
  private QuoteFormDto quoteFormDto;
  private QuoteAssembler quoteAssembler;

  @Before
  public void setUp() throws QuoteNotFoundException {
    quoteAssembler = new QuoteAssembler();
    quoteFormDto = QuoteFormGenerator.createQuoteFormDto();
    when(quote.getQuoteId()).thenReturn(QUOTE_ID);
    when(quote.getQuoteForm()).thenReturn(QuoteFormGenerator.createQuoteForm());
    when(quotePriceCalculator.compute(any(QuoteForm.class))).thenReturn(A_PRICE);
    when(quoteFactory.create(any(Money.class), any(QuoteForm.class))).thenReturn(quote);
    when(quoteRepository.getById(any(QuoteId.class))).thenReturn(quote);

    subject =
        new QuoteAppService(
            quoteAssembler,
            quoteFormValidator,
            quotePriceCalculator,
            quoteFactory,
            quoteRepository);
  }

  @Test
  public void requestingQuote_shouldValidateQuoteForm() {
    subject.requestQuote(quoteFormDto);

    verify(quoteFormValidator).validate(argThat(matchesQuoteForm(quoteFormDto)));
  }

  @Test
  public void requestingQuote_shouldComputeQuotePrice() {
    subject.requestQuote(quoteFormDto);

    verify(quotePriceCalculator).compute(argThat(matchesQuoteForm(quoteFormDto)));
  }

  @Test
  public void requestingQuote_shouldCreateQuote() throws QuoteAlreadyCreatedException {
    subject.requestQuote(quoteFormDto);

    verify(quoteRepository).create(quote);
  }

  @Test
  public void requestingQuote_shouldProduceCorrespondingQuoteDto() {
    QuoteDto observedQuoteDto = subject.requestQuote(quoteFormDto);

    assertThat(observedQuoteDto, matchesQuoteDto(quote));
  }

  @Test(expected = CouldNotRequestQuoteError.class)
  public void requestingQuote_withQuoteAlreadyCreated_shouldThrow()
      throws QuoteAlreadyCreatedException {
    Mockito.doThrow(QuoteAlreadyCreatedException.class).when(quoteRepository).create(quote);

    subject.requestQuote(quoteFormDto);
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
