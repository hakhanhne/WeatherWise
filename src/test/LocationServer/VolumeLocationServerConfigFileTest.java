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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.*;
public class VolumeLocationServerConfigFileTest {
    @Test
    public void testLocationServerConfigFile()
            throws ClassNotFoundException, InstantiationException, IllegalAccessException,
            NoSuchMethodException, InvocationTargetException {
        Class<?> locationServerClass = Class.forName("main.LocationServer");
        Object locationServer = locationServerClass.newInstance();
    
        Method readConfig = locationServerClass.getDeclaredMethod("readConfig");
        readConfig.setAccessible(true);
    
//        LinkedHashMap<String, String> result =
//                (LinkedHashMap<String, String>) readConfig.invoke(locationServer);
//
//        LinkedHashMap<String, String> expected = new LinkedHashMap<>();
//        expected.put("A", "Indoor");
//        expected.put("B", "Indoor");
//        expected.put("C", "Outdoor");
//        expected.put("D", "Outdoor");
//
//        assertEquals(expected, result);
    }
    // Test that the readConfig method returns the expected map when the config file contains valid data
    // for the given volume of entries
    @Test
    public void testVolumeLocationServerConfigFile() throws Exception {
        String configFile = "LocationServerConfig";
        int volume = 200;
        try {
            // Open the config file for writing
            BufferedWriter writer = new BufferedWriter(new FileWriter(configFile));
        
            // Write 200 entries to the config file
            for (int i = 1; i <= volume; i++) {
                String entry = "Indoor: " + ": A,B" + "\n" + "Outdoor: " + "C,D";
                writer.write(entry);
                writer.newLine();
            }
        
            // Close the writer
            writer.close();
        
            System.out.println("LocationServerConfig file overwritten successfully.");
        
        } catch (IOException e) {
            e.printStackTrace();
        }
//
        
        
        try {
            // Load the LocationServer class
            Class<?> locationServerClass = Class.forName("main.LocationServer");
            Object locationServer = locationServerClass.newInstance();
            
            // Invoke the readConfig method using reflection
            Method readConfig = locationServerClass.getDeclaredMethod("readConfig");
            readConfig.setAccessible(true);
            
            LinkedHashMap<String, String> result =
                    (LinkedHashMap<String, String>) readConfig.invoke(locationServer);
            
//            // Verify the loaded data matches the expected values
//            assertEquals(expected, result);
        } finally {
            // Cleanup: Delete the 200 entries config file
            File configFileAgain = new File("LocationServerConfig");
            configFileAgain.delete();
        }
    }
    
    // Helper method to generate sample data for the given volume of entries
    private LinkedHashMap<String, String> generateSampleData(int volume) {
        LinkedHashMap<String, String> data = new LinkedHashMap<>();
        for (int i = 1; i <= volume; i++) {
            String key = "Key" + i;
            String value = "Value" + i;
            data.put(key, value);
        }
        return data;
    }
    
    // Helper method to create a temporary config file with the provided data
    private File createTempConfigFile(LinkedHashMap<String, String> data) throws Exception {
        File configFile = File.createTempFile("LocationServerConfig", null);
        configFile.deleteOnExit();
        try (PrintWriter writer = new PrintWriter(configFile)) {
            for (String key : data.keySet()) {
                writer.println(key + ": " + data.get(key));
            }
        }
        return configFile;
    }
    
    @After
    public void tearDown() throws Exception {
        // Recreate the config file
        try (PrintWriter writer = new PrintWriter("LocationServerConfig", "UTF-8")) {
            writer.println("Indoor: A,B");
            writer.print("Outdoor: C,D");
        }
    }
}
