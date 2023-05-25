package Integration;

import helper.*;
import main.AllSensors;
import main.ContextCoordinator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import utils.CC_Utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;

/*
    Run LocationServer, PreferenceRepository, WeatherAlarm, ContextCoordinatior before running  this test class
 */
@RunWith(Parameterized.class)
public class CC_AQI {
    static ContextCoordinatorWorker ccW = new ContextCoordinator.ContextCoordinatorWorkerI();
    static Field usersField;

    @Parameterized.Parameter
    public String username;


    @Parameterized.Parameter(1)
    public int aqi;

    @Parameterized.Parameter(2)
    public int expectedAPOThreshold;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Jack", 20, 40},
                {"Jack", 50, 30},
                {"Jack", 75, 20},
                {"David", 100, 15},
                {"David", 0, 60},

        });
    }
    @BeforeClass
    public static void setUp() throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        CC_Utils.initCC_AllWorkers();
        CC_Utils.initCC_CityInfo();
        CC_Utils.accessField("currentWeather");
        CC_Utils.runMethod("runWeatherAlarm");
        usersField = CC_Utils.accessField("users");
        ccW.addUser("Jack", null);
        ccW.addUser("David", null);
    }


    @Test
    public void test() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        SensorData sensorData = new SensorData(username, "A", 30, aqi);
        Monitor m = new ContextCoordinator.MonitorI();
        m.report(sensorData, null);
        LinkedHashMap<String, User> users = (LinkedHashMap<String, User>) usersField.get(null);
        assertEquals(sensorData, users.get(username).sensorData);
        assertEquals(expectedAPOThreshold, users.get(username).apoThreshhold);

    }
}
