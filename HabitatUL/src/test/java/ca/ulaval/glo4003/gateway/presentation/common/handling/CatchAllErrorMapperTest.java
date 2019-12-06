package ca.ulaval.glo4003.gateway.presentation.common.handling;

import ca.ulaval.glo4003.shared.application.logging.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CatchAllErrorMapperTest {
  private static final Throwable THROWABLE = new RuntimeException();

  @Mock private Logger logger;

  private CatchAllErrorMapper subject;

  @Before
  public void setUp() {
    subject = new CatchAllErrorMapper(logger);
  }

  @Test
  public void mappingErrorToResponse_shouldLogErrorAsSevere() {
    subject.toResponse(THROWABLE);

    verify(logger).severe(anyString());
  }
}
