package main.PreferenceRepositoryTests;

import helper.SensorData;
import helper.User;
import main.PreferenceRepository;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import support.Preference;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.runners.Parameterized.*;

import java.lang.reflect.Field;
import java.util.*;


import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class PreferenceRepositoryTestGetUserInfo {

    // Test inputs for the parameterized test
    @Parameter
    public static String testName;
    @Parameter(1)
    public static String name;
    @Parameter(2)
    public static List<Preference> testPreferences;
    @Parameter(3)
    public static User expectedUserInfo;

    private static PreferenceRepository preferenceRepository;
    public static int apoThreshhold;
    public static int clock;
    public static SensorData sensorData;
    public static int weather;
    public static boolean apoReached;
    public static boolean tempReached;

    // init
    @BeforeClass
    public static void setUpTest() {
        preferenceRepository = new PreferenceRepository();

        apoThreshhold = 0;
        clock = 0;
        sensorData = null;
        weather = 0;
        apoReached = false;
        tempReached = false;
    }

    @Parameters(name = "{0}: {1}")
    public static Collection<Object[]> data() {
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

        return Arrays.asList(new Object[][] {
                // Test case 1: Empty preferences
                {"Empty pref list", "Jack", new ArrayList<>(),
                        new User(0, new int[0], 0, 0, new SensorData(), 0, false, false)
                },
                // Test case 2: Multiple users
                // Jack
                {"Non-empty pref list", "Jack", multiplePreferences,
                        new User(2, new int[]{20, 30}, 0, 0, new SensorData(), 0, false, false)
                },
                // David
                {"Non-empty pref list", "David", multiplePreferences,
                        new User(3, new int[]{16}, 0, 0, new SensorData(), 0, false, false)
                },
                // Test case 3: Multiple users - no match
                {"Non-empty pref list but no match", "John", multiplePreferences,
                        new User(0, new int[0], 0, 0, new SensorData(), 0, false, false)
                },
                // Test case 4: Non-empty list - empty temperature preferences
                {"Non-empty pref list but empty temperature preferences", "Jack", new ArrayList<>(Arrays.asList(
                        new Preference("Jack", 2, Arrays.asList(
                                "when APO suggest bowling",
                                "when weather suggest cinema"
                        )))),
                        new User(2, new int[0], 0, 0, new SensorData(), 0, false, false)
                },
                // Test case 5: Non-empty list - 1 invalid over 2 temperature preferences
                // Above Upperbound
                {"Non-empty pref list but 1 invalid over 2 temperature preferences", "Jack", new ArrayList<>(Arrays.asList(
                        new Preference("Jack", 2, Arrays.asList(
                                "when APO suggest bowling",
                                "when 51 suggest shops",
                                "when 30 suggest pool",
                                "when weather suggest cinema"
                        )))),
                        new User(2, new int[]{30}, 0, 0, new SensorData(), 0, false, false)
                },
                // Below Lower bound
                {"Non-empty pref list but 1 invalid over 2 temperature preferences", "Jack", new ArrayList<>(Arrays.asList(
                        new Preference("Jack", 2, Arrays.asList(
                                "when APO suggest bowling",
                                "when -1 suggest shops",
                                "when 30 suggest pool",
                                "when weather suggest cinema"
                        )))),
                        new User(2, new int[]{30}, 0, 0, new SensorData(), 0, false, false)
                },
                // Invalid data type
                {"Non-empty pref list but 1 invalid over 2 temperature preferences", "Jack", new ArrayList<>(Arrays.asList(
                        new Preference("Jack", 2, Arrays.asList(
                                "when APO suggest bowling",
                                "when 2.0 suggest shops",
                                "when 30 suggest pool",
                                "when weather suggest cinema"
                        )))),
                        new User(2, new int[]{30}, 0, 0, new SensorData(), 0, false, false)
                },
        });
    }
    @Before
    public void before() throws NoSuchFieldException, IllegalAccessException {

        // Set the actualPreferences field of the PreferenceRepository instance
        Field preferencesField = PreferenceRepository.class.getDeclaredField("preferences");
        preferencesField.setAccessible(true);
        preferencesField.set(preferenceRepository, testPreferences);
    }

    // ---------------------------------------------
    @Test
    public void testGetUserInfo() {
        PreferenceRepository.PreferenceWorkerI worker = new PreferenceRepository.PreferenceWorkerI();
        User actualUserInfo = worker.getUserInfo(name, null);
        // Use JUnit to compare the actual and expected user info
        System.out.println("name: " + name + ": expected medical: " + expectedUserInfo.medicalConditionType + " " +
                                   "actual" +
                                   " medical: " + actualUserInfo.medicalConditionType);
        assertEquals("Medical condition type", expectedUserInfo.medicalConditionType, actualUserInfo.medicalConditionType);
        assertArrayEquals("Temperature thresholds", expectedUserInfo.tempThreshholds, actualUserInfo.tempThreshholds);
        assertEquals("APO threshold", expectedUserInfo.apoThreshhold, actualUserInfo.apoThreshhold);

    }

}