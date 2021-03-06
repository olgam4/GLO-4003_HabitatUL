package ca.ulaval.glo4003.gateway.presentation;

import ca.ulaval.glo4003.coverage.domain.form.building.PreventionSystems;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.Animals;
import ca.ulaval.glo4003.gateway.presentation.administration.user.request.CredentialsRequest;
import ca.ulaval.glo4003.gateway.presentation.coverage.request.*;
import ca.ulaval.glo4003.gateway.presentation.insuring.policy.request.ClaimRequest;
import ca.ulaval.glo4003.gateway.presentation.insuring.policy.request.InsureBicycleRequest;
import ca.ulaval.glo4003.gateway.presentation.insuring.policy.request.ModifyCoverageRequest;
import ca.ulaval.glo4003.gateway.presentation.insuring.policy.request.TriggerRenewalRequest;
import ca.ulaval.glo4003.gateway.presentation.underwriting.quote.request.QuoteRequest;
import ca.ulaval.glo4003.insuring.domain.claim.LossDeclarations;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import org.json.JSONArray;
import org.json.JSONObject;

import static ca.ulaval.glo4003.administration.helper.user.CredentialsGenerator.createCredentialsRequest;
import static ca.ulaval.glo4003.gateway.presentation.IntegrationTestContext.VALID_FLOOR_VALUE;
import static ca.ulaval.glo4003.gateway.presentation.IntegrationTestContext.VALID_ZIP_CODE_VALUE;
import static ca.ulaval.glo4003.insuring.helper.claim.ClaimGenerator.createClaimRequest;
import static ca.ulaval.glo4003.insuring.helper.policy.PolicyGenerator.*;
import static ca.ulaval.glo4003.underwriting.helper.quote.QuoteGenerator.createQuoteRequest;

public class RequestBodyGenerator {

  public static JSONObject createQuoteRequestBody() {
    QuoteRequest quoteRequest = createQuoteRequest();
    JSONObject json = new JSONObject();
    json.put("personalInformation", toRequestBody(quoteRequest.getPersonalInformation()));
    quoteRequest.getAdditionalInsured().map(x -> json.put("additionalInsured", toRequestBody(x)));
    json.put("location", toRequestBody(quoteRequest.getLocation()));
    json.put("effectiveDate", toRequestBody(quoteRequest.getEffectiveDate()));
    json.put("building", toRequestBody(quoteRequest.getBuilding()));
    json.put("personalProperty", toRequestBody(quoteRequest.getPersonalProperty()));
    quoteRequest.getCivilLiability().map(x -> json.put("civilLiability", toRequestBody(x)));
    return json;
  }

  private static JSONObject toRequestBody(IdentityRequest identityRequest) {
    JSONObject json = new JSONObject();
    json.put("firstName", identityRequest.getFirstName());
    json.put("lastName", identityRequest.getLastName());
    json.put("birthDate", toRequestBody(identityRequest.getBirthDate()));
    json.put("gender", identityRequest.getGender().toString());
    identityRequest
        .getUniversityProfile()
        .map(x -> json.put("universityProfile", toRequestBody(x)));
    return json;
  }

  private static JSONObject toRequestBody(UniversityProfileRequest universityProfileRequest) {
    JSONObject json = new JSONObject();
    json.put("idul", universityProfileRequest.getIdul());
    json.put("ni", universityProfileRequest.getNi());
    json.put("program", toRequestBody(universityProfileRequest.getProgram()));
    return json;
  }

  private static JSONObject toRequestBody(UniversityProgramRequest universityProgramRequest) {
    JSONObject json = new JSONObject();
    json.put("cycle", universityProgramRequest.getCycle().getValue());
    json.put("degree", universityProgramRequest.getDegree().getValue());
    json.put("major", universityProgramRequest.getMajor());
    return json;
  }

  private static JSONObject toRequestBody(LocationRequest locationRequest) {
    JSONObject json = new JSONObject();
    json.put("zipCode", VALID_ZIP_CODE_VALUE);
    json.put("streetNumber", locationRequest.getStreetNumber());
    json.put("apartmentNumber", locationRequest.getApartmentNumber());
    json.put("floor", VALID_FLOOR_VALUE);
    return json;
  }

