package AllSensors;

import static org.junit.Assert.assertEquals;

import helper.SensorData;

import main.AllSensors;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ValidConstructorTest {
    @Parameterized.Parameter(0)
    public String username;

    @Parameterized.Parameters(name = "{index}: username={0}")
    public static Collection<String> usernames() {
        return Arrays.asList("jack", "david");
    }

    @Test
    public void testConstructor() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        AllSensors userSensors = new AllSensors(username);
        String expectedName = username;
        String expectedLocation = "A";
        int expectedTemperature = 10;
        int expectedUvr = 200;
        Method method = AllSensors.class.getDeclaredMethod("getSensorData");
        method.setAccessible(true);
        SensorData data = (SensorData) method.invoke(userSensors);
        assertEquals(expectedName, data.username);
        assertEquals(expectedTemperature, data.temperature);
        assertEquals(expectedUvr, data.aqi);
        assertEquals(expectedLocation, data.location);
    }
}
