package ca.ulaval.glo4003.gateway.presentation.quote;

import ca.ulaval.glo4003.gateway.presentation.quote.view.request.QuoteRequestView;
import ca.ulaval.glo4003.underwriting.application.quote.QuoteAppService;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteRequestDto;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

@Path(QuoteResource.QUOTE_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class QuoteResource {
  public static final String QUOTE_PATH = "/quotes";

  private QuoteAppService quoteAppService;
  private QuoteViewAssembler quoteViewAssembler;

  public QuoteResource() {
    this(new QuoteAppService(), new QuoteViewAssembler());
  }

  public QuoteResource(QuoteAppService quoteAppService, QuoteViewAssembler quoteViewAssembler) {
    this.quoteAppService = quoteAppService;
    this.quoteViewAssembler = quoteViewAssembler;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response requestQuote(QuoteRequestView quoteRequestView) {
    QuoteRequestDto quoteRequestDto = quoteViewAssembler.from(quoteRequestView);
    QuoteDto quoteDto = quoteAppService.requestQuote(quoteRequestDto);
    QuoteId quoteId = quoteDto.getQuoteId();
    String quoteIdString = quoteId.getValue().toString();
    URI location = UriBuilder.fromResource(QuoteResource.class).path(quoteIdString).build();
    return Response.created(location).build();
  }
}
