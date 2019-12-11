package ca.ulaval.glo4003.administration.infrastructure.user.credential;

public class Secret {
  private String salt;
  private String hash;

  public Secret(String salt, String hash) {
    this.salt = salt;
    this.hash = hash;
  }

  public String getSalt() {
    return salt;
  }

  public String getHash() {
    return hash;
  }
}
