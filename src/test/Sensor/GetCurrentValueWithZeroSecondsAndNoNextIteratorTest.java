package Sensor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import support.Sensor;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Assert;

@RunWith(Parameterized.class)
public class GetCurrentValueWithZeroSecondsAndNoNextIteratorTest {
    @Parameterized.Parameter(0)
    public String name;
    @Parameterized.Parameter(1)
    public String type;
    @Parameterized.Parameter(2)
    public Integer seconds;
    @Parameterized.Parameter(3)
    public Integer loops;
    @Parameterized.Parameter(4)
    public String expectedValue;
    @Parameterized.Parameter(5)
    public Integer expectedSeconds;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{
                {"David", "AQI", 0, 2, "200", 14},
                {"David", "Location", 0, 3, "A", 0},
                {"David", "Temperature", 0, 3, "10", 4},
                {"Jack", "AQI", 0, 2, "200", 14},
                {"Jack", "Location", 0, 3, "A", 0},
                {"Jack", "Temperature", 0, 3, "10", 4}
        };
        return Arrays.asList(data);
    }

    private Sensor sensor;

    @Before
    public void setUp() {
        sensor = new Sensor(name, type);
    }

    @Test
    public void testGetCurrentValueWithZeroSecondsAndNoNextIterator() {
        Assert.assertEquals(expectedValue, sensor.getCurrentValue());
    }

    @Test
    public void testGetSecondsWithZeroSecondsAndNoNextIterator() throws NoSuchFieldException, IllegalAccessException {
        Field secondsField = Sensor.class.getDeclaredField("seconds");
        secondsField.setAccessible(true);
        Integer value = (Integer) secondsField.get(sensor);
        value = value -1;
        Assert.assertEquals(expectedSeconds, value);
    }
}
