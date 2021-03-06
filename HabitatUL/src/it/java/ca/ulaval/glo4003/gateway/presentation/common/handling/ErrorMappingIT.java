package ca.ulaval.glo4003.gateway.presentation.common.handling;

import org.junit.Test;

import static ca.ulaval.glo4003.gateway.presentation.RestITestHelper.getBaseScenario;
import static ca.ulaval.glo4003.gateway.presentation.RestITestHelper.matchesJsonSchema;
import static ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorThrowingResource.ERROR_ROUTE;
import static org.hamcrest.CoreMatchers.equalTo;

public abstract class ErrorMappingIT extends BaseErrorMappingIT {
  @Test
  public void gettingError_shouldHaveExpectedStatusCode() {
    getBaseScenario().given().when().get(ERROR_ROUTE).then().statusCode(getStatusCode());
  }

  @Test
  public void gettingError_shouldProvideProperlyFormattedResponse() {
    getBaseScenario()
        .given()
        .when()
        .get(ERROR_ROUTE)
        .then()
        .body(matchesJsonSchema("error/ErrorResponse"));
  }

  @Test
  public void gettingError_shouldProvideDefaultErrorResponse() {
    getBaseScenario()
        .given()
        .when()
        .get(ERROR_ROUTE)
        .then()
        .body("error", equalTo(getErrorCodeMatcher()))
        .body("message", equalTo(getErrorMessageMatcher()));
  }

  public abstract int getStatusCode();

  public abstract String getErrorCodeMatcher();

  public abstract String getErrorMessageMatcher();
}
