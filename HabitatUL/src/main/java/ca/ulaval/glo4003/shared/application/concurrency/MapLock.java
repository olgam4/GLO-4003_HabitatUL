package ca.ulaval.glo4003.shared.application.concurrency;

import ca.ulaval.glo4003.shared.application.concurrency.exception.ValueAlreadyLockedException;

public class MapLock<T> {
  private ConcurrentHashSet<T> values;

  public MapLock() {
    values = new ConcurrentHashSet<>();
  }

  public LockHandle<T> lock(T value) throws ValueAlreadyLockedException {
    if (!values.add(value)) {
      throw new ValueAlreadyLockedException();
    }

    return new LockHandle<>(this, value);
  }

  private void unlock(T value) {
    values.remove(value);
  }

  static class LockHandle<U> {
    private final MapLock<U> mapLock;
    private final U value;

    private LockHandle(MapLock<U> mapLock, U value) {
      this.mapLock = mapLock;
      this.value = value;
    }

    public void unlock() {
      mapLock.unlock(value);
    }
  }
}
