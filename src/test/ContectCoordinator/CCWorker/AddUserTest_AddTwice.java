package ContectCoordinator.CCWorker;

import com.zeroc.Ice.Communicator;
import helper.ContextCoordinatorWorker;
import helper.User;
import main.ContextCoordinator;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.CC_Utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/*
    Run LocationServer, PreferenceRepository, WeatherAlarm before running this test class
 */
public class AddUserTest_AddTwice {
    static ContextCoordinatorWorker ccW = new ContextCoordinator.ContextCoordinatorWorkerI();
    static Communicator communicator;
    static Field usersField;

    @BeforeClass
    public static void setUp() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        communicator = CC_Utils.initCC_Communicator();
        CC_Utils.initCC_AllWorkers();
        CC_Utils.initCC_CityInfo();
        CC_Utils.accessField("currentWeather");
        CC_Utils.runMethod("runWeatherAlarm");
        usersField = CC_Utils.accessField("users");
    }

    @Test
    public void testAddUser_AddTwice() throws AssertionError, NullPointerException, IllegalAccessException {
        String username = "Jack";
        ccW.addUser(username, null);
        assertEquals(1,((LinkedHashMap<String, User>) usersField.get(null)).size());
        ccW.addUser(username, null);
        assertEquals(1,((LinkedHashMap<String, User>) usersField.get(null)).size());
    }
}

