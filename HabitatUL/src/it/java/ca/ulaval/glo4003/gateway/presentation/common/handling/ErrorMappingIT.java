package ca.ulaval.glo4003.gateway.presentation.common.handling;

import ca.ulaval.glo4003.gateway.presentation.ResourceConfigBuilder;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.gateway.presentation.RestITestHelper.*;
import static ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorThrowingResource.ERROR_ROUTE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public abstract class ErrorMappingIT {
  @Mock private ErrorThrowingAppService errorThrowingAppService;

  @BeforeClass
  public static void setUpClass() {
    startServer();
  }

  @AfterClass
  public static void tearDownClass() {
    stopServer();
  }

  @Before
  public void setUp() throws Exception {
    when(errorThrowingAppService.getError()).thenThrow(getError());
    ErrorThrowingResource errorThrowingResource =
        new ErrorThrowingResource(errorThrowingAppService);
    ResourceConfig resourceConfig =
        ResourceConfigBuilder.aResourceConfig()
            .withResource(errorThrowingResource)
            .withErrorMapper(CatchAllErrorMapper.class)
            .withErrorMapper(BaseErrorMapper.class)
            .withErrorMapper(DeserializationErrorMapper.class)
            .withErrorMapper(RouteNotFoundErrorMapper.class)
            .build();
    addResourceConfig(resourceConfig);
  }

  @After
  public void tearDown() {
    resetServer();
  }

  @Test
  public void gettingError_withUnMappedThrowable_shouldHaveExpectedStatusCode() {
    getBaseScenario().given().when().get(ERROR_ROUTE).then().statusCode(getStatusCode());
  }

  @Test
  public void gettingError_withUnMappedThrowable_shouldProvideProperlyFormattedResponse() {
    getBaseScenario()
        .given()
        .when()
        .get(ERROR_ROUTE)
        .then()
        .body(matchesJsonSchema("error/ErrorResponse"));
  }

  @Test
  public void gettingError_withUnMappedThrowable_shouldProvideDefaultErrorResponse() {
    getBaseScenario()
        .given()
        .when()
        .get(ERROR_ROUTE)
        .then()
        .body("error", equalTo(getErrorCodeMatcher()))
        .body("message", equalTo(getErrorMessageMatcher()));
  }

  public abstract Throwable getError();

  public abstract int getStatusCode();

  public abstract String getErrorCodeMatcher();

  public abstract String getErrorMessageMatcher();
}
