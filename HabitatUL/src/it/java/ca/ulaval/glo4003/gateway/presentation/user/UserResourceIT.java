package ca.ulaval.glo4003.gateway.presentation.user;

import ca.ulaval.glo4003.gateway.presentation.ResourceConfigBuilder;
import ca.ulaval.glo4003.generator.user.UserGenerator;
import ca.ulaval.glo4003.management.application.user.UserAppService;
import ca.ulaval.glo4003.management.application.user.dto.UserDto;
import ca.ulaval.glo4003.management.domain.user.token.Token;
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
  private static Token A_TOKEN = new Token(Faker.instance().internet().uuid());

  @Mock private UserAppService userAppService;

  private UserDto userDto;
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
    userViewAssembler = new UserViewAssembler();
    UserResource userResource = new UserResource(userAppService, userViewAssembler);
    ResourceConfig resourceConfig =
        ResourceConfigBuilder.aResourceConfig().withResource(userResource).build();
    addResourceConfig(resourceConfig);
    userDto = UserGenerator.createUserDto();
    when(userAppService.createUser(any())).thenReturn(userDto);
    when(userAppService.authenticateUser(any())).thenReturn(A_TOKEN);
  }

  @After
  public void tearDown() {
    resetServer();
  }

  @Test
  public void postingUserPath_withValidRequest_shouldHaveExpectedStatusCode() {
    JSONObject request = CredentialsBuilder.aCredentialsRequest().build();

    getBaseScenario()
        .given()
        .body(request.toString())
        .when()
        .post(USER_ROUTE)
        .then()
        .statusCode(Status.CREATED.getStatusCode());
  }

  @Test
  public void postingUserPath_withValidRequest_shouldProvideLocationCreatedUser() {
    JSONObject request = CredentialsBuilder.aCredentialsRequest().build();

    String expectedLocation = toUri(USER_ROUTE, userDto.getUserId().getValue().toString());
    getBaseScenario()
        .given()
        .body(request.toString())
        .when()
        .post(USER_ROUTE)
        .then()
        .header(HttpHeaders.LOCATION, expectedLocation);
  }

  @Test
  public void postingUserAuthenticationPath_withValidRequest_shouldHaveExpectedStatusCode() {
    String path = toPath(USER_ROUTE, AUTHENTICATION_ROUTE);
    JSONObject request = CredentialsBuilder.aCredentialsRequest().build();

    getBaseScenario()
        .given()
        .body(request.toString())
        .when()
        .post(path)
        .then()
        .statusCode(Status.OK.getStatusCode());
  }

  @Test
  public void
      postingUserAuthenticationPath_withValidRequest_shouldProvideProperlyFormattedResponse() {
    String path = toPath(USER_ROUTE, AUTHENTICATION_ROUTE);
    JSONObject request = CredentialsBuilder.aCredentialsRequest().build();

    getBaseScenario()
        .given()
        .body(request.toString())
        .when()
        .post(path)
        .then()
        .body(matchesJsonSchema("user/AuthenticationResponse"));
  }
}
