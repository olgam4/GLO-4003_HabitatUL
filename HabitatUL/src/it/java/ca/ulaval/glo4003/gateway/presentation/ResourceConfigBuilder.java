package ca.ulaval.glo4003.gateway.presentation;

import org.glassfish.jersey.server.ResourceConfig;

import java.util.HashSet;
import java.util.Set;

public class ResourceConfigBuilder {
  private Set<Object> resources = new HashSet<>();
  private Set<Class> errorMappers = new HashSet<>();

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

  public ResourceConfig build() {
    ResourceConfig resourceConfig = new ResourceConfig();
    for (Object resource : resources) {
      resourceConfig.register(resource);
    }
    for (Class errorMapper : errorMappers) {
      resourceConfig.register(errorMapper);
    }
    return resourceConfig;
  }
}
