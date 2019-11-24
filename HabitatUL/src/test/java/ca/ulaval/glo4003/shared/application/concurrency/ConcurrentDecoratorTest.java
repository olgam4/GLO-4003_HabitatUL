package ca.ulaval.glo4003.shared.application.concurrency;

import ca.ulaval.glo4003.shared.application.concurrency.MapLock.LockHandle;
import ca.ulaval.glo4003.shared.application.concurrency.error.ConcurrentAccessError;
import ca.ulaval.glo4003.shared.application.concurrency.exception.ValueAlreadyLockedException;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ConcurrentDecoratorTest {
  private static final String LOCK_VALUE = Faker.instance().internet().uuid();
  private static final String SUPPLIER_RESULT = Faker.instance().internet().uuid();
  private static final RuntimeException EXCEPTION = new RuntimeException();

  @Mock private MapLock<String> mapLock;
  @Mock private LockHandle<String> lockHandle;
  @Mock private Runnable runnable;
  @Mock private Supplier<String> supplier;

  private ConcurrentDecorator<String> subject;

  @Before
  public void setUp() throws ValueAlreadyLockedException {
    when(supplier.get()).thenReturn(SUPPLIER_RESULT);
    when(mapLock.lock(LOCK_VALUE)).thenReturn(lockHandle);
    subject = new ConcurrentDecorator<>(mapLock);
  }

  @Test
  public void lockingAndCallingRunnable_shouldLockAndUnlock() throws ValueAlreadyLockedException {
    subject.lockAndCall(LOCK_VALUE, runnable);

    verify(mapLock).lock(LOCK_VALUE);
    verify(lockHandle).unlock();
  }

  @Test
  public void lockingAndCallingSupplier_shouldLockAndUnlock() throws ValueAlreadyLockedException {
    subject.lockAndCall(LOCK_VALUE, supplier);

    verify(mapLock).lock(LOCK_VALUE);
    verify(lockHandle).unlock();
  }

  @Test
  public void lockingAndCallingRunnable_withThrowingRunnable_shouldLockAndUnlock()
      throws ValueAlreadyLockedException {
    Mockito.doThrow(EXCEPTION).when(runnable).run();

    callSafely(runnable);

    verify(mapLock).lock(LOCK_VALUE);
    verify(lockHandle).unlock();
  }

  @Test
  public void lockingAndCallingSupplier_withThrowingSupplier_shouldLockAndUnlock()
      throws ValueAlreadyLockedException {
    Mockito.doThrow(EXCEPTION).when(supplier).get();

    callSafely(supplier);

    verify(mapLock).lock(LOCK_VALUE);
    verify(lockHandle).unlock();
  }

  @Test
  public void lockingAndCallingRunnable_shouldExecuteRunnable() {
    subject.lockAndCall(LOCK_VALUE, runnable);

    verify(runnable).run();
  }

  @Test
  public void lockingAndCallingSupplier_shouldReturnSupplierResult() {
    String result = subject.lockAndCall(LOCK_VALUE, supplier);

    assertEquals(SUPPLIER_RESULT, result);
  }

  @Test
  public void lockingAndCallingRunnable_withThrowingRunnable_shouldThrowSameException() {
    Mockito.doThrow(EXCEPTION).when(runnable).run();

    try {
      subject.lockAndCall(LOCK_VALUE, runnable);
    } catch (Exception e) {
      assertEquals(EXCEPTION, e);
    }
  }

  @Test
  public void lockingAndCallingSupplier_withThrowingSupplier_shouldThrowSameException() {
    Mockito.doThrow(EXCEPTION).when(supplier).get();

    try {
      subject.lockAndCall(LOCK_VALUE, supplier);
    } catch (Exception e) {
      assertEquals(EXCEPTION, e);
    }
  }

  @Test(expected = ConcurrentAccessError.class)
  public void lockingAndCallingRunnable_withAlreadyLockedValue_shouldThrow()
      throws ValueAlreadyLockedException {
    when(mapLock.lock(LOCK_VALUE)).thenThrow(ValueAlreadyLockedException.class);

    subject.lockAndCall(LOCK_VALUE, runnable);
  }

  @Test(expected = ConcurrentAccessError.class)
  public void lockingAndCallingSupplier_withAlreadyLockedValue_shouldThrow()
      throws ValueAlreadyLockedException {
    when(mapLock.lock(LOCK_VALUE)).thenThrow(ValueAlreadyLockedException.class);

    subject.lockAndCall(LOCK_VALUE, supplier);
  }

  @Test
  public void lockingAndCallingRunnable_withAlreadyLockedValue_shouldNotExecuteRunnable()
      throws ValueAlreadyLockedException {
    when(mapLock.lock(LOCK_VALUE)).thenThrow(ValueAlreadyLockedException.class);

    callSafely(runnable);

    verify(runnable, never()).run();
  }

  @Test
  public void lockingAndCallingSupplier_withAlreadyLockedValue_shouldNotExecuteSupplier()
      throws ValueAlreadyLockedException {
    when(mapLock.lock(LOCK_VALUE)).thenThrow(ValueAlreadyLockedException.class);

    callSafely(supplier);

    verify(supplier, never()).get();
  }

  private void callSafely(Runnable runnable) {
    try {
      subject.lockAndCall(LOCK_VALUE, runnable);
    } catch (Exception ignored) {
    }
  }

  private void callSafely(Supplier<?> supplier) {
    try {
      subject.lockAndCall(LOCK_VALUE, supplier);
    } catch (Exception ignored) {
    }
  }
}
