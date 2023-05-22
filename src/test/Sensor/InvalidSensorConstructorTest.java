package Sensor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import support.Sensor;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertThrows;

@RunWith(Parameterized.class)
public class InvalidSensorConstructorTest {

    @Parameterized.Parameter(0)
    public String invalidName;

    @Parameterized.Parameter(1)
    public String invalidType;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{
                {"Huy", "AQI"},
                {"!@#!$", "Temperature"},
                {"Jack", "Locaton"},
                {"David", ""},
                {"", "Temperature"},
                {"David", "////"}};
        return Arrays.asList(data);
    }

    private Sensor sensor;

    @Before
    public void setUp() throws FileNotFoundException {
        sensor = new Sensor(invalidName, invalidType);
    }

    @Test(expected = FileNotFoundException.class)
    public void testInvalidSensorConstructor() {
        String name = invalidName;
        String type = invalidType;

        assertThrows(FileNotFoundException.class, () -> {
            new Sensor(name, "AQI");
        });

        assertThrows(FileNotFoundException.class, () -> {
            new Sensor("Jack", type);
        });

        assertThrows(FileNotFoundException.class, () -> {
            new Sensor(name, type);
        });
    }

}
