package ContectCoordinator.CCWorker;
import helper.ContextCoordinatorWorker;
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
import static org.junit.Assert.fail;

/*
    Run LocationServer, PreferenceRepository, WeatherAlarm before running this test class
 */
@RunWith(Parameterized.class)
public class DeleteUserTest {
    static ContextCoordinatorWorker ccW = new ContextCoordinator.ContextCoordinatorWorkerI();
    static Field usersField;

    @Parameterized.Parameter
    public String username;

    @Parameterized.Parameter(1)
    public int expectedSize;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Jack", 1},
                {"David", 1},
                {"jack", 1},
                {"Khanh", 2},
                {"", 2},
                {null, 2},
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
    }

    @Before
    public void beforeEachTest() throws NoSuchFieldException, IllegalAccessException {
        //refresh users list
        CC_Utils.initCC_Communicator();
        ccW.addUser("Jack", null);
        ccW.addUser("David", null);
    }


    @Test
    public void testDeleteUser() throws IllegalAccessException, AssertionError {
        System.out.println("size before delete:" + ((LinkedHashMap<String, User>) usersField.get(null)).size());
        System.out.println("trying to delete: " + username);

        ccW.deleteUser(username, null);
        System.out.println("deleted: " + username);
        LinkedHashMap<String, User> usersAfter = (LinkedHashMap<String, User>) usersField.get(null);
        System.out.println("size after delete:" + usersAfter.size() + "\n");
        assertEquals("wrong size:\n" + usersAfter, expectedSize, usersAfter.size());
    }
}

