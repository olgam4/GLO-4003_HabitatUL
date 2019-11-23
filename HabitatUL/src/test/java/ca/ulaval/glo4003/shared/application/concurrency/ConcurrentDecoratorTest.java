package ca.ulaval.glo4003.shared.application.concurrency;

import ca.ulaval.glo4003.shared.application.concurrency.MapLock.LockHandle;
import ca.ulaval.glo4003.shared.application.concurrency.error.ConcurrentAccessError;
import ca.ulaval.glo4003.shared.application.concurrency.exception.ValueAlreadyLockedException;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConcurrentDecoratorTest {
  private static final String LOCK_VALUE = Faker.instance().internet().uuid();
  private static final String SUPPLIER_RESULT = Faker.instance().internet().uuid();
  private static final Supplier<String> SUPPLIER = () -> SUPPLIER_RESULT;
  private static final RuntimeException EXCEPTION = new RuntimeException();
  private static final Runnable THROWING_RUNNABLE =
      () -> {
        throw EXCEPTION;
      };
  private static final Supplier<String> THROWING_SUPPLIER =
      () -> {
        throw EXCEPTION;
      };

  @Mock private MapLock<String> mapLock;
  @Mock private LockHandle<String> lockHandle;
  @Mock private Runnable runnable;

  private ConcurrentDecorator<String> subject;

  @Before
  public void setUp() throws ValueAlreadyLockedException {
    when(mapLock.lock(LOCK_VALUE)).thenReturn(lockHandle);
    subject = new ConcurrentDecorator<>(mapLock);
  }

  @Test
  public void lockingAndCalling_supplier_shouldReturnSupplierResult() {
    String result = subject.lockAndCall(LOCK_VALUE, SUPPLIER);

    assertEquals(SUPPLIER_RESULT, result);
  }

  @Test
  public void lockingAndCalling_supplier_shouldLockAndUnlock() throws ValueAlreadyLockedException {
    subject.lockAndCall(LOCK_VALUE, SUPPLIER);

    verify(mapLock).lock(LOCK_VALUE);
    verify(lockHandle).unlock();
  }

  @Test
  public void lockingAndCalling_throwingSupplier_shouldThrowSameException() {
    try {
      subject.lockAndCall(LOCK_VALUE, THROWING_SUPPLIER);
    } catch (Exception thrownException) {
      assertEquals(EXCEPTION, thrownException);
    }
  }

  @Test
  public void lockingAndCalling_withThrowingSupplier_shouldLockAndUnlock()
      throws ValueAlreadyLockedException {
    try {
      subject.lockAndCall(LOCK_VALUE, THROWING_SUPPLIER);
    } catch (Exception ignored) {
    }

    verify(mapLock).lock(LOCK_VALUE);
    verify(lockHandle).unlock();
  }

  @Test(expected = ConcurrentAccessError.class)
  public void lockingAndCalling_withSupplierAndAlreadyLockedValue_shouldThrow()
      throws ValueAlreadyLockedException {
    when(mapLock.lock(LOCK_VALUE)).thenThrow(ValueAlreadyLockedException.class);

    subject.lockAndCall(LOCK_VALUE, SUPPLIER);
  }

  @Test
  public void lockingAndCalling_runnable_shouldExecuteRunnable() {
    subject.lockAndCall(LOCK_VALUE, runnable);

    verify(runnable).run();
  }

  @Test
  public void lockingAndCalling_runnable_shouldLockAndUnlock() throws ValueAlreadyLockedException {
    subject.lockAndCall(LOCK_VALUE, runnable);

    verify(mapLock).lock(LOCK_VALUE);
    verify(lockHandle).unlock();
  }

  @Test
  public void lockingAndCalling_throwingRunnable_shouldThrowSameException() {
    try {
      subject.lockAndCall(LOCK_VALUE, THROWING_RUNNABLE);
    } catch (Exception thrownException) {
      assertEquals(EXCEPTION, thrownException);
    }
  }

  @Test
  public void lockingAndCalling_withThrowingRunnable_shouldLockAndUnlock()
      throws ValueAlreadyLockedException {
    try {
      subject.lockAndCall(LOCK_VALUE, THROWING_RUNNABLE);
    } catch (Exception e) {
    }

    verify(mapLock).lock(LOCK_VALUE);
    verify(lockHandle).unlock();
  }

  @Test(expected = ConcurrentAccessError.class)
  public void lockingAndCalling_withRunnableAndAlreadyLockedValue_shouldThrow()
      throws ValueAlreadyLockedException {
    when(mapLock.lock(LOCK_VALUE)).thenThrow(ValueAlreadyLockedException.class);

    subject.lockAndCall(LOCK_VALUE, runnable);
  }
}
