package ca.ulaval.glo4003.helper;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EnumSampler {
  public static <T extends Enum<T>> List<T> sample(Class<T> enumeration, int size) {
    return IntStream.range(0, size).mapToObj(i -> sample(enumeration)).collect(Collectors.toList());
  }

  public static <T extends Enum<T>> T sample(Class<T> enumeration) {
    List<T> values = Arrays.asList(enumeration.getEnumConstants());
    return sampleValue(values);
  }

  public static <T extends Enum<T>> T sample(Class<T> enumeration, List<T> exceptions) {
    List<T> values = Arrays.asList(enumeration.getEnumConstants());
    values.removeAll(exceptions);
    return sampleValue(values);
  }

  private static <T extends Enum<T>> T sampleValue(List<T> values) {
    Random random = new Random();
    return values.get(random.nextInt(values.size()));
  }
}
