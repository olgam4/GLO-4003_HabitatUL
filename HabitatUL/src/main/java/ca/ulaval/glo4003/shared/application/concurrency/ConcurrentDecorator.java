package ca.ulaval.glo4003.shared.application.concurrency;

import ca.ulaval.glo4003.shared.application.concurrency.MapLock.LockHandle;
import ca.ulaval.glo4003.shared.application.concurrency.error.ConcurrentAccessError;
import ca.ulaval.glo4003.shared.application.concurrency.exception.ValueAlreadyLockedException;

import java.util.function.Supplier;

public class ConcurrentDecorator<T> {
  private MapLock<T> mapLock;

  public ConcurrentDecorator() {
    this(new MapLock<>());
  }

  public ConcurrentDecorator(MapLock<T> mapLock) {
    this.mapLock = mapLock;
  }

  protected <U> U lockAndCall(T lockValue, Supplier<U> supplier) {
    try {
      return supplier.get();
    } finally {
      getLockHandle(lockValue).unlock();
    }
  }

  protected void lockAndCall(T lockValue, Runnable runnable) {
    try {
      runnable.run();
    } finally {
      getLockHandle(lockValue).unlock();
    }
  }

  private LockHandle<T> getLockHandle(T value) {
    try {
      return mapLock.lock(value);
    } catch (ValueAlreadyLockedException e) {
      throw new ConcurrentAccessError();
    }
  }
}
