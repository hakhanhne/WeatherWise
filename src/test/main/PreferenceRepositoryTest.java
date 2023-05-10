package main;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;
import helper.SensorData;
import helper.User;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import support.Preference;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.runners.Parameterized.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class PreferenceRepositoryTest {

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

//    // init
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

                // Test case 2: Multiple preferences - Jack
                {"Normal pref list", "Jack", multiplePreferences,
                        new User(2, new int[]{20, 30}, 0, 0, new SensorData(), 0, false, false)
                },
                // Test case 2: Multiple preferences - David
                {"Normal pref list", "David", multiplePreferences,
                        new User(3, new int[]{16}, 0, 0, new SensorData(), 0, false, false)
                },
        });
    }

//    @AfterParam
//    public static void afterParam() throws NoSuchFieldException, IllegalAccessException {
//        System.out.println("ajkbfjsd");
//        // Set the actualPreferences field of the PreferenceRepository instance
//        Field preferencesField = PreferenceRepository.class.getDeclaredField("preferences");
//        preferencesField.setAccessible(true);
//        preferencesField.set(preferenceRepository, testPreferences);
//    }
    @Before
    public void before() throws NoSuchFieldException, IllegalAccessException {

        // Set the actualPreferences field of the PreferenceRepository instance
        Field preferencesField = PreferenceRepository.class.getDeclaredField("preferences");
        preferencesField.setAccessible(true);
        preferencesField.set(preferenceRepository, testPreferences);
    }

    // ---------------------------------------------
    @Test
    public void testGetUserInfo() throws Exception {
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



    // ---------------------------------------------
    // only test 1 case because of focusing on the code, instead of text file
    // more tests will be in integration test between PreferenceRepository and Preference.txt
    @Test
    public void testReadPreference() throws Exception {
        List<Preference> expectedPreferences = new ArrayList<>();
        expectedPreferences.add(new Preference("Jack", 2, Arrays.asList(
                "when 20 suggest shops",
                "when 30 suggest pool",
                "when APO suggest bowling",
                "when weather suggest cinema"
        )));
        expectedPreferences.add(new Preference("David", 3, Arrays.asList(
                "when 16 suggest pool",
                "when APO suggest cinema",
                "when weather suggest shops"
        )));

        // Use reflection to access the private readPreference() method
        Method readPreferenceMethod = PreferenceRepository.class.getDeclaredMethod("readPreference");
        readPreferenceMethod.setAccessible(true);

        // Invoke the readPreference() method and cast the result to List<Preference>
        @SuppressWarnings("unchecked")
        List<Preference> actualPreferences = (List<Preference>) readPreferenceMethod.invoke(preferenceRepository);
        // Use JUnit to compare the actual and expected preferences
        assertEquals("Length of the preference list", expectedPreferences.size(), actualPreferences.size());
        // assert equality of the preference element in the list
        for (int i = 0; i < expectedPreferences.size(); i++) {
            Preference expected = expectedPreferences.get(i);
            Preference actual = actualPreferences.get(i);
            assertEquals("Preference name in element " + i, expected.getName(), actual.getName());
            assertEquals("Preference medical condition in element " + i, expected.getMedicalCondition(), actual.getMedicalCondition());
            assertEquals("Preference suggestions in element " + i, expected.getSuggestions(), actual.getSuggestions());
        }
    }

}