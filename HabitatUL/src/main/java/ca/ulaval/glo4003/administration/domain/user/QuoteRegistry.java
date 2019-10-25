package ca.ulaval.glo4003.administration.domain.user;

import ca.ulaval.glo4003.administration.domain.user.exception.KeyNotFoundException;

import java.util.List;

public interface QuoteRegistry {
  void register(String userKey, String quoteKey);

  String getUserKey(String quoteKey) throws KeyNotFoundException;

  List<String> getQuoteKeys(String userKey);
}
