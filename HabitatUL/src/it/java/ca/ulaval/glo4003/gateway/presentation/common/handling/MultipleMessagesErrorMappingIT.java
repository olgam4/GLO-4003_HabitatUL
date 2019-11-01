package ca.ulaval.glo4003.gateway.presentation.common.handling;

import org.junit.Test;

import java.util.Set;

import static ca.ulaval.glo4003.gateway.presentation.RestITestHelper.getBaseScenario;
import static ca.ulaval.glo4003.gateway.presentation.RestITestHelper.matchesJsonSchema;
import static ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorThrowingResource.ERROR_ROUTE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.containsInAnyOrder;

public abstract class MultipleMessagesErrorMappingIT extends BaseErrorMappingIT {
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
        .body(matchesJsonSchema("error/MultipleMessagesErrorResponse"));
  }

  @Test
  public void gettingError_shouldProvideDefaultErrorResponse() {
    getBaseScenario()
        .given()
        .when()
        .get(ERROR_ROUTE)
        .then()
        .body("error", equalTo(getErrorCodeMatcher()))
        .body("messages", containsInAnyOrder(getErrorMessagesMatcher().toArray()));
  }

  public abstract int getStatusCode();

  public abstract String getErrorCodeMatcher();

  public abstract Set<String> getErrorMessagesMatcher();
}
