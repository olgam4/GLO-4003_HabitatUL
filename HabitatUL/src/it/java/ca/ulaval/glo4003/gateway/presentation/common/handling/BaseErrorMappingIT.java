package ca.ulaval.glo4003.gateway.presentation.common.handling;

import ca.ulaval.glo4003.gateway.presentation.ResourceConfigBuilder;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.gateway.presentation.RestITestHelper.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public abstract class BaseErrorMappingIT {
  @Mock private ErrorThrowingAppService errorThrowingAppService;

  @BeforeClass
  public static void setUpClass() {
    startServer();
  }

  @AfterClass
  public static void tearDownClass() {
    stopServer();
  }

  @Before
  public void setUp() throws Exception {
    when(errorThrowingAppService.getError()).thenThrow(getError());
    ErrorThrowingResource errorThrowingResource =
        new ErrorThrowingResource(errorThrowingAppService);
    ResourceConfig resourceConfig =
        ResourceConfigBuilder.aResourceConfig()
            .withResource(errorThrowingResource)
            .withErrorMapper(RouteNotFoundErrorMapper.class)
            .withErrorMapper(DeserializationErrorMapper.class)
            .withErrorMapper(ConstraintViolationErrorMapper.class)
            .withErrorMapper(BaseErrorMapper.class)
            .withErrorMapper(CatchAllErrorMapper.class)
            .build();
    addResourceConfig(resourceConfig);
  }

  @After
  public void tearDown() {
    resetServer();
  }

  public abstract Throwable getError();
}
