package ca.ulaval.glo4003.gateway.presentation.databind;

import ca.ulaval.glo4003.gateway.presentation.databind.deserializer.DateTimeDeserializer;
import ca.ulaval.glo4003.gateway.presentation.databind.deserializer.FloorDeserializer;
import ca.ulaval.glo4003.gateway.presentation.databind.deserializer.GenderDeserializer;
import ca.ulaval.glo4003.gateway.presentation.databind.deserializer.PostalCodeDeserializer;
import ca.ulaval.glo4003.gateway.presentation.databind.serializer.DateTimeSerializer;
import ca.ulaval.glo4003.gateway.presentation.databind.serializer.PremiumSerializer;
import ca.ulaval.glo4003.gateway.presentation.databind.serializer.QuoteIdSerializer;
import ca.ulaval.glo4003.shared.domain.DateTime;
import ca.ulaval.glo4003.underwriting.domain.premium.Premium;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Gender;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.Floor;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.PostalCode;
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
    deserializers.addDeserializer(DateTime.class, new DateTimeDeserializer());
    deserializers.addDeserializer(PostalCode.class, new PostalCodeDeserializer());
    deserializers.addDeserializer(Floor.class, new FloorDeserializer());
    deserializers.addDeserializer(Gender.class, new GenderDeserializer());
    setupContext.addDeserializers(deserializers);
  }

  private void setSerializers(SetupContext setupContext) {
    SimpleSerializers serializers = new SimpleSerializers();
    serializers.addSerializer(QuoteId.class, new QuoteIdSerializer());
    serializers.addSerializer(DateTime.class, new DateTimeSerializer());
    serializers.addSerializer(Premium.class, new PremiumSerializer());
    setupContext.addSerializers(serializers);
  }
}
