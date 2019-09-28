package ca.ulaval.glo4003.gateway.presentation;

import org.glassfish.jersey.server.ResourceConfig;

import java.util.HashSet;
import java.util.Set;

public class ResourceConfigBuilder {
  private Set<Object> resources = new HashSet<>();

  private ResourceConfigBuilder() {}

  public static ResourceConfigBuilder aResourceConfig() {
    return new ResourceConfigBuilder();
  }

  public ResourceConfigBuilder withResource(Object resource) {
    resources.add(resource);
    return this;
  }

  public ResourceConfig build() {
    ResourceConfig resourceConfig = new ResourceConfig();
    for (Object resource : resources) {
      resourceConfig.register(resource);
    }
    return resourceConfig;
  }
}
