package ca.ulaval.glo4003;

import ca.ulaval.glo4003.context.ProdContext;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.Optional;

@SuppressWarnings("all")
public class Main {
  private static final String PORT_ENV_VAR = "port";
  private static final String DEFAULT_PORT = "8080";
  private static final ProdContext DEFAULT_CONTEXT = new ProdContext();
  private static final String RESOURCE_PACKAGE = "ca.ulaval.glo4003.gateway.presentation";

  public static void main(String[] args) {
    new ProdContext().execute();
    Server server = createServer();
    registerResources(server);
  }

  private static Server createServer() {
    Server server = new Server();
    server.start(retrievePortNumber());
    server.join();
    return server;
  }

  private static Integer retrievePortNumber() {
    String portNumber = Optional.ofNullable(System.getProperty(PORT_ENV_VAR)).orElse(DEFAULT_PORT);
    return Integer.valueOf(portNumber);
  }

  private static void registerResources(Server server) {
    ResourceConfig resourceConfig = new ResourceConfig();
    resourceConfig.packages(RESOURCE_PACKAGE);
    server.addResourceConfig(resourceConfig);
  }
}
