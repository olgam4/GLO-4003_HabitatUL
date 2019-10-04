package ca.ulaval.glo4003.gateway.presentation.databind;

import ca.ulaval.glo4003.coverage.domain.claim.ClaimId;
import ca.ulaval.glo4003.coverage.domain.claim.LossDeclarations;
import ca.ulaval.glo4003.coverage.domain.claim.SinisterType;
import ca.ulaval.glo4003.gateway.presentation.databind.deserializer.*;
import ca.ulaval.glo4003.gateway.presentation.databind.serializer.*;
import ca.ulaval.glo4003.management.domain.user.credential.Credentials;
import ca.ulaval.glo4003.management.domain.user.token.Token;
import ca.ulaval.glo4003.shared.domain.Amount;
import ca.ulaval.glo4003.shared.domain.Date;
import ca.ulaval.glo4003.shared.domain.DateTime;
import ca.ulaval.glo4003.underwriting.domain.premium.Premium;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Gender;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.Floor;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.PostalCode;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.Animals;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleSerializers;

public class SerializationModule extends Module {
  @Override
  public String getModuleName() {
    return this.getClass().getName();
  }

  @Override
  public Version version() {
    return Version.unknownVersion();
  }

  @Override
  public void setupModule(SetupContext setupContext) {
    setSerializers(setupContext);
    setDeserializers(setupContext);
  }

  private void setDeserializers(SetupContext setupContext) {
    SimpleDeserializers deserializers = new SimpleDeserializers();
    deserializers.addDeserializer(Amount.class, new AmountDeserializer());
    deserializers.addDeserializer(Animals.class, new AnimalsDeserializer());
    deserializers.addDeserializer(Credentials.class, new CredentialsDeserializer());
    deserializers.addDeserializer(Date.class, new DateDeserializer());
    deserializers.addDeserializer(DateTime.class, new DateTimeDeserializer());
    deserializers.addDeserializer(Floor.class, new FloorDeserializer());
    deserializers.addDeserializer(Gender.class, new GenderDeserializer());
    deserializers.addDeserializer(LossDeclarations.class, new LossDeclarationsDeserializer());
    deserializers.addDeserializer(PostalCode.class, new PostalCodeDeserializer());
    deserializers.addDeserializer(SinisterType.class, new SinisterTypeDeserializer());
    setupContext.addDeserializers(deserializers);
  }

  private void setSerializers(SetupContext setupContext) {
    SimpleSerializers serializers = new SimpleSerializers();
    serializers.addSerializer(ClaimId.class, new ClaimIdSerializer());
    serializers.addSerializer(Date.class, new DateSerializer());
    serializers.addSerializer(DateTime.class, new DateTimeSerializer());
    serializers.addSerializer(Premium.class, new PremiumSerializer());
    serializers.addSerializer(QuoteId.class, new QuoteIdSerializer());
    serializers.addSerializer(Token.class, new TokenSerializer());
    setupContext.addSerializers(serializers);
  }
}
