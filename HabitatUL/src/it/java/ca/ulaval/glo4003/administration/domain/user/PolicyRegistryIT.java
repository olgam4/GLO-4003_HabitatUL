package ca.ulaval.glo4003.administration.domain.user;

import ca.ulaval.glo4003.administration.domain.user.exception.KeyNotFoundException;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class PolicyRegistryIT {
  private static final String USER_KEY = Faker.instance().book().author();
  private static final String NOT_EXISTING_USER_KEY = Faker.instance().aviation().aircraft();
  private static final String POLICY_KEY = Faker.instance().cat().name();
  private static final String NOT_EXISTING_POLICY_KEY = Faker.instance().dragonBall().character();

  private PolicyRegistry subject;

  @Before
  public void setUp() {
    subject = createSubject();
    subject.register(USER_KEY, POLICY_KEY);
  }

  @Test
  public void gettingUserKey_withRegisteredPolicyKey_shouldReturnMappedUserKey()
      throws KeyNotFoundException {
    assertEquals(USER_KEY, subject.getUserKey(POLICY_KEY));
  }

  @Test(expected = KeyNotFoundException.class)
  public void gettingUserKey_withNotExistingPolicyKey_shouldThrow() throws KeyNotFoundException {
    subject.getUserKey(NOT_EXISTING_POLICY_KEY);
  }

  @Test
  public void gettingPolicyKeys_withRegisteredUserKey_shouldReturnAllMappedPolicyKeys() {
    List<String> quoteKeys = subject.getPolicyKeys(USER_KEY);

    assertEquals(Arrays.asList(POLICY_KEY), quoteKeys);
  }

  @Test
  public void gettingPolicyKeys_withNotExistingUserKey_shouldReturnEmptyList() {
    List<String> quoteKeys = subject.getPolicyKeys(NOT_EXISTING_USER_KEY);

    assertEquals(0, quoteKeys.size());
  }

  public abstract PolicyRegistry createSubject();
}
