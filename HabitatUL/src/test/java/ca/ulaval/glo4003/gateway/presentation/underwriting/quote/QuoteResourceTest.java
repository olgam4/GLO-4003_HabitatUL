package ca.ulaval.glo4003.gateway.presentation.underwriting.quote;

import ca.ulaval.glo4003.administration.application.user.UserAppService;
import ca.ulaval.glo4003.gateway.presentation.underwriting.quote.request.QuoteRequest;
import ca.ulaval.glo4003.shared.helper.SecurityContextGenerator;
import ca.ulaval.glo4003.underwriting.application.quote.QuoteAppService;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;
import ca.ulaval.glo4003.underwriting.helper.quote.QuoteGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.SecurityContext;

import static ca.ulaval.glo4003.underwriting.matcher.QuoteMatcher.matchesRequestQuoteDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

@RunWith(MockitoJUnitRunner.class)
public class QuoteResourceTest {
  private static final SecurityContext SECURITY_CONTEXT =
      SecurityContextGenerator.createSecurityContext();
  private static final QuoteId QUOTE_ID = QuoteGenerator.createQuoteId();

  @Mock private QuoteAppService quoteAppService;
  @Mock private UserAppService userAppService;

  private QuoteResource subject;
  private QuoteRequest quoteRequest;
  private QuoteViewAssembler quoteViewAssembler;
  private QuoteDto quoteDto;

  @Before
  public void setUp() {
    quoteRequest = QuoteGenerator.createQuoteRequest();
    quoteViewAssembler = new QuoteViewAssembler();
    quoteDto = QuoteGenerator.createQuoteDto();
    when(quoteAppService.requestQuote(any())).thenReturn(quoteDto);
    subject = new QuoteResource(quoteAppService, userAppService, quoteViewAssembler);
  }

  @Test
  public void requestingQuote_shouldDelegateToQuoteAppService() {
    subject.requestQuote(quoteRequest);

    verify(quoteAppService).requestQuote(argThat(matchesRequestQuoteDto(quoteRequest)));
  }

  @Test
  public void purchasingQuote_shouldDelegateToQuoteAppService() {
    subject.purchaseQuote(SECURITY_CONTEXT, QUOTE_ID);

    verify(quoteAppService).purchaseQuote(QUOTE_ID);
  }

  @Test
  public void purchasingQuote_shouldAssociateQuoteToUser() {
    String userKey = SECURITY_CONTEXT.getUserPrincipal().getName();
    String quoteKey = QUOTE_ID.toRepresentation();

    subject.purchaseQuote(SECURITY_CONTEXT, QUOTE_ID);

    verify(userAppService).associateQuote(userKey, quoteKey);
  }
}
