package ca.ulaval.glo4003.gateway.presentation.quote;

import ca.ulaval.glo4003.gateway.presentation.quote.request.QuoteRequest;
import ca.ulaval.glo4003.underwriting.application.quote.QuoteAppService;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteFormDto;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static ca.ulaval.glo4003.Server.CONTEXT_PATH;

@Path(QuoteResource.QUOTE_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class QuoteResource {
  public static final String QUOTE_PATH = "/quotes";
  public static final String PURCHASE_ROUTE = "/purchase";
  private static final String QUOTE_ID_PARAM_NAME = "quoteId";

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
  public Response requestQuote(QuoteRequest quoteRequest) {
    QuoteFormDto quoteFormDto = quoteViewAssembler.from(quoteRequest);
    QuoteDto quoteDto = quoteAppService.requestQuote(quoteFormDto);
    String quoteIdString = quoteDto.getQuoteId().getValue().toString();
    // TODO: create class to encapsulate the context path - quite easy to forget
    URI location = UriBuilder.fromPath(CONTEXT_PATH).path(QUOTE_PATH).path(quoteIdString).build();
    return Response.created(location).build();
  }

  @POST
  @Path(PURCHASE_ROUTE + "/{" + QUOTE_ID_PARAM_NAME + "}")
  public Response purchaseQuote(@PathParam(QUOTE_ID_PARAM_NAME) QuoteId quoteId) {
    quoteAppService.purchaseQuote(quoteId);
    return Response.ok().build();
  }
}
