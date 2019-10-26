package ca.ulaval.glo4003.helper;

import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import ca.ulaval.glo4003.shared.infrastructure.FixedClockProvider;
import com.github.javafaker.Faker;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public class TemporalGenerator {
  public static Date getNowDate() {
    return Date.now(getClockProvider().getClock());
  }

  public static Date createDate() {
    return Date.from(createDateTime().getValue().toLocalDate());
  }

  public static Date createDateBefore(Date date) {
    return Date.from(
        date.getValue().minus(Faker.instance().number().randomDigitNotZero(), ChronoUnit.DAYS));
  }

  public static Date createDateAfter(Date date) {
    return Date.from(
        date.getValue().plus(Faker.instance().number().randomDigitNotZero(), ChronoUnit.DAYS));
  }

  private static DateTime createDateTime() {
    return DateTime.from(
        LocalDateTime.ofInstant(
            Faker.instance().date().birthday().toInstant(), ZoneId.systemDefault()));
  }

  public static DateTime createPastDateTime() {
    Instant pastInstant =
        Faker.instance()
            .date()
            .past(Faker.instance().number().randomDigitNotZero(), TimeUnit.DAYS)
            .toInstant();
    LocalDateTime pastDate = LocalDateTime.ofInstant(pastInstant, ZoneOffset.UTC);
    return DateTime.from(pastDate);
  }

  public static DateTime createFutureDateTime() {
    Instant futureInstant =
        Faker.instance()
            .date()
            .future(Faker.instance().number().randomDigitNotZero(), TimeUnit.DAYS)
            .toInstant();
    LocalDateTime futureDate = LocalDateTime.ofInstant(futureInstant, ZoneOffset.UTC);
    return DateTime.from(futureDate);
  }

  public static Period createPeriod() {
    return new Period(createDate(), createDate());
  }

  public static Period createPastPeriod() {
    Date endDate = createDateBefore(getNowDate());
    Date startDate = createDateBefore(endDate);
    return new Period(startDate, endDate);
  }

  public static Period createActivePeriod() {
    Date startDate = createDateBefore(getNowDate());
    Date endDate = createDateAfter(getNowDate());
    return new Period(startDate, endDate);
  }

  public static java.time.Period createJavaTimePeriod() {
    return java.time.Period.ofMonths(Faker.instance().number().randomDigitNotZero());
  }

  public static Duration createDuration() {
    return Duration.of(Faker.instance().number().randomDigitNotZero(), ChronoUnit.MINUTES);
  }

  public static ClockProvider getClockProvider() {
    return new FixedClockProvider();
  }
}
