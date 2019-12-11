package ca.ulaval.glo4003.gateway.presentation.common.databind.configuration;

import ca.ulaval.glo4003.administration.domain.user.token.Token;
import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.form.building.PreventionSystems;
import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.Animals;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.*;
import ca.ulaval.glo4003.gateway.presentation.common.databind.serializer.*;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.insuring.domain.claim.LossDeclarations;
import ca.ulaval.glo4003.insuring.domain.claim.SinisterType;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import ca.ulaval.glo4003.insuring.domain.policy.lossratio.LossRatio;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationId;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalId;
import ca.ulaval.glo4003.shared.domain.address.Floor;
import ca.ulaval.glo4003.shared.domain.address.ZipCode;
import ca.ulaval.glo4003.shared.domain.authority.AuthorityNumber;
import ca.ulaval.glo4003.shared.domain.identity.Cycle;
import ca.ulaval.glo4003.shared.domain.identity.Degree;
import ca.ulaval.glo4003.shared.domain.identity.Gender;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import ca.ulaval.glo4003.shared.domain.temporal.Year;
import ca.ulaval.glo4003.shared.presentation.temporal.LocalZoneIdProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleSerializers;

import java.time.ZoneId;

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
    deserializers.addDeserializer(AuthorityNumber.class, new AuthorityNumberDeserializer());
    deserializers.addDeserializer(CivilLiabilityLimit.class, new CivilLiabilityLimitDeserializer());
    deserializers.addDeserializer(Cycle.class, new CycleDeserializer());
    deserializers.addDeserializer(Date.class, new DateDeserializer());
    deserializers.addDeserializer(Degree.class, new DegreeDeserializer());
    deserializers.addDeserializer(Floor.class, new FloorDeserializer());
    deserializers.addDeserializer(Gender.class, new GenderDeserializer());
    deserializers.addDeserializer(LossDeclarations.class, new LossDeclarationsDeserializer());
    deserializers.addDeserializer(LossRatio.class, new LossRatioDeserializer());
    deserializers.addDeserializer(PreventionSystems.class, new PreventionSystemsDeserializer());
    deserializers.addDeserializer(SinisterType.class, new SinisterTypeDeserializer());
    deserializers.addDeserializer(Year.class, new YearDeserializer());
    deserializers.addDeserializer(ZipCode.class, new ZipCodeDeserializer());
    setupContext.addDeserializers(deserializers);
  }

  private void setSerializers(SetupContext setupContext) {
    SimpleSerializers serializers = new SimpleSerializers();
    serializers.addSerializer(Amount.class, new AmountSerializer());
    serializers.addSerializer(AuthorityNumber.class, new AuthorityNumberSerializer());
    serializers.addSerializer(ClaimId.class, new ClaimIdSerializer());
    serializers.addSerializer(CoverageDetails.class, new CoverageDetailsSerializer());
    serializers.addSerializer(Date.class, new DateSerializer());
    serializers.addSerializer(DateTime.class, new DateTimeSerializer(getLocalZoneId()));
    serializers.addSerializer(Money.class, new MoneySerializer());
    serializers.addSerializer(PolicyId.class, new PolicyIdSerializer());
    serializers.addSerializer(PolicyModificationId.class, new PolicyModificationIdSerializer());
    serializers.addSerializer(PolicyRenewalId.class, new PolicyRenewalIdSerializer());
    serializers.addSerializer(PremiumDetails.class, new PremiumDetailsSerializer());
    serializers.addSerializer(QuoteId.class, new QuoteIdSerializer());
    serializers.addSerializer(Token.class, new TokenSerializer());
    setupContext.addSerializers(serializers);
  }

  private ZoneId getLocalZoneId() {
    LocalZoneIdProvider provider = ServiceLocator.resolve(LocalZoneIdProvider.class);
    return provider.getLocalZoneId();
  }
}
