package ca.ulaval.glo4003;

import ca.ulaval.glo4003.context.Context;
import ca.ulaval.glo4003.context.ProdContext;

import java.util.Optional;

@SuppressWarnings("all")
public class Main {
  private static final String PORT_ENV_VAR = "PORT";
  private static final int DEFAULT_PORT = 8080;
  private static final ProdContext DEFAULT_CONTEXT = new ProdContext();

  public static void main(String[] args) {
    Context context = new ProdContext();
    context.execute();
    Server server = new Server();
    server.start(retrievePortNumber());
    server.join();
  }

  private static Integer retrievePortNumber() {
    return Optional.ofNullable(Integer.valueOf(System.getProperty(PORT_ENV_VAR)))
        .orElse(DEFAULT_PORT);
  }
}
