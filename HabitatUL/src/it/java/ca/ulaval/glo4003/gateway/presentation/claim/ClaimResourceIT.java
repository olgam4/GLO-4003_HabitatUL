package ca.ulaval.glo4003.gateway.presentation.claim;

import ca.ulaval.glo4003.coverage.application.claim.ClaimAppService;
import ca.ulaval.glo4003.coverage.application.claim.dto.ClaimDto;
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
  @Mock private ClaimAppService claimAppService;

  private ClaimDto claimDto;

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
    ClaimResource claimResource = new ClaimResource(claimAppService, new ClaimViewAssembler());
    claimDto = ClaimGenerator.createClaimDto();
    when(claimAppService.getClaim(any())).thenReturn(claimDto);
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
  public void gettingClaimPath_withValidRequest_shouldHaveExpectedStatusCode() {
    String path = toPath(CLAIM_ROUTE, claimDto.getClaimId().toRepresentation());

    int expectedStatusCode = Response.Status.OK.getStatusCode();
    getBaseScenario().given().when().get(path).then().statusCode(expectedStatusCode);
  }

  @Test
  public void gettingClaimPath_withValidRequest_shouldProvideProperlyFormattedResponse() {
    String path = toPath(CLAIM_ROUTE, claimDto.getClaimId().toRepresentation());

    getBaseScenario()
        .given()
        .when()
        .get(path)
        .then()
        .body(matchesJsonSchema("claim/ClaimResponse"));
  }
}
