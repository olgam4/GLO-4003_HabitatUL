package ca.ulaval.glo4003.gateway.presentation.common.handling.error.generic;

import ca.ulaval.glo4003.gateway.presentation.common.handling.MultipleMessagesErrorMappingIT;
import ca.ulaval.glo4003.helper.shared.EnumSampler;
import com.github.javafaker.Faker;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ElementKind;
import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ConstraintViolationErrorMappingIT extends MultipleMessagesErrorMappingIT {
  private static final String FIRST_ERROR_MESSAGE = Faker.instance().rickAndMorty().quote();
  private static final String FIRST_ERROR_FIRST_PROPERTY_NODE_NAME =
      Faker.instance().rickAndMorty().character();
  private static final String FIRST_ERROR_SECOND_PROPERTY_NODE_NAME =
      Faker.instance().rickAndMorty().location();
  private static final String SECOND_ERROR_MESSAGE = Faker.instance().leagueOfLegends().quote();
  private static final String SECOND_ERROR_FIRST_PROPERTY_NODE_NAME =
      Faker.instance().leagueOfLegends().champion();
  private static final String SECOND_ERROR_SECOND_PROPERTY_NODE_NAME =
      Faker.instance().leagueOfLegends().location();
  private static final String SECOND_ERROR_THIRD_PROPERTY_NODE_NAME =
      Faker.instance().leagueOfLegends().masteries();

  @Override
  public Throwable getError() {
    DummyConstraintViolation firstViolation =
        createViolation(
            FIRST_ERROR_MESSAGE,
            FIRST_ERROR_FIRST_PROPERTY_NODE_NAME,
            FIRST_ERROR_SECOND_PROPERTY_NODE_NAME);
    DummyConstraintViolation secondViolation =
        createViolation(
            SECOND_ERROR_MESSAGE,
            SECOND_ERROR_FIRST_PROPERTY_NODE_NAME,
            SECOND_ERROR_SECOND_PROPERTY_NODE_NAME,
            SECOND_ERROR_THIRD_PROPERTY_NODE_NAME);
    return new ConstraintViolationException(
        new HashSet<>(Arrays.asList(firstViolation, secondViolation)));
  }

  private DummyConstraintViolation createViolation(String message, String... nodesName) {
    List<Path.Node> nodes =
        Arrays.stream(nodesName).map(PropertyDummyNode::new).collect(Collectors.toList());
    for (int i = 0; i < Faker.instance().number().randomDigit(); i++) {
      int index = Faker.instance().number().numberBetween(0, nodes.size());
      nodes.add(index, new NotAPropertyDummyNode());
    }
    return new DummyConstraintViolation(message, new DummyPath(nodes));
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "INVALID_REQUEST_FORMAT";
  }

  @Override
  public Set<String> getErrorMessagesMatcher() {
    return new HashSet<>(
        Arrays.asList(
            getConstraintViolationMessage(
                FIRST_ERROR_MESSAGE,
                FIRST_ERROR_FIRST_PROPERTY_NODE_NAME,
                FIRST_ERROR_SECOND_PROPERTY_NODE_NAME),
            getConstraintViolationMessage(
                SECOND_ERROR_MESSAGE,
                SECOND_ERROR_FIRST_PROPERTY_NODE_NAME,
                SECOND_ERROR_SECOND_PROPERTY_NODE_NAME,
                SECOND_ERROR_THIRD_PROPERTY_NODE_NAME)));
  }

  private String getConstraintViolationMessage(String message, String... nodesName) {
    return String.join(" ", String.join(".", nodesName), message);
  }

  static class PropertyDummyNode extends DummyNode {
    PropertyDummyNode(String name) {
      super(name, ElementKind.PROPERTY);
    }
  }

  static class NotAPropertyDummyNode extends DummyNode {
    NotAPropertyDummyNode() {
      super(Faker.instance().lordOfTheRings().character(), getElementKind());
    }

    private static ElementKind getElementKind() {
      ElementKind elementKind = ElementKind.PROPERTY;
      while (elementKind.equals(ElementKind.PROPERTY)) {
        elementKind = EnumSampler.sample(ElementKind.class);
      }
      return elementKind;
    }
  }

  static class DummyNode implements Path.Node {
    private final String name;
    private final ElementKind kind;

    DummyNode(String name, ElementKind kind) {
      this.name = name;
      this.kind = kind;
    }

    @Override
    public String getName() {
      return name;
    }

    @Override
    public boolean isInIterable() {
      return false;
    }

    @Override
    public Integer getIndex() {
      return null;
    }

    @Override
    public Object getKey() {
      return null;
    }

    @Override
    public ElementKind getKind() {
      return kind;
    }

    @Override
    public <T extends Path.Node> T as(Class<T> nodeType) {
      return null;
    }
  }

  static class DummyPath implements Path {
    List<Node> nodes;

    DummyPath(List<Node> nodes) {
      this.nodes = nodes;
    }

    @Override
    public Iterator<Node> iterator() {
      return nodes.iterator();
    }

    @Override
    public void forEach(Consumer<? super Node> consumer) {}

    @Override
    public Spliterator<Node> spliterator() {
      return null;
    }
  }

  static class DummyConstraintViolation implements ConstraintViolation<Object> {
    private Path propertyPath;
    private String message;

    DummyConstraintViolation(String message, Path propertyPath) {
      this.propertyPath = propertyPath;
      this.message = message;
    }

    @Override
    public String getMessage() {
      return message;
    }

    @Override
    public String getMessageTemplate() {
      return null;
    }

    @Override
    public Object getRootBean() {
      return null;
    }

    @Override
    public Class getRootBeanClass() {
      return null;
    }

    @Override
    public Object getLeafBean() {
      return null;
    }

    @Override
    public Object[] getExecutableParameters() {
      return new Object[0];
    }

    @Override
    public Object getExecutableReturnValue() {
      return null;
    }

    @Override
    public Path getPropertyPath() {
      return propertyPath;
    }

    @Override
    public Object getInvalidValue() {
      return null;
    }

    @Override
    public ConstraintDescriptor<?> getConstraintDescriptor() {
      return null;
    }

    @Override
    public Object unwrap(Class type) {
      return null;
    }
  }
}
