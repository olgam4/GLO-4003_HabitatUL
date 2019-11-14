package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer;

import org.hamcrest.Matchers;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static ca.ulaval.glo4003.gateway.presentation.RestITestHelper.getBaseScenario;
import static org.hamcrest.CoreMatchers.equalTo;

public abstract class InvalidTestCasesCustomDeserializerIT extends CustomDeserializerIT {
  private Object value;

  public InvalidTestCasesCustomDeserializerIT(Object value) {
    this.value = value;
  }

  @Test
  public void gettingDeserialization_withInvalidValue_shouldNotBeSuccessful() {
    getBaseScenario()
        .given()
        .body(createSinglePropertyRequestBody(value))
        .when()
        .post(DESERIALIZE_ROUTE)
        .then()
        .statusCode(Matchers.not(Response.Status.OK.getStatusCode()));
  }

  @Test
  public void gettingDeserialization_withInvalidValue_shouldThrowMeaningfulError() {
    getBaseScenario()
        .given()
        .body(createSinglePropertyRequestBody(value))
        .when()
        .post(DESERIALIZE_ROUTE)
        .then()
        .body("cause", equalTo(getDeserializationErrorCause().getName()));
  }

  protected abstract Class<?> getDeserializationErrorCause();
}
