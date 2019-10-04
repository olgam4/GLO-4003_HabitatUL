package ca.ulaval.glo4003.underwriting.application.quote;

import ca.ulaval.glo4003.generator.premium.PremiumGenerator;
import ca.ulaval.glo4003.generator.quote.form.QuoteFormGenerator;
import ca.ulaval.glo4003.shared.domain.ClockProvider;
import ca.ulaval.glo4003.shared.domain.Date;
import ca.ulaval.glo4003.shared.infrastructure.FixedClockProvider;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteFormDto;
import ca.ulaval.glo4003.underwriting.application.quote.exception.InvalidEffectiveDateException;
import ca.ulaval.glo4003.underwriting.domain.QuotePremiumCalculator;
import ca.ulaval.glo4003.underwriting.domain.premium.Premium;
import ca.ulaval.glo4003.underwriting.domain.quote.Quote;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteFactory;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteRepository;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.Period;

import static ca.ulaval.glo4003.matcher.quote.QuoteDtoMatcher.matchesQuoteDto;
import static ca.ulaval.glo4003.matcher.quote.QuoteFormMatcher.mockitoQuoteFormMatcher;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class QuoteAppServiceTest {
  private static final Premium A_PREMIUM = PremiumGenerator.create();
  private static final QuoteId QUOTE_ID = new QuoteId();

  @Mock private QuotePremiumCalculator quotePremiumCalculator;
  @Mock private Quote quote;
  @Mock private QuoteFactory quoteFactory;
  @Mock private QuoteRepository quoteRepository;

  private QuoteAppService subject;
  private QuoteAssembler quoteAssembler;
  private ClockProvider clockProvider;
  private QuoteFormDto quoteFormDto;

  @Before
  public void setUp() {
    quoteAssembler = new QuoteAssembler();
    clockProvider = new FixedClockProvider();
    quoteFormDto = QuoteFormGenerator.createQuoteFormDto();
    when(quote.getQuoteId()).thenReturn(QUOTE_ID);
    when(quotePremiumCalculator.computeQuotePremium(any(QuoteForm.class))).thenReturn(A_PREMIUM);
    when(quoteFactory.create(any(Premium.class), any(QuoteForm.class))).thenReturn(quote);
    when(quoteRepository.getById(any(QuoteId.class))).thenReturn(quote);

    subject =
        new QuoteAppService(
            quoteAssembler, quotePremiumCalculator, quoteFactory, quoteRepository, clockProvider);
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

    assertThat(observedQuoteDto, matchesQuoteDto(quote));
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

  @Test(expected = InvalidEffectiveDateException.class)
  public void requestingQuoteWithEffectiveDateInThePast_shouldThrow() {
    LocalDate date =
        LocalDate.now(clockProvider.getClock())
            .minus(Period.ofYears(Faker.instance().number().randomDigitNotZero()));
    Date invalidEffectiveDate = Date.from(date);

    quoteFormDto = QuoteFormGenerator.createQuoteFormDtoWithEffectiveDate(invalidEffectiveDate);

    subject.requestQuote(quoteFormDto);
  }

  @Test(expected = InvalidEffectiveDateException.class)
  public void requestingQuoteWithEffectiveDateTooFarInTheFuture_shouldThrow() {
    LocalDate date =
        LocalDate.now(clockProvider.getClock())
            .plus(Period.ofYears(Faker.instance().number().numberBetween(2, 10)));
    Date invalidEffectiveDate = Date.from(date);
    quoteFormDto = QuoteFormGenerator.createQuoteFormDtoWithEffectiveDate(invalidEffectiveDate);

    subject.requestQuote(quoteFormDto);
  }
}
