package Sensor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import support.Sensor;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ValidSensorConstructorTest {

    @Parameterized.Parameter(0)
    public String name;

    @Parameterized.Parameter(1)
    public String type;
    @Parameterized.Parameter(2)
    public String expectedName;
    @Parameterized.Parameter(3)
    public String expectedType;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{
                {"David", "AQI", "David", "AQI"},
                {"David", "Temperature", "David", "Temperature"},
                {"David", "Location", "David", "Location"},
                {"Jack", "AQI", "Jack", "AQI"},
                {"Jack", "Temperature", "Jack", "Temperature"},
                {"Jack", "Location", "Jack", "Location"}};
        return Arrays.asList(data);
    }

    private Sensor sensor;

    @Before
    public void setUp() {
        sensor = new Sensor(name, type);
    }

    @Test
    public void testGetUserName() {
        assertEquals(expectedName, sensor.getUsername());
    }

    @Test
    public void testGetType() {
        assertEquals(expectedType, sensor.getType());
    }
}
