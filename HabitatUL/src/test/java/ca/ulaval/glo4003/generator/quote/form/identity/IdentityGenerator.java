package ca.ulaval.glo4003.generator.quote.form.identity;

import ca.ulaval.glo4003.gateway.presentation.quote.request.IdentityView;
import ca.ulaval.glo4003.shared.domain.DateTime;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Gender;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Identity;
import com.github.javafaker.Faker;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class IdentityGenerator {
  public static IdentityView createIdentityView() {
    // TODO: remove duplicate
    // TODO: add createIdentityDto
    // TODO: use assemblers to pass from entity to dto to view such as QuoteForm
    Faker faker = Faker.instance();
    DateTime birthDate =
        DateTime.from(
            LocalDateTime.ofInstant(faker.date().birthday().toInstant(), ZoneId.systemDefault()));
    return new IdentityView(
        faker.name().firstName(), faker.name().lastName(), birthDate, Gender.OTHER);
  }

  public static Identity createIdentity() {
    Faker faker = Faker.instance();
    return new Identity(faker.name().firstName(), faker.name().lastName(), Gender.OTHER);
  }
}
