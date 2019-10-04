package ca.ulaval.glo4003.gateway.presentation.quote;

import ca.ulaval.glo4003.gateway.presentation.quote.request.QuoteRequest;
import ca.ulaval.glo4003.generator.SecurityContextGenerator;
import ca.ulaval.glo4003.generator.quote.QuoteGenerator;
import ca.ulaval.glo4003.generator.quote.form.QuoteFormGenerator;
import ca.ulaval.glo4003.management.application.user.UserAppService;
import ca.ulaval.glo4003.management.domain.user.QuoteKey;
import ca.ulaval.glo4003.management.domain.user.UserId;
import ca.ulaval.glo4003.underwriting.application.quote.QuoteAppService;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.SecurityContext;

import static ca.ulaval.glo4003.matcher.quote.QuoteFormDtoMatcher.mockitoQuoteFormDtoMatcher;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuoteResourceTest {
  private static final QuoteId QUOTE_ID = new QuoteId();
  private static final SecurityContext SECURITY_CONTEXT =
      SecurityContextGenerator.createSecurityContext();

  @Mock private QuoteAppService quoteAppService;
  @Mock private UserAppService userAppService;

  private QuoteResource subject;
  private QuoteRequest quoteRequest;
  private QuoteViewAssembler quoteViewAssembler;
  private QuoteDto quoteDto;

  @Before
  public void setUp() {
    quoteRequest = QuoteFormGenerator.createQuoteRequest();
    quoteViewAssembler = new QuoteViewAssembler();
    quoteDto = QuoteGenerator.createValidQuoteDto();
    subject = new QuoteResource(quoteAppService, quoteViewAssembler, userAppService);
    when(quoteAppService.requestQuote(any())).thenReturn(quoteDto);
  }

  @Test
  public void requestingQuote_shouldDelegateToQuoteAppService() {
    subject.requestQuote(quoteRequest);

    verify(quoteAppService).requestQuote(mockitoQuoteFormDtoMatcher(quoteRequest));
  }

  @Test
  public void purchasingQuote_shouldDelegateToQuoteAppService() {
    subject.purchaseQuote(SECURITY_CONTEXT, QUOTE_ID);

    verify(quoteAppService).purchaseQuote(QUOTE_ID);
  }

  @Test
  public void purchasingQuote_shouldAssociateQuoteToUser() {
    UserId userId = new UserId(SECURITY_CONTEXT.getUserPrincipal().getName());
    QuoteKey quoteKey = new QuoteKey(QUOTE_ID.getValue().toString());

    subject.purchaseQuote(SECURITY_CONTEXT, QUOTE_ID);

    verify(userAppService).associateQuote(userId, quoteKey);
  }
}
