package ca.ulaval.glo4003.context;

import ca.ulaval.glo4003.context.exception.CannotRegisterContractTwiceException;
import ca.ulaval.glo4003.context.exception.CannotReplaceUnregisteredContract;
import ca.ulaval.glo4003.context.exception.CannotResolveServiceException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class ServiceLocatorTest {
  @Before
  public void setUp() {
    ServiceLocator.reset();
  }

  @Test
  public void resolvingContract_withRegisteredService_shouldResolveService() {
    TestImplementation expectedImplementation = new TestImplementation();
    ServiceLocator.register(TestContract.class, expectedImplementation);

    TestContract observedImplementation = ServiceLocator.resolve(TestContract.class);

    assertSame(expectedImplementation, observedImplementation);
  }

  @Test(expected = CannotResolveServiceException.class)
  public void resolvingContract_withUnregisteredService_shouldThrow() {
    ServiceLocator.resolve(Test.class);
  }

  @Test(expected = CannotRegisterContractTwiceException.class)
  public void registeringContract_withAlreadyRegisteredService_shouldThrow() {
    ServiceLocator.register(TestContract.class, new TestImplementation());

    ServiceLocator.register(TestContract.class, new TestImplementation());
  }

  @Test
  public void replacingContract_withRegisteredService_shouldReplaceService() {
    ServiceLocator.register(TestContract.class, new TestImplementation());

    AnotherTestImplementation replacingService = new AnotherTestImplementation();
    ServiceLocator.replace(TestContract.class, replacingService);

    TestContract service = ServiceLocator.resolve(TestContract.class);
    assertEquals(replacingService, service);
  }

  @Test(expected = CannotReplaceUnregisteredContract.class)
  public void replacingContract_withUnregisteredService_shouldThrow() {
    ServiceLocator.replace(TestContract.class, new TestImplementation());
  }

  @Test
  public void resettingServiceLocator_shouldForgetAllRegisteredContractsAndServices() {
    ServiceLocator.register(TestContract.class, new TestImplementation());

    ServiceLocator.reset();

    ServiceLocator.register(TestContract.class, new TestImplementation());
  }

  private interface TestContract {}

  private class TestImplementation implements TestContract {}

  private class AnotherTestImplementation implements TestContract {}
}
