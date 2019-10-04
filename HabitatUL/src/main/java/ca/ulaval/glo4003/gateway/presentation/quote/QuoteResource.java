package ca.ulaval.glo4003.gateway.presentation.quote;

import ca.ulaval.glo4003.gateway.presentation.annotation.Secured;
import ca.ulaval.glo4003.gateway.presentation.quote.request.QuoteRequest;
import ca.ulaval.glo4003.management.application.user.UserAppService;
import ca.ulaval.glo4003.underwriting.application.quote.QuoteAppService;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteFormDto;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;

import static ca.ulaval.glo4003.Server.CONTEXT_PATH;

@Path(QuoteResource.QUOTE_ROUTE)
@Produces(MediaType.APPLICATION_JSON)
public class QuoteResource {
  public static final String QUOTE_ROUTE = "/quotes";
  public static final String PURCHASE_ROUTE = "/purchase";
  private static final String QUOTE_ID_PARAM_NAME = "quoteId";

  private QuoteAppService quoteAppService;
  private QuoteViewAssembler quoteViewAssembler;
  private UserAppService userAppService;

  public QuoteResource() {
    this(new QuoteAppService(), new QuoteViewAssembler(), new UserAppService());
  }

  public QuoteResource(
      QuoteAppService quoteAppService,
      QuoteViewAssembler quoteViewAssembler,
      UserAppService userAppService) {
    this.quoteAppService = quoteAppService;
    this.quoteViewAssembler = quoteViewAssembler;
    this.userAppService = userAppService;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response requestQuote(QuoteRequest quoteRequest) {
    QuoteFormDto quoteFormDto = quoteViewAssembler.from(quoteRequest);
    QuoteDto quoteDto = quoteAppService.requestQuote(quoteFormDto);
    String quoteIdString = quoteDto.getQuoteId().getValue().toString();
    // TODO: create class to encapsulate the context path - quite easy to forget
    URI location = UriBuilder.fromPath(CONTEXT_PATH).path(QUOTE_ROUTE).path(quoteIdString).build();
    return Response.created(location).entity(quoteViewAssembler.from(quoteDto)).build();
  }

  @POST
  @Secured
  @Path("/{" + QUOTE_ID_PARAM_NAME + "}" + PURCHASE_ROUTE)
  public Response purchaseQuote(
      @Context SecurityContext securityContext, @PathParam(QUOTE_ID_PARAM_NAME) QuoteId quoteId) {
    quoteAppService.purchaseQuote(quoteId);
    String userKey = securityContext.getUserPrincipal().getName();
    String quoteKey = quoteId.getValue().toString();
    userAppService.associateQuote(userKey, quoteKey);
    return Response.ok().build();
  }
}
