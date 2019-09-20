package ca.ulaval.glo4003;

import ca.ulaval.glo4003.api.contact.ContactResource;
import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.http.CORSResponseFilter;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class Server {
  public static final String CONTEXT_PATH = "/api/";
  private org.eclipse.jetty.server.Server server;

  public void start(int serverPort) {
    server = new org.eclipse.jetty.server.Server(serverPort);
    configureServer(server);
    serverStart();
  }

  private void configureServer(org.eclipse.jetty.server.Server server) {
    ContactResource contactResource = ServiceLocator.resolve(ContactResource.class);

    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath(CONTEXT_PATH);
    ResourceConfig resourceConfig =
        ResourceConfig.forApplication(
            new Application() {
              @Override
              public Set<Object> getSingletons() {
                HashSet<Object> resources = new HashSet<>();
                resources.add(contactResource);
                return resources;
              }
            });
    resourceConfig.register(CORSResponseFilter.class);

    ServletContainer servletContainer = new ServletContainer(resourceConfig);
    ServletHolder servletHolder = new ServletHolder(servletContainer);
    context.addServlet(servletHolder, "/*");

    ContextHandlerCollection contexts = new ContextHandlerCollection();
    contexts.setHandlers(new Handler[] {context});
    server.setHandler(contexts);
  }

  private void serverStart() {
    try {
      server.start();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      tryStopServer();
    }
  }

  public void join() {
    serverJoin();
  }

  private void serverJoin() {
    try {
      server.join();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      tryStopServer();
    }
  }

  private void tryStopServer() {
    try {
      server.destroy();
    } catch (Exception e) {
      return;
    }
  }

  public void stop() {
    serverStop();
  }

  private void serverStop() {
    try {
      server.stop();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
