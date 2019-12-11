package ca.ulaval.glo4003.administration.infrastructure.user.credential;

import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public abstract class PbkdfPasswordStorageIT {
  private static final String KEY = Faker.instance().internet().uuid();
  private static final Secret SECRET =
      new Secret(Faker.instance().internet().uuid(), Faker.instance().internet().uuid());
  private static final Secret ANOTHER_SECRET =
      new Secret(Faker.instance().internet().uuid(), Faker.instance().internet().uuid());

  private PbkdfPasswordStorage subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void fetchingSecret_withNotExistingKey_shouldBeNull() {
    assertNull(subject.fetch(KEY));
  }

  @Test
  public void fetchingSecret_withExistingKey_shouldReturnAssociatedSecret() {
    subject.store(KEY, SECRET);

    Secret secret = subject.fetch(KEY);

    assertEquals(SECRET, secret);
  }

  @Test
  public void storingSecret_withAlreadyExistingKey_shouldOverrideAssociatedSecret() {
    subject.store(KEY, SECRET);
    subject.store(KEY, ANOTHER_SECRET);

    Secret secret = subject.fetch(KEY);

    assertEquals(ANOTHER_SECRET, secret);
  }

  protected abstract PbkdfPasswordStorage createSubject();
}
