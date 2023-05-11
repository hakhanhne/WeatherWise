package AllSensors;

import static org.junit.Assert.assertEquals;

import helper.SensorData;

import main.AllSensors;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class InvalidConstructorTest {
    @Parameterized.Parameter(0)
    public String invalidUsername;

    @Parameterized.Parameters(name = "{index}: invalidUsername={0}")
    public static Collection<String> data() {
        return Arrays.asList("abc", "anna", "RMIT", "\r", "n", "", " ", "+++");
    }

    @Test(expected = FileNotFoundException.class)
    public void testInvalidConstructorWithInvalidUsername() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        AllSensors userSensors = new AllSensors(invalidUsername);
        String expectedName = invalidUsername;
        String expectedLocation = "A";
        int expectedTemperature = 10;
        int expectedUvr = 200;
        Method getSensorData = AllSensors.class.getDeclaredMethod("getSensorData");
        getSensorData.setAccessible(true);
        SensorData data = (SensorData) getSensorData.invoke(userSensors);
        assertEquals(expectedName, data.username);
        assertEquals(expectedTemperature, data.temperature);
        assertEquals(expectedUvr, data.aqi);
        assertEquals(expectedLocation, data.location);
    }
}
