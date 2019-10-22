package ca.ulaval.glo4003.gateway.presentation.quote;

import ca.ulaval.glo4003.administration.application.user.UserAppService;
import ca.ulaval.glo4003.gateway.presentation.ResourceConfigBuilder;
import ca.ulaval.glo4003.gateway.presentation.common.filter.AuthFilterBuilder;
import ca.ulaval.glo4003.helper.quote.QuoteGenerator;
import ca.ulaval.glo4003.underwriting.application.quote.QuoteAppService;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import org.glassfish.jersey.server.ResourceConfig;
import org.json.JSONObject;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import static ca.ulaval.glo4003.gateway.presentation.RestITestHelper.*;
import static ca.ulaval.glo4003.gateway.presentation.quote.QuoteResource.PURCHASE_ROUTE;
import static ca.ulaval.glo4003.gateway.presentation.quote.QuoteResource.QUOTE_ROUTE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuoteResourceIT {
  @Mock private QuoteAppService quoteAppService;
  @Mock private UserAppService userAppService;

  private QuoteDto quoteDto;

  @BeforeClass
  public static void setUpClass() {
    startServer();
  }

  @AfterClass
  public static void tearDownClass() {
    stopServer();
  }

  @Before
  public void setUp() {
    QuoteResource quoteResource =
        new QuoteResource(quoteAppService, new QuoteViewAssembler(), userAppService);
    quoteDto = QuoteGenerator.createValidQuoteDto();
    when(quoteAppService.requestQuote(any())).thenReturn(quoteDto);
    registerResource(quoteResource);
  }

  @After
  public void tearDown() {
    resetServer();
  }

  private void registerResource(QuoteResource quoteResource) {
    ResourceConfig resourceConfig =
        ResourceConfigBuilder.aResourceConfig()
            .withResource(quoteResource)
            .withRequestFilter(AuthFilterBuilder.anAuthFilter().build())
            .build();
    addResourceConfig(resourceConfig);
  }

  @Test
  public void postingQuotePath_withValidRequest_shouldHaveExpectedStatusCode() {
    JSONObject request = QuoteRequestBuilder.aQuoteRequest().build();

    int expectedStatusCode = Response.Status.CREATED.getStatusCode();
    getBaseScenario()
        .given()
        .body(request.toString())
        .when()
        .post(QUOTE_ROUTE)
        .then()
        .statusCode(expectedStatusCode);
  }

  @Test
  public void postingQuotePath_withValidRequest_shouldProvideLocationCreatedQuote() {
    JSONObject request = QuoteRequestBuilder.aQuoteRequest().build();

    String expectedLocation = toUri(QUOTE_ROUTE, quoteDto.getQuoteId().toRepresentation());
    getBaseScenario()
        .given()
        .body(request.toString())
        .when()
        .post(QUOTE_ROUTE)
        .then()
        .header(HttpHeaders.LOCATION, expectedLocation);
  }

  @Test
  public void postingQuotePath_withValidRequest_shouldProvideProperlyFormattedResponse() {
    JSONObject request = QuoteRequestBuilder.aQuoteRequest().build();

    getBaseScenario()
        .given()
        .body(request.toString())
        .when()
        .post(QUOTE_ROUTE)
        .then()
        .body(matchesJsonSchema("quote/QuoteResponse"));
  }

  @Test
  public void postingPurchaseQuotePath_withValidQuoteId_shouldHaveExpectedStatusCode() {
    String path = toPath(QUOTE_ROUTE, quoteDto.getQuoteId().toRepresentation(), PURCHASE_ROUTE);

    int expectedStatusCode = Response.Status.OK.getStatusCode();
    getBaseScenario().when().post(path).then().statusCode(expectedStatusCode);
  }
}
