package LocationServer;

import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;

import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReadConfigTest {

//  Test that the readConfig method returns the expected map when the config file contains valid data:
  @Test
  public void testReadConfigCheckFileValidation()
      throws ClassNotFoundException, InstantiationException, IllegalAccessException,
          NoSuchMethodException, InvocationTargetException {
    Class<?> locationServerClass = Class.forName("main.LocationServer");
    Object locationServer = locationServerClass.newInstance();

    Method readConfig = locationServerClass.getDeclaredMethod("readConfig");
    readConfig.setAccessible(true);

    LinkedHashMap<String, String> result =
        (LinkedHashMap<String, String>) readConfig.invoke(locationServer);

    LinkedHashMap<String, String> expected = new LinkedHashMap<>();
    expected.put("A", "Indoor");
    expected.put("B", "Indoor");
    expected.put("C", "Outdoor");
    expected.put("D", "Outdoor");

    assertEquals(expected, result);
  }

  //  Test that the readConfig method returns a non-empty map when the config file exists:
  @Test
  public void testReadConfigCreateAFileAndRead() throws Exception {
    Class<?> locationServerClass = Class.forName("main.LocationServer");
    Object locationServer = locationServerClass.newInstance();

    // create a temporary config file with sample data
    File configFile = File.createTempFile("LocationServerConfig", null);
    configFile.deleteOnExit();
    try (PrintWriter writer = new PrintWriter(configFile)) {
      writer.println("ON: A, B, C");
      writer.println("OFF: D, E, F");
    }

    // invoke the private method using reflection
    Method readConfig = locationServerClass.getDeclaredMethod("readConfig");
    readConfig.setAccessible(true);
    Object result = readConfig.invoke(null);

    // check that the method returns a non-empty LinkedHashMap
    assertTrue(result instanceof LinkedHashMap);
    LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) result;
    assertFalse(map.isEmpty());
  }

  @Test
  public void testReadConfigFileNotFound() throws Exception {
    Class<?> locationServerClass = Class.forName("main.LocationServer");
    Object locationServer = locationServerClass.newInstance();
    // delete any existing config file
    File configFile = new File("LocationServerConfig");
    configFile.delete();

    // invoke the private method using reflection
    Method readConfig = locationServerClass.getDeclaredMethod("readConfig");
    readConfig.setAccessible(true);
    Object result = readConfig.invoke(null);

    // check that the method returns an empty LinkedHashMap
    assertTrue(result instanceof LinkedHashMap);
    LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) result;
    assertTrue(map.isEmpty());
  }
  
  @After
  public void tearDown() throws Exception {
    // Recreate the test config file
    try (PrintWriter writer = new PrintWriter("LocationServerConfig", "UTF-8")) {
      writer.println("Indoor: A,B");
      writer.println("Outdoor: C,D");
    }
  }
  }
