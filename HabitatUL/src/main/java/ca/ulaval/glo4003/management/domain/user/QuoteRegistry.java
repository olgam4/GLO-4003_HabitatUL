package ca.ulaval.glo4003.management.domain.user;

import java.util.List;

public interface QuoteRegistry {
  void register(String userKey, String quoteKey);

  String getUserKey(String quoteKey);

  List<String> getQuoteKeys(String userKey);
}
