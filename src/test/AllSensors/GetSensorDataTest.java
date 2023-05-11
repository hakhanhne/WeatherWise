package AllSensors;

import static org.junit.Assert.assertEquals;

import helper.SensorData;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import main.AllSensors;
import java.lang.reflect.Field;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class GetSensorDataTest {

    @Parameterized.Parameter(0) public String username;
    @Parameterized.Parameter(1) public String expectedName;
    @Parameterized.Parameter(2) public boolean expectedSignal;
    @Parameterized.Parameter(3) public String expectedLoc;
    @Parameterized.Parameter(4) public int expectedTemp;
    @Parameterized.Parameter(5) public int expectedAQI;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Jack", "Jack", true, "A", 10, 200},
                {"David", "David", true, "A", 10, 200},
        });
    }

    @Test
    public void testGetSensorData() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        AllSensors userSensor = new AllSensors(username);
        Method getSensorData = AllSensors.class.getDeclaredMethod("getSensorData");
        getSensorData.setAccessible(true);
        SensorData data = (SensorData) getSensorData.invoke(userSensor);
        Field signal = AllSensors.class.getDeclaredField("signal");
        signal.setAccessible(true);
        boolean signalRes = (boolean) signal.get(userSensor);
        assertEquals(expectedSignal, signalRes);
        assertEquals(expectedName, data.username);
        assertEquals(expectedLoc, data.location);
        assertEquals(expectedTemp, data.temperature);
        assertEquals(expectedAQI, data.aqi);
    }
}
