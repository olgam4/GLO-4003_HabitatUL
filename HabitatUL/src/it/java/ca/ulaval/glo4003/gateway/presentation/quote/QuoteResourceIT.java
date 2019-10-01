package ca.ulaval.glo4003.gateway.presentation.quote;

import ca.ulaval.glo4003.gateway.presentation.ResourceConfigBuilder;
import ca.ulaval.glo4003.generator.quote.QuoteGenerator;
import ca.ulaval.glo4003.underwriting.application.quote.QuoteAppService;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import org.glassfish.jersey.server.ResourceConfig;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import static ca.ulaval.glo4003.gateway.presentation.RestITestHelper.*;
import static ca.ulaval.glo4003.gateway.presentation.quote.QuoteResource.PURCHASE_ROUTE;
import static ca.ulaval.glo4003.gateway.presentation.quote.QuoteResource.QUOTE_PATH;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuoteResourceIT {
  @Mock private QuoteAppService quoteAppService;

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
    QuoteResource quoteResource = new QuoteResource(quoteAppService, new QuoteViewAssembler());
    ResourceConfig resourceConfig =
        ResourceConfigBuilder.aResourceConfig().withResource(quoteResource).build();
    addResourceConfig(resourceConfig);
    quoteDto = QuoteGenerator.createValidQuoteDto();
    when(quoteAppService.requestQuote(any())).thenReturn(quoteDto);
  }

  @Test
  public void postingQuotePath_withValidRequest_shouldHaveExpectedStatusCode() {
    JSONObject request = QuoteRequestBuilder.aQuoteRequest().build();

    int expectedStatusCode = Response.Status.CREATED.getStatusCode();
    getBaseScenario()
        .given()
        .body(request.toString())
        .when()
        .post(QUOTE_PATH)
        .then()
        .statusCode(expectedStatusCode);
  }

  @Test
  public void postingQuotePath_withValidRequest_shouldProvideLocationCreatedQuote() {
    JSONObject request = QuoteRequestBuilder.aQuoteRequest().build();

    String expectedLocation = toUri(QUOTE_PATH, quoteDto.getQuoteId().getValue().toString());
    getBaseScenario()
        .given()
        .body(request.toString())
        .when()
        .post(QUOTE_PATH)
        .then()
        .header(HttpHeaders.LOCATION, expectedLocation);
  }

  @Test
  public void postingPurchaseQuotePath_withValidQuoteId_shouldHaveExpectedStatusCode() {
    String path = toPath(QUOTE_PATH, PURCHASE_ROUTE, quoteDto.getQuoteId().getValue().toString());

    int expectedStatusCode = Response.Status.OK.getStatusCode();
    getBaseScenario().when().post(path).then().statusCode(expectedStatusCode);
  }
}
