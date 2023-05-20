package ContectCoordinator.CCWorker;
import com.zeroc.Ice.Communicator;
import helper.ContextCoordinatorWorker;
import helper.User;
import main.ContextCoordinator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import utils.CC_Utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/*
    Run LocationServer, PreferenceRepository, WeatherAlarm before running this test class
 */
@RunWith(Parameterized.class)
public class AddUserTest {
    static ContextCoordinatorWorker ccW = new ContextCoordinator.ContextCoordinatorWorkerI();
    static Communicator communicator;
    static Field usersField;

    @Parameterized.Parameter
    public String username;

    @Parameterized.Parameter(1)
    public int expectedSize_users;

    @Parameterized.Parameter(2)
    public int expectedMedicalCondition; //medical condition = 0 if user not found

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Jack", 1, 2},
                {"David", 2, 3},
                {"Jack", 2, 2},
                {"Khanh", 3, 0},
                {"jack", 3, 0},
                {"", 4, 0}

        });
    }

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
    public void testAddUser() throws IllegalAccessException, AssertionError, NullPointerException, NoSuchFieldException {
        try {
            ccW.addUser(username, null);
        } catch (com.zeroc.Ice.AlreadyRegisteredException e) {
            fail("user was previously added " + username);
        }

        LinkedHashMap<String, User> usersAfter = (LinkedHashMap<String, User>) usersField.get(null);
        String topicName = username + "-sensors";

        try {
            com.zeroc.IceStorm.TopicManagerPrx topicManager = com.zeroc.IceStorm.TopicManagerPrx.checkedCast(communicator.stringToProxy("IceStorm/TopicManager:tcp -p 10000"));
            topicManager.retrieve(topicName);
            assertEquals("wrong size users:\n"+usersAfter.keySet(), expectedSize_users, usersAfter.size());
            assertEquals("wrong medical condition of user " + username, expectedMedicalCondition, usersAfter.get(username).medicalConditionType);
        } catch (com.zeroc.IceStorm.NoSuchTopic e) {
            fail("failed to find topic " + topicName);
        }
    }

}

