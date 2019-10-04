package ca.ulaval.glo4003.management.persistence.user;

import ca.ulaval.glo4003.management.domain.user.QuoteRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryQuoteRegistry implements QuoteRegistry {
  private Map<String, List<String>> quoteKeysByUserKey = new HashMap<>();
  private Map<String, String> userKeyByQuoteKey = new HashMap<>();

  @Override
  public void register(String userKey, String quoteKey) {
    List<String> quoteKeys = quoteKeysByUserKey.getOrDefault(userKey, new ArrayList<>());
    quoteKeys.add(quoteKey);
    quoteKeysByUserKey.put(userKey, quoteKeys);
    userKeyByQuoteKey.put(quoteKey, userKey);
  }

  @Override
  public String getUserKey(String quoteKey) {
    return userKeyByQuoteKey.get(quoteKey);
  }

  @Override
  public List<String> getQuoteKeys(String userKey) {
    return quoteKeysByUserKey.get(userKey);
  }
}
