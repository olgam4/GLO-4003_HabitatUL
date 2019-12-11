package ca.ulaval.glo4003.administration.infrastructure.user.credential;

import ca.ulaval.glo4003.administration.domain.user.credential.PasswordManager;
import ca.ulaval.glo4003.administration.domain.user.credential.PasswordManagerIT;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.security.NoSuchAlgorithmException;

@RunWith(MockitoJUnitRunner.class)
public class PbkdfPasswordManagerIT extends PasswordManagerIT {
  @Override
  public PasswordManager createSubject() {
    try {
      return new PbkdfPasswordManager(new InMemoryPbkdfPasswordStorage());
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    }
  }
}
