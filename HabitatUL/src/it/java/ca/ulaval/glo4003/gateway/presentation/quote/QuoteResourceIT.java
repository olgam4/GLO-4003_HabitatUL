package ca.ulaval.glo4003.gateway.presentation.quote;

import ca.ulaval.glo4003.administration.application.user.UserAppService;
import ca.ulaval.glo4003.gateway.presentation.RequestBodyGenerator;
import ca.ulaval.glo4003.gateway.presentation.ResourceConfigBuilder;
import ca.ulaval.glo4003.gateway.presentation.common.filter.AuthFilterBuilder;
import ca.ulaval.glo4003.helper.quote.QuoteGenerator;
import ca.ulaval.glo4003.underwriting.application.quote.QuoteAppService;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteFormDto;
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
  private static final QuoteDto QUOTE_DTO = QuoteGenerator.createQuoteDto();
  private static final String QUOTE_ID_REPRESENTATION = QUOTE_DTO.getQuoteId().toRepresentation();

  @Mock private QuoteAppService quoteAppService;
  @Mock private UserAppService userAppService;

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
    when(quoteAppService.requestQuote(any(QuoteFormDto.class))).thenReturn(QUOTE_DTO);
    QuoteResource quoteResource =
        new QuoteResource(quoteAppService, userAppService, new QuoteViewAssembler());
    ResourceConfig resourceConfig =
        ResourceConfigBuilder.aResourceConfig()
            .withResource(quoteResource)
            .withRequestFilter(AuthFilterBuilder.anAuthFilter().build())
            .build();
    addResourceConfig(resourceConfig);
  }

  @After
  public void tearDown() {
    resetServer();
  }

  @Test
  public void creatingQuote_shouldHaveExpectedStatusCode() {
    JSONObject request = RequestBodyGenerator.createQuoteRequestBody();

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
  public void creatingQuote_shouldProvideLocationCreatedQuote() {
    JSONObject request = RequestBodyGenerator.createQuoteRequestBody();

    String expectedLocation = toUri(QUOTE_ROUTE, QUOTE_ID_REPRESENTATION);
    getBaseScenario()
        .given()
        .body(request.toString())
        .when()
        .post(QUOTE_ROUTE)
        .then()
        .header(HttpHeaders.LOCATION, expectedLocation);
  }

  @Test
  public void creatingQuote_shouldProvideProperlyFormattedResponse() {
    JSONObject request = RequestBodyGenerator.createQuoteRequestBody();

    getBaseScenario()
        .given()
        .body(request.toString())
        .when()
        .post(QUOTE_ROUTE)
        .then()
        .body(matchesJsonSchema("quote/QuoteResponse"));
  }

  @Test
  public void purchasingQuote_shouldHaveExpectedStatusCode() {
    String route = toPath(QUOTE_ROUTE, QUOTE_ID_REPRESENTATION, PURCHASE_ROUTE);

    int expectedStatusCode = Response.Status.OK.getStatusCode();
    getBaseScenario().when().post(route).then().statusCode(expectedStatusCode);
  }
}
