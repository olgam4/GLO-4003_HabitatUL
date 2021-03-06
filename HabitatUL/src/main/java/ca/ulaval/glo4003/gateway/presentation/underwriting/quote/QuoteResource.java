package ca.ulaval.glo4003.gateway.presentation.underwriting.quote;

import ca.ulaval.glo4003.administration.application.user.UserAppService;
import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.gateway.presentation.common.filter.annotation.Secured;
import ca.ulaval.glo4003.gateway.presentation.underwriting.quote.request.QuoteRequest;
import ca.ulaval.glo4003.underwriting.application.quote.QuoteAppService;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.RequestQuoteDto;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;

import static ca.ulaval.glo4003.Server.CONTEXT_PATH;

@Path(QuoteResource.QUOTE_ROUTE)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class QuoteResource {
  public static final String QUOTE_ROUTE = "/quotes";
  public static final String PURCHASE_ROUTE = "/purchase";
  private static final String QUOTE_ID_PARAM_NAME = "quoteId";
  private static final String SPECIFIC_QUOTE_ROUTE = "/{" + QUOTE_ID_PARAM_NAME + "}";
  private static final String PURCHASE_QUOTE_FULL_ROUTE = SPECIFIC_QUOTE_ROUTE + PURCHASE_ROUTE;

  private QuoteAppService quoteAppService;
  private UserAppService userAppService;
  private QuoteViewAssembler quoteViewAssembler;

  public QuoteResource() {
    this(
        ServiceLocator.resolve(QuoteAppService.class),
        ServiceLocator.resolve(UserAppService.class),
        new QuoteViewAssembler());
  }

  public QuoteResource(
      QuoteAppService quoteAppService,
      UserAppService userAppService,
      QuoteViewAssembler quoteViewAssembler) {
    this.quoteAppService = quoteAppService;
    this.userAppService = userAppService;
    this.quoteViewAssembler = quoteViewAssembler;
  }

  @POST
  public Response requestQuote(@Valid QuoteRequest quoteRequest) {
    RequestQuoteDto requestQuoteDto = quoteViewAssembler.from(quoteRequest);
    QuoteDto quoteDto = quoteAppService.requestQuote(requestQuoteDto);
    String quoteIdRepresentation = quoteDto.getQuoteId().toRepresentation();
    URI location =
        UriBuilder.fromPath(CONTEXT_PATH).path(QUOTE_ROUTE).path(quoteIdRepresentation).build();
    return Response.created(location).entity(quoteViewAssembler.from(quoteDto)).build();
  }

  @POST
  @Secured
  @Path(PURCHASE_QUOTE_FULL_ROUTE)
  public Response purchaseQuote(
      @Context SecurityContext securityContext, @PathParam(QUOTE_ID_PARAM_NAME) QuoteId quoteId) {
    String userKey = securityContext.getUserPrincipal().getName();
    String quoteKey = quoteId.toRepresentation();
    userAppService.associateQuote(userKey, quoteKey);
    quoteAppService.purchaseQuote(quoteId);
    return Response.ok().build();
  }
}
