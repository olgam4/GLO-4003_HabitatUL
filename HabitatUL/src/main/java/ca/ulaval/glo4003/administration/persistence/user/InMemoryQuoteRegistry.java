package ca.ulaval.glo4003.administration.persistence.user;

import ca.ulaval.glo4003.administration.domain.user.QuoteRegistry;
import ca.ulaval.glo4003.administration.domain.user.exception.KeyNotFoundException;

import java.util.*;

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
  public String getUserKey(String quoteKey) throws KeyNotFoundException {
    return Optional.ofNullable(userKeyByQuoteKey.get(quoteKey))
        .orElseThrow(KeyNotFoundException::new);
  }

  @Override
  public List<String> getQuoteKeys(String userKey) {
    return quoteKeysByUserKey.getOrDefault(userKey, new ArrayList<>());
  }
}
