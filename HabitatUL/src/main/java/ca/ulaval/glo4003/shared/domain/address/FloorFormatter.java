package ca.ulaval.glo4003.shared.domain.address;

import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;

public interface FloorFormatter {
  int format(String floorValue) throws InvalidArgumentException;
}
