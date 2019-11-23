package ca.ulaval.glo4003.shared.domain.authority;

import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;
import ca.ulaval.glo4003.shared.domain.temporal.Date;

public interface AuthorityNumberParser {
  Date parseDate(String value) throws InvalidArgumentException;
}
