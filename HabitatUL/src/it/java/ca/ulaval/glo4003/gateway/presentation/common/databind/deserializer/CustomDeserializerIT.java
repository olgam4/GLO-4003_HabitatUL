package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer;

import ca.ulaval.glo4003.gateway.presentation.ResourceConfigBuilder;
import org.glassfish.jersey.server.ResourceConfig;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import static ca.ulaval.glo4003.gateway.presentation.RestITestHelper.*;

public abstract class CustomDeserializerIT {
  public static final String DESERIALIZE_ROUTE = "/deserialize";

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
    ResourceConfig resourceConfig =
        ResourceConfigBuilder.aResourceConfig()
            .withResource(createDeserializationResource())
            .withErrorMapper(MockedDeserializationErrorMapper.class)
            .build();
    addResourceConfig(resourceConfig);
  }

  @After
  public void tearDown() {
    resetServer();
  }

  public static String createSinglePropertyRequestBody(Object value) {
    return createSinglePropertyRequestBody("value", value);
  }

  public static String createSinglePropertyRequestBody(String key, Object value) {
    return new JSONObject().put(key, value).toString();
  }

  public abstract Object createDeserializationResource();
}
