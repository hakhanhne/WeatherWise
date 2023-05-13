package Sensor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import support.Sensor;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class GetCurrentValueTest {
    private Sensor sensor;

    @Parameterized.Parameter(0)
    public String name;
    @Parameterized.Parameter(1)
    public String type;
    @Parameterized.Parameter(2)
    public String expectedValue;
    @Parameterized.Parameter(3)
    public int expectedSeconds;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{
                {"Jack", "AQI", "200", 14},
                {"Jack", "Location", "A", 0},
                {"Jack", "Temperature", "10", 4},
                {"David", "AQI", "200", 14},
                {"David", "Location", "A", 0},
                {"David", "Temperature", "10", 4}};
        return Arrays.asList(data);
    }

    @Before
    public void setUp() {
        sensor = new Sensor(name, type);
    }

    @Test
    public void testGetCurrentValue() {
        assertEquals(expectedValue, sensor.getCurrentValue());
    }

    @Test
    public void testGetSeconds() throws NoSuchFieldException, IllegalAccessException {
        sensor.getCurrentValue();
        Field secondsField = Sensor.class.getDeclaredField("seconds");
        secondsField.setAccessible(true);
        int actualSeconds = (int) secondsField.get(sensor);
        assertEquals(expectedSeconds, actualSeconds);
    }
}
