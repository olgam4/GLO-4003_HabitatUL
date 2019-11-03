package ca.ulaval.glo4003.gateway.presentation.policy;

import ca.ulaval.glo4003.administration.application.user.UserAppService;
import ca.ulaval.glo4003.coverage.application.claim.dto.ClaimCreationDto;
import ca.ulaval.glo4003.coverage.application.policy.PolicyAppService;
import ca.ulaval.glo4003.coverage.domain.claim.ClaimId;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyId;
import ca.ulaval.glo4003.gateway.presentation.RequestBodyGenerator;
import ca.ulaval.glo4003.gateway.presentation.ResourceConfigBuilder;
import ca.ulaval.glo4003.gateway.presentation.common.filter.AuthFilterBuilder;
import ca.ulaval.glo4003.helper.claim.ClaimGenerator;
import ca.ulaval.glo4003.helper.policy.PolicyGenerator;
import com.github.javafaker.Faker;
import org.glassfish.jersey.server.ResourceConfig;
import org.json.JSONObject;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

import static ca.ulaval.glo4003.gateway.presentation.RestITestHelper.*;
import static ca.ulaval.glo4003.gateway.presentation.claim.ClaimResource.CLAIM_ROUTE;
import static ca.ulaval.glo4003.gateway.presentation.policy.PolicyResource.OPEN_CLAIM_ROUTE;
import static ca.ulaval.glo4003.gateway.presentation.policy.PolicyResource.POLICY_ROUTE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PolicyResourceIT {
  private static final String FIRST_POLICY_ID = Faker.instance().internet().uuid();
  private static final String SECOND_POLICY_ID = Faker.instance().internet().uuid();
  private static final List<String> POLICIES_ID = Arrays.asList(FIRST_POLICY_ID, SECOND_POLICY_ID);
  private static final String POLICY_ID_REPRESENTATION =
      PolicyGenerator.createPolicyId().toRepresentation();
  private static final ClaimId CLAIM_ID = ClaimGenerator.createClaimId();
  private static final String CLAIM_ID_REPRESENTATION = CLAIM_ID.toRepresentation();

  @Mock private PolicyAppService policyAppService;
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
    when(userAppService.getPolicies(any(String.class))).thenReturn(POLICIES_ID);
    when(policyAppService.openClaim(any(PolicyId.class), any(ClaimCreationDto.class)))
        .thenReturn(CLAIM_ID);
    PolicyResource policyResource =
        new PolicyResource(policyAppService, userAppService, new PolicyViewAssembler());
    ResourceConfig resourceConfig =
        ResourceConfigBuilder.aResourceConfig()
            .withResource(policyResource)
            .withRequestFilter(AuthFilterBuilder.anAuthFilter().build())
            .build();
    addResourceConfig(resourceConfig);
  }

  @After
  public void tearDown() {
    resetServer();
  }

  @Test
  public void gettingPolicies_shouldHaveExpectedStatusCode() {
    int expectedStatusCode = Response.Status.OK.getStatusCode();
    getBaseScenario().given().when().get(POLICY_ROUTE).then().statusCode(expectedStatusCode);
  }

  @Test
  public void gettingPolicies_shouldProvideProperlyFormattedResponse() {
    getBaseScenario()
        .given()
        .when()
        .get(POLICY_ROUTE)
        .then()
        .body(matchesJsonSchema("policy/PoliciesResponse"));
  }

  @Test
  public void openingClaim_shouldHaveExpectedStatusCode() {
    JSONObject request = RequestBodyGenerator.createClaimRequestBody();
    String route = toPath(POLICY_ROUTE, POLICY_ID_REPRESENTATION, OPEN_CLAIM_ROUTE);

    int expectedStatusCode = Response.Status.CREATED.getStatusCode();
    getBaseScenario()
        .given()
        .body(request.toString())
        .when()
        .post(route)
        .then()
        .statusCode(expectedStatusCode);
  }

  @Test
  public void openingClaim_shouldProvideLocationCreatedClaim() {
    JSONObject request = RequestBodyGenerator.createClaimRequestBody();
    String route = toPath(POLICY_ROUTE, POLICY_ID_REPRESENTATION, OPEN_CLAIM_ROUTE);

    String expectedLocation = toUri(CLAIM_ROUTE, CLAIM_ID_REPRESENTATION);
    getBaseScenario()
        .given()
        .body(request.toString())
        .when()
        .post(route)
        .then()
        .header(HttpHeaders.LOCATION, expectedLocation);
  }
}
