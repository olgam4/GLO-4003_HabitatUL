package ca.ulaval.glo4003.shared.infrastructure.io;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class JsonFileReader {
  private static final Charset ENCODING = StandardCharsets.UTF_8;

  public JSONObject read(String filePath) {
    try {
      InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
      String fileContent = streamToString(inputStream, ENCODING);
      return new JSONObject(fileContent);
    } catch (Exception e) {
      return new JSONObject();
    }
  }

  private String streamToString(InputStream inputStream, Charset charset) {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset));
    return bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
  }
}
