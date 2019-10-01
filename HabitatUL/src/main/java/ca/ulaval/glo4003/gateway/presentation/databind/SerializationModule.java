package ca.ulaval.glo4003.gateway.presentation.databind;

import ca.ulaval.glo4003.gateway.presentation.databind.deserializer.DateDeserializer;
import ca.ulaval.glo4003.gateway.presentation.databind.deserializer.GenderDeserializer;
import ca.ulaval.glo4003.shared.domain.DateTime;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Gender;
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
    deserializers.addDeserializer(DateTime.class, new DateDeserializer());
    deserializers.addDeserializer(Gender.class, new GenderDeserializer());
    setupContext.addDeserializers(deserializers);
  }

  private void setSerializers(SetupContext setupContext) {
    SimpleSerializers serializers = new SimpleSerializers();
    setupContext.addSerializers(serializers);
  }
}
