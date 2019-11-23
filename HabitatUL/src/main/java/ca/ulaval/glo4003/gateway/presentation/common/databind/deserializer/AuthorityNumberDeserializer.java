package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidAuthorityNumber;
import ca.ulaval.glo4003.shared.domain.authority.AuthorityNumber;
import ca.ulaval.glo4003.shared.domain.authority.AuthorityNumberFormatter;
import ca.ulaval.glo4003.shared.domain.authority.AuthorityNumberParser;
import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.io.IOException;

public class AuthorityNumberDeserializer extends JsonDeserializer<AuthorityNumber> {
  private AuthorityNumberParser authorityNumberParser;
  private AuthorityNumberFormatter authorityNumberFormatter;
  private String inputValue;

  public AuthorityNumberDeserializer() {
    this.authorityNumberFormatter = ServiceLocator.resolve(AuthorityNumberFormatter.class);
    this.authorityNumberParser = ServiceLocator.resolve(AuthorityNumberParser.class);
  }

  @Override
  public AuthorityNumber deserialize(
      JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    inputValue = node.toString();
    enforceNodeType(node);
    return convertValueSafely(node);
  }

  private void enforceNodeType(JsonNode node) throws InvalidAuthorityNumber {
    if (!node.getNodeType().equals(JsonNodeType.STRING)) {
      throw new InvalidAuthorityNumber(inputValue);
    }
  }

  private AuthorityNumber convertValueSafely(JsonNode node) throws InvalidAuthorityNumber {
    try {
      return new AuthorityNumber(node.textValue(), authorityNumberFormatter, authorityNumberParser);
    } catch (InvalidArgumentException e) {
      throw new InvalidAuthorityNumber(inputValue);
    }
  }
}
