package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer;

import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidPreventionSystemsError;
import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;
import ca.ulaval.glo4003.underwriting.domain.quote.form.building.PreventionSystem;
import ca.ulaval.glo4003.underwriting.domain.quote.form.building.PreventionSystems;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class PreventionSystemsDeserializer extends JsonDeserializer<PreventionSystems> {
  private String inputValue;

  @Override
  public PreventionSystems deserialize(
      JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
    JsonNode nodes = jsonParser.getCodec().readTree(jsonParser);
    inputValue = nodes.toString();
    enforceNodeType(nodes, JsonNodeType.ARRAY);
    return convertValueSafely(nodes);
  }

  private void enforceNodeType(JsonNode node, JsonNodeType nodeType)
      throws InvalidPreventionSystemsError {
    if (!node.getNodeType().equals(nodeType)) {
      throw new InvalidPreventionSystemsError(inputValue);
    }
  }

  private PreventionSystems convertValueSafely(JsonNode nodes)
      throws InvalidPreventionSystemsError {
    return new PreventionSystems(parseNodes(nodes));
  }

  private Set<PreventionSystem> parseNodes(JsonNode nodes) throws InvalidPreventionSystemsError {
    Set<PreventionSystem> preventionSystems = new HashSet<>();
    for (JsonNode node : nodes) {
      enforceNodeType(node, JsonNodeType.STRING);
      parseNode(preventionSystems, node);
    }
    return preventionSystems;
  }

  private void parseNode(Set<PreventionSystem> preventionSystems, JsonNode node)
      throws InvalidPreventionSystemsError {
    String value = node.textValue();
    try {
      preventionSystems.add(PreventionSystem.getEnum(value));
    } catch (InvalidArgumentException e) {
      throw new InvalidPreventionSystemsError(inputValue);
    }
  }
}
