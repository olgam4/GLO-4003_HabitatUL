package ca.ulaval.glo4003.administration.domain.user;

import java.util.List;

public interface QuoteRegistry {
  void register(String userKey, String quoteKey);

  String getUserKey(String quoteKey);

  List<String> getQuoteKeys(String userKey);
}
