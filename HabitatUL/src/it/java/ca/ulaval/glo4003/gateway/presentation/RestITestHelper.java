package ca.ulaval.glo4003.gateway.presentation;

import ca.ulaval.glo4003.Server;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ca.ulaval.glo4003.Server.CONTEXT_PATH;
import static io.restassured.RestAssured.given;

public class RestITestHelper {
  private static final int TEST_SERVER_PORT = 9292;
  private static final String BASE_URI =
      String.format("http://localhost:%s%s", TEST_SERVER_PORT, CONTEXT_PATH);

  private static Server server;

  private RestITestHelper() {}

  public static void startServer() {
    if (server == null) {
      server = new Server();
      server.start(TEST_SERVER_PORT);
    }
  }

  public static void addResourceConfig(ResourceConfig resourceConfig) {
    server.addResourceConfig(resourceConfig);
  }

  public static void stopServer() {
    if (server != null) {
      server.stop();
    }
  }

  public static RequestSpecification getBaseScenario() {
    return given()
        .port(TEST_SERVER_PORT)
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .basePath(CONTEXT_PATH);
  }

  public static String toUri(String... pathParts) {
    List<String> uriPartsArray = new ArrayList<>(Arrays.asList(pathParts));
    uriPartsArray.add(0, BASE_URI);
    return joinPath(uriPartsArray);
  }

  public static String toPath(String... pathParts) {
    List<String> pathPartsArray = new ArrayList<>(Arrays.asList(pathParts));
    return joinPath(pathPartsArray);
  }

  private static String joinPath(List<String> pathParts) {
    List<String> strippedUriParts =
        pathParts.stream()
            .map(uriPart -> StringUtils.strip(uriPart, "/"))
            .collect(Collectors.toList());
    return String.join("/", strippedUriParts);
  }
}