  private static JSONObject toRequestBody(BuildingRequest buildingRequest) {
    JSONObject json = new JSONObject();
    json.put("numberOfUnits", buildingRequest.getNumberOfUnits());
    buildingRequest
        .getPreventionSystems()
        .map(x -> json.put("preventionSystems", toRequestBody(x)));
    buildingRequest.getCommercialUse().map(x -> json.put("commercialUse", x));
    return json;
  }

  private static JSONArray toRequestBody(PreventionSystems preventionSystems) {
    JSONArray json = new JSONArray();
    preventionSystems.getCollection().forEach(x -> json.put(x.toString()));
    return json;
  }

  private static JSONObject toRequestBody(PersonalPropertyRequest personalPropertyRequest) {
    JSONObject json = new JSONObject();
    json.put("coverageAmount", personalPropertyRequest.getCoverageAmount().getValue());
    personalPropertyRequest.getAnimals().map(x -> json.put("animals", toRequestBody(x)));
    personalPropertyRequest.getBicycle().map(x -> json.put("bicycle", toRequestBody(x)));
    return json;
  }

  private static JSONArray toRequestBody(Animals animals) {
    JSONArray json = new JSONArray();
    animals
        .getCollection()
        .forEach(
            (key, value) ->
                json.put(new JSONObject().put("breed", key.toString()).put("quantity", value)));
    return json;
  }

  private static JSONObject toRequestBody(BicycleRequest bicycleRequest) {
    JSONObject json = new JSONObject();
    json.put("price", bicycleRequest.getPrice().getValue());
    json.put("brand", bicycleRequest.getBrand());
    json.put("model", bicycleRequest.getModel());
    json.put("year", bicycleRequest.getYear().getValue());
    return json;
  }

  private static JSONObject toRequestBody(CivilLiabilityRequest civilLiabilityRequest) {
    JSONObject json = new JSONObject();
    json.put("limit", civilLiabilityRequest.getLimit().getRepresentation());
    return json;
  }

  public static JSONObject createCredentialRequestBody() {
    CredentialsRequest credentialsRequest = createCredentialsRequest();
    JSONObject json = new JSONObject();
    json.put("username", credentialsRequest.getUsername());
    json.put("password", credentialsRequest.getPassword());
    return json;
  }

  public static JSONObject createInsureBicycleRequestBody() {
    InsureBicycleRequest insureBicycleRequest = createInsureBicycleRequest();
    JSONObject json = new JSONObject();
    json.put("bicycle", toRequestBody(insureBicycleRequest.getBicycle()));
    return json;
  }

  public static JSONObject createModifyCoverageRequestBody() {
    ModifyCoverageRequest modifyCoverageRequest = createModifyCoverageRequest();
    JSONObject json = new JSONObject();
    json.put("personalProperty", modifyCoverageRequest.getPersonalProperty().getValue());
    json.put("civilLiability", modifyCoverageRequest.getCivilLiability().getRepresentation());
    return json;
  }

  public static JSONObject createTriggerRenewalRequestBody() {
    TriggerRenewalRequest triggerRenewalRequest = createTriggerRenewalRequest();
    JSONObject json = new JSONObject();
    json.put("personalProperty", triggerRenewalRequest.getPersonalProperty().getValue());
    return json;
  }

  public static JSONObject createClaimRequestBody() {
    ClaimRequest claimRequest = createClaimRequest();
    JSONObject json = new JSONObject();
    json.put("sinisterType", claimRequest.getSinisterType().toString());
    json.put("lossDeclarations", toRequestBody(claimRequest.getLossDeclarations()));
    return json;
  }

  private static JSONArray toRequestBody(LossDeclarations lossDeclarations) {
    JSONArray json = new JSONArray();
    lossDeclarations
        .getCollection()
        .forEach(
            (key, value) ->
                json.put(
                    new JSONObject()
                        .put("category", key.toString())
                        .put("amount", value.getValue().floatValue())));
    return json;
  }

  private static String toRequestBody(Date date) {
    return date.getValue().toString();
  }
}
