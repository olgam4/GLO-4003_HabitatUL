package ca.ulaval.glo4003;

import ca.ulaval.glo4003.gateway.presentation.databind.JacksonFeature;
import ca.ulaval.glo4003.underwriting.infrastructure.http.CORSResponseFilter;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class Server {
  public static final String CONTEXT_PATH = "/v1";
  private org.eclipse.jetty.server.Server server;
  private ContextHandlerCollection contextHandlerCollection;

  public void start(int serverPort) {
    server = new org.eclipse.jetty.server.Server(serverPort);
    contextHandlerCollection = new ContextHandlerCollection();
    server.setHandler(contextHandlerCollection);
    serverStart();
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

  public void addResourceConfig(ResourceConfig resourceConfig) {
    ServletContextHandler servletContextHandler =
        createServletContextHandlerFromResourceConfig(resourceConfig);
    startServletContextHandler(servletContextHandler);
  }

  private void startServletContextHandler(ServletContextHandler servletContextHandler) {
    try {
      servletContextHandler.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private ServletContextHandler createServletContextHandlerFromResourceConfig(
      ResourceConfig resourceConfig) {
    ServletContextHandler servletContextHandler =
        new ServletContextHandler(
            contextHandlerCollection, CONTEXT_PATH, ServletContextHandler.SESSIONS);
    resourceConfig.register(CORSResponseFilter.class);
    resourceConfig.register(JacksonFeature.class);
    ServletContainer servletContainer = new ServletContainer(resourceConfig);
    ServletHolder servletHolder = new ServletHolder(servletContainer);
    servletContextHandler.addServlet(servletHolder, "/*");
    return servletContextHandler;
  }

  public void join() {
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

  public void reset() {
    Handler[] handlers = contextHandlerCollection.getHandlers();
    if (handlers != null) {
      for (Handler handler : handlers) {
        try {
          handler.stop();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

  public void stop() {
    try {
      server.stop();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
