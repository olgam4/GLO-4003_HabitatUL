package ca.ulaval.glo4003.gateway.presentation.insuring.policy;

import ca.ulaval.glo4003.administration.application.user.UserAppService;
import ca.ulaval.glo4003.gateway.presentation.ResourceConfigBuilder;
import ca.ulaval.glo4003.gateway.presentation.common.filter.AuthFilterBuilder;
import ca.ulaval.glo4003.insuring.application.policy.PolicyAppService;
import ca.ulaval.glo4003.insuring.application.policy.dto.*;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationId;
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

import static ca.ulaval.glo4003.gateway.presentation.RequestBodyGenerator.*;
import static ca.ulaval.glo4003.gateway.presentation.RestITestHelper.*;
import static ca.ulaval.glo4003.gateway.presentation.insuring.claim.ClaimResource.CLAIM_ROUTE;
import static ca.ulaval.glo4003.gateway.presentation.insuring.policy.PolicyResource.*;
import static ca.ulaval.glo4003.helper.claim.ClaimGenerator.createClaimId;
import static ca.ulaval.glo4003.helper.policy.PolicyGenerator.createPolicyDto;
import static ca.ulaval.glo4003.helper.policy.PolicyGenerator.createPolicyId;
import static ca.ulaval.glo4003.helper.policy.PolicyModificationGenerator.createPolicyModificationDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PolicyResourceIT {
  private static final String FIRST_POLICY_ID_REPRESENTATION = createPolicyId().toRepresentation();
  private static final String SECOND_POLICY_ID_REPRESENTATION = createPolicyId().toRepresentation();
  private static final List<String> POLICIES_ID =
      Arrays.asList(FIRST_POLICY_ID_REPRESENTATION, SECOND_POLICY_ID_REPRESENTATION);
  private static final String POLICY_ID_REPRESENTATION = createPolicyId().toRepresentation();
  private static final PolicyModificationDto POLICY_MODIFICATION_DTO =
      createPolicyModificationDto();
  private static final PolicyModificationId POLICY_MODIFICATION_ID =
      POLICY_MODIFICATION_DTO.getPolicyModificationId();
  private static final String POLICY_MODIFICATION_ID_REPRESENTATION =
      POLICY_MODIFICATION_ID.toRepresentation();
  private static final PolicyDto POLICY_DTO = createPolicyDto();
  private static final ClaimId CLAIM_ID = createClaimId();
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
    when(policyAppService.insureBicycle(any(PolicyId.class), any(InsureBicycleDto.class)))
        .thenReturn(POLICY_MODIFICATION_DTO);
    when(policyAppService.modifyCoverage(any(PolicyId.class), any(ModifyCoverageDto.class)))
        .thenReturn(POLICY_MODIFICATION_DTO);
    when(policyAppService.confirmModification(any(PolicyId.class), any(PolicyModificationId.class)))
        .thenReturn(POLICY_DTO);
    when(policyAppService.openClaim(any(PolicyId.class), any(OpenClaimDto.class)))
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
  public void insuringBicycle_shouldHaveExpectedStatusCode() {
    JSONObject request = createInsureBicycleRequestBody();
    String route = toPath(POLICY_ROUTE, POLICY_ID_REPRESENTATION, INSURE_BICYCLE_ROUTE);

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
  public void insuringBicycle_shouldProvideLocationCreatedPolicyModification() {
    JSONObject request = createInsureBicycleRequestBody();
    String route = toPath(POLICY_ROUTE, POLICY_ID_REPRESENTATION, INSURE_BICYCLE_ROUTE);

    String expectedLocation =
        toUri(
            POLICY_ROUTE,
            POLICY_ID_REPRESENTATION,
            POLICY_MODIFICATION_ROUTE,
            POLICY_MODIFICATION_ID_REPRESENTATION);
    getBaseScenario()
        .given()
        .body(request.toString())
        .when()
        .post(route)
        .then()
        .header(HttpHeaders.LOCATION, expectedLocation);
  }

  @Test
  public void insuringBicycle_shouldProvideProperlyFormattedResponse() {
    JSONObject request = createInsureBicycleRequestBody();
    String route = toPath(POLICY_ROUTE, POLICY_ID_REPRESENTATION, INSURE_BICYCLE_ROUTE);

    getBaseScenario()
        .given()
        .body(request.toString())
        .when()
        .post(route)
        .then()
        .body(matchesJsonSchema("policy/PolicyModificationResponse"));
  }

  @Test
  public void modifyingCoverage_shouldHaveExpectedStatusCode() {
    JSONObject request = createModifyCoverageRequestBody();
    String route = toPath(POLICY_ROUTE, POLICY_ID_REPRESENTATION, MODIFY_COVERAGE_ROUTE);

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
  public void modifyingCoverage_shouldProvideLocationCreatedPolicyModification() {
    JSONObject request = createModifyCoverageRequestBody();
    String route = toPath(POLICY_ROUTE, POLICY_ID_REPRESENTATION, MODIFY_COVERAGE_ROUTE);

    String expectedLocation =
        toUri(
            POLICY_ROUTE,
            POLICY_ID_REPRESENTATION,
            POLICY_MODIFICATION_ROUTE,
            POLICY_MODIFICATION_ID_REPRESENTATION);
    getBaseScenario()
        .given()
        .body(request.toString())
        .when()
        .post(route)
        .then()
        .header(HttpHeaders.LOCATION, expectedLocation);
  }

  @Test
  public void modifyingCoverage_shouldProvideProperlyFormattedResponse() {
    JSONObject request = createModifyCoverageRequestBody();
    String route = toPath(POLICY_ROUTE, POLICY_ID_REPRESENTATION, MODIFY_COVERAGE_ROUTE);

    getBaseScenario()
        .given()
        .body(request.toString())
        .when()
        .post(route)
        .then()
        .body(matchesJsonSchema("policy/PolicyModificationResponse"));
  }

  @Test
  public void confirmingModification_shouldHaveExpectedStatusCode() {
    String route =
        toPath(
            POLICY_ROUTE,
            POLICY_ID_REPRESENTATION,
            POLICY_MODIFICATION_ROUTE,
            POLICY_MODIFICATION_ID_REPRESENTATION,
            CONFIRM_MODIFICATION_ROUTE);

    int expectedStatusCode = Response.Status.OK.getStatusCode();
    getBaseScenario().when().post(route).then().statusCode(expectedStatusCode);
  }

  @Test
  public void confirmingModification_shouldProvideProperlyFormattedResponse() {
    String route =
        toPath(
            POLICY_ROUTE,
            POLICY_ID_REPRESENTATION,
            POLICY_MODIFICATION_ROUTE,
            POLICY_MODIFICATION_ID_REPRESENTATION,
            CONFIRM_MODIFICATION_ROUTE);

    getBaseScenario().when().post(route).then().body(matchesJsonSchema("policy/PolicyResponse"));
  }

  @Test
  public void openingClaim_shouldHaveExpectedStatusCode() {
    JSONObject request = createClaimRequestBody();
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
    JSONObject request = createClaimRequestBody();
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
