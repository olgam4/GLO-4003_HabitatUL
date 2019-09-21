package ca.ulaval.glo4003.context;

import ca.ulaval.glo4003.context.exception.CannotRegisterContractTwiceException;
import ca.ulaval.glo4003.context.exception.UnableResolveServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServiceLocatorTest {
  @BeforeEach
  public void setUp() {
    ServiceLocator.reset();
  }

  @Test
  public void resolvingAService_whenServiceIsRegistered_thenServiceIsResolved() {
    TestImplementation expectedImplementation = new TestImplementation();
    ServiceLocator.register(TestContract.class, expectedImplementation);

    TestContract observedImplementation = ServiceLocator.resolve(TestContract.class);

    assertSame(expectedImplementation, observedImplementation);
  }

  @Test
  public void resolvingAService_whenServiceIsNotRegistered_thenThrows() {
    Executable act = () -> ServiceLocator.resolve(Test.class);

    assertThrows(UnableResolveServiceException.class, act);
  }

  @Test
  public void registeringAService_whenServiceAlreadyRegistered_thenThrows() {
    ServiceLocator.register(TestContract.class, new TestImplementation());

    Executable act = () -> ServiceLocator.register(TestContract.class, new TestImplementation());

    assertThrows(CannotRegisterContractTwiceException.class, act);
  }

  private interface TestContract {}

  private class TestImplementation implements TestContract {}
}
