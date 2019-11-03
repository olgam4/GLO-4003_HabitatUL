package ca.ulaval.glo4003.gateway.presentation.claim;

import ca.ulaval.glo4003.coverage.application.claim.ClaimAppService;
import ca.ulaval.glo4003.coverage.application.claim.dto.ClaimDto;
import ca.ulaval.glo4003.coverage.domain.claim.ClaimId;
import ca.ulaval.glo4003.gateway.presentation.ResourceConfigBuilder;
import ca.ulaval.glo4003.gateway.presentation.common.filter.AuthFilterBuilder;
import ca.ulaval.glo4003.helper.claim.ClaimGenerator;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

import static ca.ulaval.glo4003.gateway.presentation.RestITestHelper.*;
import static ca.ulaval.glo4003.gateway.presentation.claim.ClaimResource.CLAIM_ROUTE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClaimResourceIT {
  private static final ClaimDto CLAIM_DTO = ClaimGenerator.createClaimDto();
  private static final String CLAIM_ID_REPRESENTATION = CLAIM_DTO.getClaimId().toRepresentation();

  @Mock private ClaimAppService claimAppService;

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
    when(claimAppService.getClaim(any(ClaimId.class))).thenReturn(CLAIM_DTO);
    ClaimResource claimResource = new ClaimResource(claimAppService, new ClaimViewAssembler());
    ResourceConfig resourceConfig =
        ResourceConfigBuilder.aResourceConfig()
            .withResource(claimResource)
            .withRequestFilter(AuthFilterBuilder.anAuthFilter().build())
            .build();
    addResourceConfig(resourceConfig);
  }

  @After
  public void tearDown() {
    resetServer();
  }

  @Test
  public void gettingClaim_shouldHaveExpectedStatusCode() {
    String route = toPath(CLAIM_ROUTE, CLAIM_ID_REPRESENTATION);

    int expectedStatusCode = Response.Status.OK.getStatusCode();
    getBaseScenario().given().when().get(route).then().statusCode(expectedStatusCode);
  }

  @Test
  public void gettingClaim_shouldProvideProperlyFormattedResponse() {
    String route = toPath(CLAIM_ROUTE, CLAIM_ID_REPRESENTATION);

    getBaseScenario()
        .given()
        .when()
        .get(route)
        .then()
        .body(matchesJsonSchema("claim/ClaimResponse"));
  }
}
