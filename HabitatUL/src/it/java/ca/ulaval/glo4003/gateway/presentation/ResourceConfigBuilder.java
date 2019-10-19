package ca.ulaval.glo4003.gateway.presentation;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.container.ContainerRequestFilter;
import java.util.HashSet;
import java.util.Set;

public class ResourceConfigBuilder {
  private Set<Object> resources = new HashSet<>();
  private Set<Class> errorMappers = new HashSet<>();
  private Set<ContainerRequestFilter> requestFilters = new HashSet<>();

  private ResourceConfigBuilder() {}

  public static ResourceConfigBuilder aResourceConfig() {
    return new ResourceConfigBuilder();
  }

  public ResourceConfigBuilder withResource(Object resource) {
    resources.add(resource);
    return this;
  }

  public ResourceConfigBuilder withErrorMapper(Class errorMapper) {
    errorMappers.add(errorMapper);
    return this;
  }

  public ResourceConfigBuilder withRequestFilter(ContainerRequestFilter requestFilter) {
    requestFilters.add(requestFilter);
    return this;
  }

  public ResourceConfig build() {
    ResourceConfig resourceConfig = new ResourceConfig();
    resources.forEach(resourceConfig::register);
    errorMappers.forEach(resourceConfig::register);
    requestFilters.forEach(resourceConfig::register);
    return resourceConfig;
  }
}
