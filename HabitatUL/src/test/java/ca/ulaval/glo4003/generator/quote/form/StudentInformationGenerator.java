package ca.ulaval.glo4003.generator.quote.form;

import ca.ulaval.glo4003.gateway.presentation.quote.request.StudentInformationRequest;
import ca.ulaval.glo4003.underwriting.domain.quote.form.studentinformation.StudentInformation;
import com.github.javafaker.Faker;

public class StudentInformationGenerator {
  public static StudentInformationRequest createStudentInformationRequest() {
    return new StudentInformationRequest(
        createIdul(), createIdentificationNumber(), createProgram());
  }

  public static StudentInformation createStudentInformation() {
    return new StudentInformation(createIdentificationNumber(), createIdul(), createProgram());
  }

  private static String createIdul() {
    return Faker.instance().name().username();
  }

  private static String createIdentificationNumber() {
    return Faker.instance().idNumber().valid();
  }

  private static String createProgram() {
    return Faker.instance().cat().name();
  }
}
