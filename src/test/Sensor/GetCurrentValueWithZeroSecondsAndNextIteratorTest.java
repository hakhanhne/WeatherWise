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
public class GetCurrentValueWithZeroSecondsAndNextIteratorTest {
    @Parameterized.Parameter(0)
    public String name;
    @Parameterized.Parameter(1)
    public String type;
    @Parameterized.Parameter(2)
    public Integer seconds;
    @Parameterized.Parameter(3)
    public String expectedValue;
    @Parameterized.Parameter(4)
    public Integer expectedSeconds;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{
                {"David", "AQI", 0, "200", 10},
                {"David", "Location", 0, "A", 14},
                {"David", "Temperature", 0, "10", 2},
                {"Jack", "AQI", 0, "200", 10},
                {"Jack", "Location", 0, "A", 14},
                {"Jack", "Temperature", 0, "10", 2},
        };
        return Arrays.asList(data);
    }

    private Sensor sensor;

    @Before
    public void setUp() {
        sensor = new Sensor(name, type);
    }

    @Test
    public void testGetCurrentValue() throws NoSuchFieldException, IllegalAccessException {
        Field secondsField = Sensor.class.getDeclaredField("seconds");
        secondsField.setAccessible(true);
        while ((Integer) secondsField.get(sensor) > 1) {
            sensor.getCurrentValue();
        }
        assertEquals(expectedValue, sensor.getCurrentValue());
    }

    @Test
    public void testGetSeconds() throws NoSuchFieldException, IllegalAccessException {
        Field secondsField = Sensor.class.getDeclaredField("seconds");
        secondsField.setAccessible(true);
        do {
            sensor.getCurrentValue();
        } while ((Integer) secondsField.get(sensor) != 0);
        sensor.getCurrentValue();
        assertEquals(expectedSeconds, secondsField.get(sensor));
    }
}
