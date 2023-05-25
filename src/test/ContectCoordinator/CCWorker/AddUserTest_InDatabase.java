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
public class AddUserTest_InDatabase {
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
    public void testAddUser_InDatabase() throws IllegalAccessException, AssertionError, NullPointerException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException {
        String[] username = new String[] {"Jack", "David"};
        int[] expectedSize = new int[] {1,2};
        int[] expectedMedicalCondition = new int[] {2,3};

        for (int i=0; i<2; i++) {
            ccW.addUser(username[i], null);
            checkAddTopic(username[i]);
            LinkedHashMap<String, User> usersAfter = (LinkedHashMap<String, User>) usersField.get(null);
            assertEquals("wrong size users:\n"+usersAfter.keySet(), expectedSize[i], usersAfter.size());
            assertEquals("wrong medical condition of user " + username, expectedMedicalCondition[i], usersAfter.get(username[i]).medicalConditionType);
        }
    }

    static void checkAddTopic(String username) {
        String topicName = username + "-sensors";
        try {
            com.zeroc.IceStorm.TopicManagerPrx topicManager = com.zeroc.IceStorm.TopicManagerPrx.checkedCast(communicator.stringToProxy("IceStorm/TopicManager:tcp -p 10000"));
            topicManager.retrieve(topicName);
            System.out.println("found topic " + topicName);
        } catch (com.zeroc.IceStorm.NoSuchTopic e) {
            fail("failed to find topic " + topicName);
        }
    }
}

