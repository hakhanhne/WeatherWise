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
public class AddUserTest_NotInDatabase {
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
    public void testAddUser_NotInDatabase() throws IllegalAccessException, AssertionError, NullPointerException {
        String username = "Khanh";
        int expectedMedicalCondition = 0;
        ccW.addUser(username, null);
        LinkedHashMap<String, User> usersAfter = (LinkedHashMap<String, User>) usersField.get(null);
        assertEquals(expectedMedicalCondition, usersAfter.get(username).medicalConditionType);
        checkAddTopic(username); //check if user is added in ice cloud also
    }


    static void checkAddTopic(String username) {
        String topicName = username + "-sensors";
        try {
            com.zeroc.IceStorm.TopicManagerPrx topicManager = com.zeroc.IceStorm.TopicManagerPrx.checkedCast(communicator.stringToProxy("IceStorm/TopicManager:tcp -p 10000"));
            topicManager.retrieve(topicName);
        } catch (com.zeroc.IceStorm.NoSuchTopic e) {
            fail("No Such Topic " + topicName);
        }
    }
}

