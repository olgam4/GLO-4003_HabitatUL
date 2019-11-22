package ca.ulaval.glo4003.shared.application.concurrency;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashSet<T> {
  private static final Object PRESENT_VALUE = new Object();

  private ConcurrentHashMap<T, Object> values;

  public ConcurrentHashSet() {
    values = new ConcurrentHashMap<>();
  }

  public boolean add(T value) {
    return values.put(value, PRESENT_VALUE) == null;
  }

  public boolean remove(T value) {
    return values.remove(value) == PRESENT_VALUE;
  }
}
