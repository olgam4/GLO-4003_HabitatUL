package ca.ulaval.glo4003.gateway.presentation.user;

import ca.ulaval.glo4003.administration.application.user.UserAppService;
import ca.ulaval.glo4003.administration.domain.user.credential.Credentials;
import ca.ulaval.glo4003.administration.domain.user.token.Token;
import ca.ulaval.glo4003.gateway.presentation.RequestBodyGenerator;
import ca.ulaval.glo4003.gateway.presentation.ResourceConfigBuilder;
import ca.ulaval.glo4003.helper.user.TokenGenerator;
import com.github.javafaker.Faker;
import org.glassfish.jersey.server.ResourceConfig;
import org.json.JSONObject;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.HttpHeaders;

import static ca.ulaval.glo4003.gateway.presentation.RestITestHelper.*;
import static ca.ulaval.glo4003.gateway.presentation.user.UserResource.AUTHENTICATION_ROUTE;
import static ca.ulaval.glo4003.gateway.presentation.user.UserResource.USER_ROUTE;
import static javax.ws.rs.core.Response.Status;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserResourceIT {
  private static final String USER_KEY = Faker.instance().internet().uuid();
  private static final Token TOKEN = TokenGenerator.createToken();

  @Mock private UserAppService userAppService;

  private UserViewAssembler userViewAssembler;

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
    when(userAppService.createUser(any(Credentials.class))).thenReturn(USER_KEY);
    when(userAppService.authenticateUser(any(Credentials.class))).thenReturn(TOKEN);
    userViewAssembler = new UserViewAssembler();
    UserResource userResource = new UserResource(userAppService, userViewAssembler);
    ResourceConfig resourceConfig =
        ResourceConfigBuilder.aResourceConfig().withResource(userResource).build();
    addResourceConfig(resourceConfig);
  }

  @After
  public void tearDown() {
    resetServer();
  }

  @Test
  public void creatingUser_shouldHaveExpectedStatusCode() {
    JSONObject request = RequestBodyGenerator.createCredentialRequestBody();

    getBaseScenario()
        .given()
        .body(request.toString())
        .when()
        .post(USER_ROUTE)
        .then()
        .statusCode(Status.CREATED.getStatusCode());
  }

  @Test
  public void creatingUser_shouldProvideLocationCreatedUser() {
    JSONObject request = RequestBodyGenerator.createCredentialRequestBody();

    String expectedLocation = toUri(USER_ROUTE, USER_KEY);
    getBaseScenario()
        .given()
        .body(request.toString())
        .when()
        .post(USER_ROUTE)
        .then()
        .header(HttpHeaders.LOCATION, expectedLocation);
  }

  @Test
  public void authenticatingUser_shouldHaveExpectedStatusCode() {
    JSONObject request = RequestBodyGenerator.createCredentialRequestBody();
    String route = toPath(USER_ROUTE, AUTHENTICATION_ROUTE);

    getBaseScenario()
        .given()
        .body(request.toString())
        .when()
        .post(route)
        .then()
        .statusCode(Status.OK.getStatusCode());
  }

  @Test
  public void authenticatingUser_shouldProvideProperlyFormattedResponse() {
    JSONObject request = RequestBodyGenerator.createCredentialRequestBody();
    String route = toPath(USER_ROUTE, AUTHENTICATION_ROUTE);

    getBaseScenario()
        .given()
        .body(request.toString())
        .when()
        .post(route)
        .then()
        .body(matchesJsonSchema("user/AuthenticationResponse"));
  }
}
