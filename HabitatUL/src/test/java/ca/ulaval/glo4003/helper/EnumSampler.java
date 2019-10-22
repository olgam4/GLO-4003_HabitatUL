package ca.ulaval.glo4003.helper;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EnumSampler {
  public static <T extends Enum<T>> List<T> sample(Class<T> enumeration, int size) {
    return IntStream.range(0, size).mapToObj(i -> sample(enumeration)).collect(Collectors.toList());
  }

  public static <T extends Enum<T>> T sample(Class<T> enumeration) {
    T[] values = enumeration.getEnumConstants();
    Random random = new Random();
    return values[random.nextInt(values.length)];
  }
}
