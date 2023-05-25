package ContectCoordinator.Monitor;

import com.zeroc.Ice.Communicator;
import helper.ContextCoordinatorWorker;
import helper.Monitor;
import helper.SensorData;
import helper.User;
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
    Run LocationServer, PreferenceRepository, WeatherAlarm before running this test class
 */
@RunWith(Parameterized.class)
public class ReportTest {
    static ContextCoordinatorWorker ccW = new ContextCoordinator.ContextCoordinatorWorkerI();

    static Field usersField;

    @Parameterized.Parameter
    public SensorData sensorData;

    @Parameterized.Parameter(1)
    public boolean expectedTempReached;

    @Parameterized.Parameter(2)
    public int expectedApoThreshold;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new SensorData("Jack", "A", 15, 25), false, 30},
                {new SensorData("Jack", "B", 20, 30), true, 30},
                {new SensorData("David", "C", 0, 40), false, 45},
                {new SensorData("David", "D", -1, 0), false, 60},
                {new SensorData("David", "D", 16, 0), true, 60},
        });
    }

    @BeforeClass
    public static void setUp() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        CC_Utils.initCC_Communicator();
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
        Monitor m = new ContextCoordinator.MonitorI();
        m.report(sensorData, null);
        LinkedHashMap<String, User> users = (LinkedHashMap<String, User>) usersField.get(null);
        assertEquals(sensorData, users.get(sensorData.username).sensorData);
        assertEquals(expectedTempReached, users.get(sensorData.username).tempReached);
        assertEquals("APO is wrong", expectedApoThreshold, users.get(sensorData.username).apoThreshhold);
    }
}
