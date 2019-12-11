package ca.ulaval.glo4003.administration.infrastructure.user.credential;

public class InMemoryPbkdfPasswordStorageIT extends PbkdfPasswordStorageIT {
  @Override
  protected PbkdfPasswordStorage createSubject() {
    return new InMemoryPbkdfPasswordStorage();
  }
}
