package ca.ulaval.glo4003.shared.infrastructure.io;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class JsonFileReaderIT {
  private JsonFileReader subject;

  @Before
  public void setUp() {
    subject = new JsonFileReader();
  }

  @Test
  public void readingJsonFile_withExistingFile_shouldReturnContent() {
    JSONObject content = subject.read("test.json");

    JSONObject expectedContent = getTestJsonFileContent();
    assertTrue(content.similar(expectedContent));
  }

  @Test
  public void readingJsonFile_withNotExistingFile_shouldReturnEmptyContent() {
    JSONObject content = subject.read("NOT_EXISTING_FILE");

    assertTrue(content.similar(new JSONObject()));
  }

  private JSONObject getTestJsonFileContent() {
    JSONObject expectedContent = new JSONObject();
    expectedContent.put("key1", "value1");
    expectedContent.put("key2", 0.15);
    JSONArray value3 = new JSONArray();
    value3.put("arrayValue1");
    value3.put("arrayValue2");
    value3.put("arrayValue3");
    expectedContent.put("key3", value3);
    JSONObject value4 = new JSONObject();
    value4.put("subKey1", "subValue1");
    value4.put("subKey2", "subValue2");
    expectedContent.put("key4", value4);
    return expectedContent;
  }
}
