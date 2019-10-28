package ca.ulaval.glo4003;

import ca.ulaval.glo4003.context.AcceptanceTestContext;

public class AcceptanceTestHelper {
  public static void initialize() {
    new AcceptanceTestContext().execute();
  }
}
