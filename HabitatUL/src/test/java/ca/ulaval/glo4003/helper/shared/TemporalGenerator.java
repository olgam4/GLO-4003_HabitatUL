package ca.ulaval.glo4003.helper.shared;

import ca.ulaval.glo4003.shared.domain.temporal.*;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import ca.ulaval.glo4003.shared.domain.temporal.Year;
import com.github.javafaker.Faker;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public class TemporalGenerator {
  private TemporalGenerator() {}

  public static Date getNowDate() {
    return getNowDate(getClockProvider());
  }

  public static Date getNowDate(ClockProvider clockProvider) {
    return Date.now(clockProvider.getClock());
  }

  public static Date createDate() {
    return Date.from(createDateTime().getValue().toLocalDate());
  }

  public static Date createPastDate() {
    Instant pastInstant =
        Faker.instance()
            .date()
            .past(Faker.instance().number().randomDigitNotZero(), TimeUnit.DAYS)
            .toInstant();
    LocalDateTime pastDateTime = LocalDateTime.ofInstant(pastInstant, ZoneOffset.UTC);
    return Date.from(pastDateTime.toLocalDate());
  }

  public static Date createFutureDate() {
    Instant futureInstant =
        Faker.instance()
            .date()
            .future(Faker.instance().number().randomDigitNotZero(), TimeUnit.DAYS)
            .toInstant();
    LocalDateTime futureDate = LocalDateTime.ofInstant(futureInstant, ZoneOffset.UTC);
    return Date.from(futureDate.toLocalDate());
  }

  public static Date createDateBetween(Date date1, Date date2) {
    long numberOfDays =
        Faker.instance().number().numberBetween(0, numberDaysBetween(date1, date2) - 1);
    return Date.from(date1.getValue().plusDays(numberOfDays));
  }

  private static long numberDaysBetween(Date date1, Date date2) {
    return Math.abs(date1.getValue().toEpochDay() - date2.getValue().toEpochDay());
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
    LocalDateTime pastDateTime = LocalDateTime.ofInstant(pastInstant, ZoneOffset.UTC);
    return DateTime.from(pastDateTime);
  }

  public static DateTime createFutureDateTime() {
    Instant futureInstant =
        Faker.instance()
            .date()
            .future(Faker.instance().number().randomDigitNotZero(), TimeUnit.DAYS)
            .toInstant();
    LocalDateTime futureDateTime = LocalDateTime.ofInstant(futureInstant, ZoneOffset.UTC);
    return DateTime.from(futureDateTime);
  }

  public static Period createPeriod() {
    return new Period(createDate(), createDate());
  }

  public static Period createPastPeriod() {
    Date endDate = createDateBefore(getNowDate());
    Date startDate = createDateBefore(endDate);
    return new Period(startDate, endDate);
  }

  public static Period createCurrentPeriod() {
    Date startDate = createDateBefore(getNowDate());
    Date endDate = createDateAfter(getNowDate());
    return new Period(startDate, endDate);
  }

  public static Period createFuturePeriod() {
    Date startDate = createDateAfter(getNowDate());
    Date endDate = createDateAfter(startDate);
    return new Period(startDate, endDate);
  }

  public static java.time.Period createJavaTimePeriod() {
    return java.time.Period.ofMonths(Faker.instance().number().randomDigitNotZero());
  }

  public static Duration createDuration() {
    return Duration.of(Faker.instance().number().randomDigitNotZero(), ChronoUnit.MINUTES);
  }

  public static Year createYear() {
    return Year.from(java.time.Year.from(createDateTime().getValue()));
  }

  public static ClockProvider getClockProvider() {
    return new FixedClockProvider();
  }

  private static class FixedClockProvider implements ClockProvider {
    private Instant instant;
    private ZoneId zoneId;

    FixedClockProvider() {
      this(Instant.now(), ZoneOffset.UTC);
    }

    FixedClockProvider(Instant instant, ZoneId zoneId) {
      this.instant = instant;
      this.zoneId = zoneId;
    }

    @Override
    public Clock getClock() {
      return Clock.fixed(instant, zoneId);
    }
  }
}
