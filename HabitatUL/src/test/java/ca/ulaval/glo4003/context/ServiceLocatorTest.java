package ca.ulaval.glo4003.context;

import ca.ulaval.glo4003.context.exception.CannotRegisterContractTwiceException;
import ca.ulaval.glo4003.context.exception.UnableResolveServiceException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertSame;

public class ServiceLocatorTest {
  @Before
  public void setUp() {
    ServiceLocator.reset();
  }

  @Test
  public void resolvingContract_whenServiceIsRegistered_resolvesAssociatedService() {
    TestImplementation expectedImplementation = new TestImplementation();
    ServiceLocator.register(TestContract.class, expectedImplementation);

    TestContract observedImplementation = ServiceLocator.resolve(TestContract.class);

    assertSame(expectedImplementation, observedImplementation);
  }

  @Test(expected = UnableResolveServiceException.class)
  public void resolvingContract_whenServiceIsNotRegistered_throws() {
    ServiceLocator.resolve(Test.class);
  }

  @Test(expected = CannotRegisterContractTwiceException.class)
  public void registeringService_whenServiceAlreadyRegistered_throws() {
    ServiceLocator.register(TestContract.class, new TestImplementation());

    ServiceLocator.register(TestContract.class, new TestImplementation());
  }

  private interface TestContract {}

  private class TestImplementation implements TestContract {}
}
