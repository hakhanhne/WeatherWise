package Sensor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import support.Sensor;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ReadDataTest {
    private Sensor sensor;

    @Parameterized.Parameter(0)
    public String username;
    @Parameterized.Parameter(1)
    public String type;
    @Parameterized.Parameter(2)
    public LinkedHashMap<String, Integer> expectedData;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{
                {"Jack", "AQI", new LinkedHashMap<String, Integer>() {{
                    put("200", 15);
                    put("90", 11);
                }}},
                {"Jack", "Location", new LinkedHashMap<String, Integer>() {{
                    put("A", 1);
                    put("C", 15);
                    put("D", 14);
                }}},
                {"Jack", "Temperature", new LinkedHashMap<String, Integer>() {{
                    put("10", 5);
                    put("15", 3);
                    put("20", 4);
                }}},
                {"David", "AQI", new LinkedHashMap<String, Integer>() {{
                    put("200", 15);
                    put("90", 11);
                }}},
                {"David", "Location", new LinkedHashMap<String, Integer>() {{
                    put("A", 1);
                    put("C", 15);
                    put("D", 14);
                }}},
                {"David", "Temperature", new LinkedHashMap<String, Integer>() {{
                    put("10", 5);
                    put("25", 3);
                    put("20", 4);
                }}},
        };
        return Arrays.asList(data);
    }

    @Before
    public void setUp() {
        sensor = new Sensor(username, type);
    }

    @Test
    public void testReadData() throws FileNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method readData = Sensor.class.getDeclaredMethod("readData");
        readData.setAccessible(true);
        LinkedHashMap<String, Integer> actualData = (LinkedHashMap<String, Integer>) readData.invoke(sensor);
        assertEquals(expectedData, actualData);

    }
}
