package ca.ulaval.glo4003.shared.application.concurrency;

import ca.ulaval.glo4003.shared.application.concurrency.MapLock.LockHandle;
import ca.ulaval.glo4003.shared.application.concurrency.exception.ValueAlreadyLockedException;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class MapLockTest {
  private static final String LOCK_VALUE = Faker.instance().internet().uuid();
  private static final String DIFFERENT_LOCK_VALUE = Faker.instance().internet().uuid();
  private static final int CONCURRENCY_LEVEL = 4;
  private static final int NB_ITERATIONS = 100000;

  private MapLock<String> subject;

  @Before
  public void setUp() {
    subject = new MapLock<>();
  }

  @Test(expected = ValueAlreadyLockedException.class)
  public void lockingValue_withLockedValue_shouldThrow() throws ValueAlreadyLockedException {
    LockHandle<String> handle = subject.lock(LOCK_VALUE);

    subject.lock(LOCK_VALUE);

    handle.unlock();
  }

  @Test
  public void lockingValue_withUnlockedValue_shouldAllowLock() throws ValueAlreadyLockedException {
    LockHandle<String> handle = subject.lock(LOCK_VALUE);
    LockHandle<String> other_handle = subject.lock(DIFFERENT_LOCK_VALUE);

    handle.unlock();
    other_handle.unlock();
  }

  @Test
  public void lockingValue_withValuePreviouslyUnlocked_shouldBeAbleToLockAgain()
      throws ValueAlreadyLockedException {
    LockHandle<String> handle = subject.lock(LOCK_VALUE);
    handle.unlock();

    handle = subject.lock(LOCK_VALUE);
    handle.unlock();
  }

  @Test
  public void lockingValueConcurrently_shouldSucceedOnlyOnce() {
    ExecutorService executor = Executors.newFixedThreadPool(CONCURRENCY_LEVEL);

    IntStream.range(0, NB_ITERATIONS).forEach(i -> assertEquals(1, concurrentLockRace(executor)));
  }

  private int concurrentLockRace(ExecutorService executor) {
    MapLock<String> mapLock = new MapLock<>();

    Callable<Integer> task =
        () -> {
          try {
            mapLock.lock(LOCK_VALUE);
          } catch (ValueAlreadyLockedException e) {
            return 0;
          }
          return 1;
        };

    return IntStream.range(0, CONCURRENCY_LEVEL).mapToObj(i -> executor.submit(task))
        .collect(Collectors.toList()).stream()
        .mapToInt(
            future -> {
              try {
                return future.get();
              } catch (InterruptedException | ExecutionException e) {
                return 0;
              }
            })
        .sum();
  }
}
