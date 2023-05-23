package Integration;

import helper.*;
import main.PreferenceRepository;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import support.Preference;
import utils.CC_Utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.runners.Parameterized.*;

@RunWith(Parameterized.class)
public class CC_PrefRepo_getUserInfo {
    public static PreferenceRepository preferenceRepository;
    public static PreferenceWorkerPrx prx;

    @Parameter
    public static String testName;
    @Parameter(1)
    public static List<Preference> testPreferences;
    @Parameter(2)
    public static String name;
    @Parameter(3)
    public static User expectedUser;


    public static final int apoThreshold = 0;
    public static final int clock = 0;
    public static final SensorData sensorData = null;
    public static final int weather = 0;
    public static final boolean apoReached = false;
    public static final boolean tempReached = false;

    @BeforeClass
    public static void setUpBeforeClass() throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException, IllegalAccessException {
        preferenceRepository = new PreferenceRepository();

        PreferenceRepository.class.getDeclaredField("communicator").setAccessible(true);
        Method preferenceSetup = PreferenceRepository.class.getDeclaredMethod("setupPreferenceWorker", String[].class);
        preferenceSetup.setAccessible(true);
        preferenceSetup.invoke(preferenceRepository, (Object) null);

        CC_Utils.initCC_Communicator();
        Field preferenceWorker = CC_Utils.accessField("preferenceWorker");
        CC_Utils.runMethod("iniPreferenceWorker");
        prx = (PreferenceWorkerPrx) preferenceWorker.get(null);
    }


    @Parameters(name = "{0} - {2}")
    public static Collection<Object[]> data() {
        // multiple preferences
        List<Preference> multiplePreferences = new ArrayList<>();
        multiplePreferences.add(new Preference("Jack", 2, Arrays.asList(
                "when 20 suggest shops",
                "when 30 suggest pool",
                "when APO suggest bowling",
                "when weather suggest cinema"
        )));
        multiplePreferences.add(new Preference("David", 3, Arrays.asList(
                "when 16 suggest pool",
                "when APO suggest cinema",
                "when weather suggest shops"
        )));

        // empty preferences
        List<Preference> emptyPreferences = new ArrayList<>();

        User emptyUser = new User(0, new int[]{}, apoThreshold, clock, sensorData, weather, apoReached, tempReached);

        return Arrays.asList(new Object[][]{
                // multiple preferences
                // exist username
                {"Multiple preferences", multiplePreferences, "Jack", new User(2, new int[]{20, 30}, apoThreshold,
                                                                               clock, sensorData, 0, apoReached, tempReached)},
                {"Multiple preferences", multiplePreferences, "David", new User(3, new int[]{16}, apoThreshold, clock
                        , sensorData, 0, apoReached, tempReached)},
                // non-exist username
                {"Multiple preferences", multiplePreferences, "Nhung", emptyUser},
                // special-case username
                {"Multiple preferences", multiplePreferences, "@@@@@", emptyUser},
                {"Multiple preferences", multiplePreferences, "_", emptyUser},
                {"Multiple preferences", multiplePreferences, "\n", emptyUser},
                {"Multiple preferences", multiplePreferences, " ", emptyUser},
                {"Multiple preferences", multiplePreferences, "", emptyUser},

                // empty preference
                {"Empty preferences", emptyPreferences, "Jack", emptyUser},
                // special-case username
                {"Empty preferences", emptyPreferences, "@@@@@", emptyUser},
                {"Empty preferences", emptyPreferences, "_", emptyUser},
                {"Empty preferences", emptyPreferences, "\n", emptyUser},
                {"Empty preferences", emptyPreferences, " ", emptyUser},
                {"Empty preferences", emptyPreferences, "", emptyUser},
        });
    }

    @Before
    public void setupBeforeTest() throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {

        Field preferencesField = PreferenceRepository.class.getDeclaredField("preferences");
        preferencesField.setAccessible(true);
        // temp set PreferenceRepository.preferences to null
        preferencesField.set(preferenceRepository, testPreferences);
    }
    @Test
    public void testGetUserInfo() throws NoSuchFieldException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        User actualUserInfo = prx.getUserInfo(name);

        System.out.println("---------------------------------------------");
        System.out.println("name: " + name);
        System.out.println("expected medical: " + expectedUser.medicalConditionType +
                                   " - actual medical: " + actualUserInfo.medicalConditionType);
        System.out.println("expected APO: " + expectedUser.apoThreshhold +
                                   " - actual APO: " + actualUserInfo.apoThreshhold);
        System.out.println("expected temperature thresholds: " + Arrays.toString(expectedUser.tempThreshholds) +
                                   " - actual temperature thresholds: " + Arrays.toString(actualUserInfo.tempThreshholds));

        assertEquals("Expected medical condition type: ", expectedUser.medicalConditionType,
                     actualUserInfo.medicalConditionType);
        assertArrayEquals("Expected temp thresholds: ", expectedUser.tempThreshholds, actualUserInfo.tempThreshholds);
        assertEquals("Expected apo threshold: ", expectedUser.apoThreshhold, actualUserInfo.apoThreshhold);

    }
}
