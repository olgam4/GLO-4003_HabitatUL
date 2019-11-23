package ca.ulaval.glo4003.shared.domain.authority;

import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;

public interface AuthorityNumberFormatter {
  String format(String value) throws InvalidArgumentException;
}
