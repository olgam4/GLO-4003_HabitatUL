package ca.ulaval.glo4003.helper.shared;

import ca.ulaval.glo4003.mediator.Event;
import com.github.javafaker.Faker;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EventGenerator {
  private EventGenerator() {}

  public static List<Event> createEvents() {
    return createEvents(Faker.instance().number().randomDigitNotZero());
  }

  public static List<Event> createEvents(int length) {
    return IntStream.range(0, length).mapToObj(i -> createEvent()).collect(Collectors.toList());
  }

  public static Event createEvent() {
    return new DummyEvent();
  }

  private static class DummyEvent extends Event {}
}
