package ca.ulaval.glo4003.management.domain.user;

import java.util.ArrayList;
import java.util.List;

public class User {
  private UserId userId;
  private String username;
  private List<QuoteKey> quotes = new ArrayList<>();

  public User(UserId userId, String username) {
    this.userId = userId;
    this.username = username;
  }

  public UserId getUserId() {
    return userId;
  }

  public String getUsername() {
    return username;
  }

  public void associate(QuoteKey quoteKey) {
    quotes.add(quoteKey);
  }

  public List<QuoteKey> getQuotes() {
    return new ArrayList<>(quotes);
  }
}
