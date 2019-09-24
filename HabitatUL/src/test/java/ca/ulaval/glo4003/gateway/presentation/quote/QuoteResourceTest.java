package ca.ulaval.glo4003.gateway.presentation.quote;

import ca.ulaval.glo4003.gateway.presentation.quote.view.request.QuoteRequestView;
import ca.ulaval.glo4003.generator.QuoteGenerator;
import ca.ulaval.glo4003.generator.QuoteRequestGenerator;
import ca.ulaval.glo4003.matcher.QuoteRequestDtoMatcher;
import ca.ulaval.glo4003.underwriting.application.quote.QuoteAppService;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuoteResourceTest {
  @Mock private QuoteAppService quoteAppService;

  private QuoteResource subject;
  private QuoteRequestView quoteRequestView;
  private QuoteViewAssembler quoteViewAssembler;
  private QuoteDto quoteDto;

  @Before
  public void setUp() {
    quoteRequestView = QuoteRequestGenerator.createQuoteRequestView();
    quoteViewAssembler = new QuoteViewAssembler();
    quoteDto = QuoteGenerator.createValidQuoteDto();
    subject = new QuoteResource(quoteAppService, quoteViewAssembler);
    when(quoteAppService.requestQuote(any())).thenReturn(quoteDto);
  }

  @Test
  public void requestingQuote_shouldCallQuoteAppService() {
    subject.requestQuote(quoteRequestView);

    verify(quoteAppService)
        .requestQuote(QuoteRequestDtoMatcher.getQuoteRequestDtoMockitoMatcher(quoteRequestView));
  }

  @Test
  public void requestingQuote_shouldHaveExpectedStatus() {
    Response response = subject.requestQuote(quoteRequestView);

    int expectedStatus = Response.Status.CREATED.getStatusCode();
    assertEquals(expectedStatus, response.getStatus());
  }

  @Test
  public void requestingQuote_shouldIndicateLocationCreatedQuote() {
    Response response = subject.requestQuote(quoteRequestView);

    List<String> locationParts =
        Arrays.asList(QuoteResource.QUOTE_PATH, quoteDto.getQuoteId().getValue().toString());
    String expectedLocation = String.join("/", locationParts);
    assertEquals(expectedLocation, response.getHeaderString("location"));
  }
}
