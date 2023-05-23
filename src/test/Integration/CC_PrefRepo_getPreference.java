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

import static org.junit.Assert.assertEquals;
import static org.junit.runners.Parameterized.*;

@RunWith(Parameterized.class)
public class CC_PrefRepo_getPreference {
    public static PreferenceRepository preferenceRepository;
    public static PreferenceWorkerPrx prx;

    @Parameter
    public static String testName;
    @Parameter(1)
    public static List<Preference> testPreferences;
    @Parameter(2)
    public static String name;
    @Parameter(3)
    public static int weather;
    @Parameter(4)
    public static String apoTemp;
    @Parameter(5)
    public static String expectedPreference;


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

        return Arrays.asList(new Object[][]{
                // only weather warning
                {"multiple preferences - only weather warning", multiplePreferences, "Jack", 2, "", "cinema"},
                {"multiple preferences - only weather warning", multiplePreferences, "David", 2, "", "shops"},
                {"multiple preferences - only weather warning", multiplePreferences, "Nhung", 2, "", ""},
                {"empty preference - only weather warning", emptyPreferences, "Jack", 2, "", ""},
                // only APO
                {"multiple preferences - only APO warning", multiplePreferences, "Jack", 0, "APO", "bowling"},
                {"multiple preferences - only APO warning", multiplePreferences, "David", 0, "APO", "cinema"},
                {"multiple preferences - only APO warning", multiplePreferences, "Nhung", 0, "APO", ""},
                {"empty preference - only APO warning", emptyPreferences, "Jack", 0, "APO", ""},
                // weather and APO
                {"multiple preferences - weather + APO", multiplePreferences, "Jack", 2, "APO", "cinema"},
                {"multiple preferences - weather + APO", multiplePreferences, "Nhung", 2, "APO", ""},
                {"empty preferences - weather + APO", emptyPreferences, "Jack", 2, "APO", ""},
                // only temp
                {"multiple preferences - only temp warning - 25", multiplePreferences, "Jack", 0, "25", "shops"},
                {"multiple preferences - only temp warning - 35", multiplePreferences, "Jack", 0, "35", "pool"},
                {"multiple preferences - only temp warning - 35", multiplePreferences, "Nhung", 0, "35", ""},
                {"empty preference - only temp warning", emptyPreferences, "Jack", 0, "25", ""},
                // weather and temp
                {"multiple preferences - weather + temp", multiplePreferences, "Jack", 2, "25", "cinema"},
                {"multiple preferences - weather + temp", multiplePreferences, "Nhung", 2, "25", ""},
                {"empty preferences - weather + temp", emptyPreferences, "Jack", 2, "25", ""},
                // Empty request
                {"multiple preferences - empty request", multiplePreferences, "Jack", 0, "", ""},
                {"multiple preferences - empty request", multiplePreferences, "Nhung", 0, "", ""},
                {"empty preferences", emptyPreferences, "Jack", 0, "", ""},
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
    public void testGetPreference() throws NoSuchFieldException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        PreferenceRequest preferenceRequest = new PreferenceRequest(name, weather, apoTemp);
        String actualPreference = prx.getPreference(preferenceRequest);
        System.out.println("----------------------------");
        System.out.println(testName);
        System.out.println("Expected: " + expectedPreference + " - Actual: " + actualPreference);
        assertEquals("Expected suggestion: ", expectedPreference, actualPreference);

    }
}
