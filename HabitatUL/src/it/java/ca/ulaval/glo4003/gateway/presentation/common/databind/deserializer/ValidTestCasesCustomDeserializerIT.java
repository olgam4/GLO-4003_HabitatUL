package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer;

import org.junit.Test;

import javax.ws.rs.core.Response;

import static ca.ulaval.glo4003.gateway.presentation.RestITestHelper.getBaseScenario;

public abstract class ValidTestCasesCustomDeserializerIT extends CustomDeserializerIT {
  private Object value;

  public ValidTestCasesCustomDeserializerIT(Object value) {
    this.value = value;
  }

  @Test
  public void gettingDeserialization_withValidValue_shouldBeSuccessful() {
    getBaseScenario()
        .given()
        .body(createSinglePropertyRequestBody(value))
        .when()
        .post(DESERIALIZE_ROUTE)
        .then()
        .statusCode(Response.Status.OK.getStatusCode());
  }
}
