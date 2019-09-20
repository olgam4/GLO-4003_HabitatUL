package ca.ulaval.glo4003.projet.base.context;

import static org.junit.Assert.assertSame;

import ca.ulaval.glo4003.projet.base.context.exception.CannotRegisterContractTwiceException;
import ca.ulaval.glo4003.projet.base.context.exception.UnableResolveServiceException;
import org.junit.Before;
import org.junit.Test;

public class ServiceLocatorTest {

  @Before
  public void setUp() {
    ServiceLocator.reset();
  }

  @Test
  public void givenARegisteredService_whenResolving_thenServiceIsResolved() {
    TestImplementation implementation = new TestImplementation();
    ServiceLocator.register(TestContract.class, implementation);
    assertSame(implementation, ServiceLocator.resolve(TestContract.class));
  }

  @Test(expected = UnableResolveServiceException.class)
  public void givenAnUnregisteredService_whenResolving_thenThrows() {
    ServiceLocator.resolve(TestContract.class);
  }

  @Test(expected = CannotRegisterContractTwiceException.class)
  public void givenAlreadyRegisteredService_whenRegisteringTwice_thenThrows() {
    ServiceLocator.register(TestContract.class, new TestImplementation());
    ServiceLocator.register(TestContract.class, new TestImplementation());
  }

  private interface TestContract {}

  private class TestImplementation implements TestContract {}
}
