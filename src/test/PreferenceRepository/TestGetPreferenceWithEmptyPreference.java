package PreferenceRepository;

import helper.PreferenceRequest;
import main.PreferenceRepository;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import support.Preference;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.runners.Parameterized.Parameter;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class TestGetPreferenceWithEmptyPreference {

    // Test inputs for the parameterized test
    @Parameter
    public static String testName;
    @Parameter(1)
    public static String name;
    @Parameter(2)
    public static List<Preference> testPreferences;
    @Parameter(3)
    public static Number weather;
    @Parameter(4)
    public static String apoTemp;
    @Parameter(5)
    public static String expectedSuggestion;

    private static PreferenceRepository preferenceRepository;

    // init
    @BeforeClass
    public static void setUpTest() {
        preferenceRepository = new PreferenceRepository();

    }

    @Parameters(name = "{0}: {1}")
    public static Collection<Object[]> data() {
        List<Preference> emptyPreferences = new ArrayList<>();

        return Arrays.asList(new Object[][]{
                // Test case 1: Empty preferences
                // non-existed username
                {"Empty pref list - non-existed username: Weather - Temp", "Nhung", emptyPreferences, 2, "35", null},
                {"Empty pref list - non-existed username: Weather - APO", "Nhung", emptyPreferences, 2, "APO", null},
                {"Empty pref list - non-existed username: Weather", "Nhung", emptyPreferences, 2, "", null},
                {"Empty pref list - non-existed username: Temp", "Nhung", emptyPreferences, 0, "35", null},
                {"Empty pref list - non-existed username: APO", "Nhung", emptyPreferences, 0, "APO", null},
                {"Empty pref list - non-existed username: empty", "Nhung", emptyPreferences, 0, "", null},
                // Special-case username
                {"Empty pref list - special-case username", "\n", emptyPreferences, 2, "35", null},
                {"Empty pref list - special-case username", "_", emptyPreferences, 2, "35", null},
                {"Empty pref list - special-case username", " ", emptyPreferences, 2, "35", null},
                {"Empty pref list - special-case username", "", emptyPreferences, 2, "35", null},
                {"Empty pref list - special-case username", null, emptyPreferences, 2, "35", null},
                // invalid weather warning
                // only weather
                {"Empty pref list - invalid weather: Weather", "Jack", emptyPreferences, -1, "", null},
                {"Empty pref list - invalid weather: Weather", "Jack", emptyPreferences, 4, "", null},
                {"Empty pref list - invalid weather: Weather", "Jack", emptyPreferences, 2.5, "", null},
                // weather and temp
                {"Empty pref list - invalid weather: Weather - Temp", "Jack", emptyPreferences, -1, "35", null},
                {"Empty pref list - invalid weather: Weather - Temp", "Jack", emptyPreferences, 4, "35", null},
                {"Empty pref list - invalid weather: Weather - Temp", "Jack", emptyPreferences, 2.5, "35", null},
                // weather and APO
                {"Empty pref list - invalid weather: Weather - Temp", "Jack", emptyPreferences, -1, "APO", null},
                {"Empty pref list - invalid weather: Weather - Temp", "Jack", emptyPreferences, 4, "APO", null},
                {"Empty pref list - invalid weather: Weather - Temp", "Jack", emptyPreferences, 2.5, "APO", null},
                // invalid temp or APO warning
                // only temp
                {"Empty pref list - invalid temp or APO: Temp", "Jack", emptyPreferences, 0, "-1", null},
                {"Empty pref list - invalid temp or APO: Temp", "Jack", emptyPreferences, 0, "51", null},
                {"Empty pref list - invalid temp or APO: Temp", "Jack", emptyPreferences, 0, "5.1", null},
                // only APO
                {"Empty pref list - invalid temp or APO: APO", "Jack", emptyPreferences, 0, "apo", null},
                {"Empty pref list - invalid temp or APO: APO", "Jack", emptyPreferences, 0, "RMIT", null},
                // weather and temp
                {"Empty pref list - invalid temp or APO: Weather - Temp", "Jack", emptyPreferences, 2, "-1", null},
                {"Empty pref list - invalid temp or APO: Weather - Temp", "Jack", emptyPreferences, 2, "51", null},
                {"Empty pref list - invalid temp or APO: Weather - Temp", "Jack", emptyPreferences, 2, "5.1", null},
                // weather and APO
                {"Empty pref list - invalid temp or APO: Weather - APO", "Jack", emptyPreferences, 2, "apo", null},
                {"Empty pref list - invalid temp or APO: Weather - APO", "Jack", emptyPreferences, 2, "RMIT", null},
                // invalid weather and temp or APO warning
                // weather and temp
                {"Empty pref list - invalid weather - temp", "Jack", emptyPreferences, -1, "-1", null},
                {"Empty pref list - invalid weather - temp", "Jack", emptyPreferences, -1, "51", null},
                {"Empty pref list - invalid weather - temp", "Jack", emptyPreferences, -1, "5.1", null},
                {"Empty pref list - invalid weather - temp", "Jack", emptyPreferences, 4, "-1", null},
                {"Empty pref list - invalid weather - temp", "Jack", emptyPreferences, 4, "51", null},
                {"Empty pref list - invalid weather - temp", "Jack", emptyPreferences, 4, "5.1", null},
                {"Empty pref list - invalid weather - temp", "Jack", emptyPreferences, 2.5, "-1", null},
                {"Empty pref list - invalid weather - temp", "Jack", emptyPreferences, 2.5, "51", null},
                {"Empty pref list - invalid weather - temp", "Jack", emptyPreferences, 2.5, "5.1", null},
                // weather and APO
                {"Empty pref list - invalid weather - APO", "Jack", emptyPreferences, -1, "apo", null},
                {"Empty pref list - invalid weather - APO", "Jack", emptyPreferences, 4, "apo", null},
                {"Empty pref list - invalid weather - APO", "Jack", emptyPreferences, 2.5, "apo", null},
                {"Empty pref list - invalid weather - APO", "Jack", emptyPreferences, -1, "RMIT", null},
                {"Empty pref list - invalid weather - APO", "Jack", emptyPreferences, 4, "RMIT", null},
                {"Empty pref list - invalid weather - APO", "Jack", emptyPreferences, 2.5, "RMIT", null},
                // valid arguments
                // nothing
                {"Empty pref list - valid: nothing", "Jack", emptyPreferences, 0, "", null},
                {"Empty pref list - valid: nothing", "David", emptyPreferences, 0, "", null},
                // weather
                {"Empty pref list - valid: Weather", "Jack", emptyPreferences, 2, "", null},
                {"Empty pref list - valid: Weather", "Jack", emptyPreferences, 3, "", null},
                {"Empty pref list - valid: Weather", "David", emptyPreferences, 2, "", null},
                // Temp
                {"Empty pref list - valid: Temp", "Jack", emptyPreferences, 0, "35", null},
                {"Empty pref list - valid: Temp", "Jack", emptyPreferences, 0, "50", null},
                {"Empty pref list - valid: Temp", "David", emptyPreferences, 0, "50", null},
                // APO
                {"Empty pref list - valid: APO", "Jack", emptyPreferences, 0, "APO", null},
                // Weather + Temp
                {"Empty pref list - valid: Weather + Temp", "Jack", emptyPreferences, 2, "35", null},
                {"Empty pref list - valid: Weather + Temp", "Jack", emptyPreferences, 3, "35", null},
                {"Empty pref list - valid: Weather + Temp", "Jack", emptyPreferences, 2, "50", null},
                {"Empty pref list - valid: Weather + Temp", "Jack", emptyPreferences, 3, "50", null},
                // Weather + APO
                {"Empty pref list - valid: Weather + APO", "Jack", emptyPreferences, 2, "APO", null},
                {"Empty pref list - valid: Weather + APO", "Jack", emptyPreferences, 3, "APO", null},

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
    public void testGetPreference() {
        try {
            if (!(weather instanceof Integer)) {throw new IllegalArgumentException("Weather must be an integer");}
            PreferenceRepository.PreferenceWorkerI worker = new PreferenceRepository.PreferenceWorkerI();
            PreferenceRequest request = new PreferenceRequest(name, (int) weather, apoTemp);
            String suggestion = worker.getPreference(request, null);
            System.out.println("-------------------------------------------");
            System.out.println("Test: " + testName);
            System.out.println("Name: " + name + ", Weather: " + weather + ", APOTemp: " + apoTemp);
            System.out.println("Expected: " + expectedSuggestion + ", Actual: " + suggestion);
            assertEquals("Preferences", expectedSuggestion, suggestion);
        }
        catch (Exception e) {
            System.out.println("------------------------------------------------");
            System.out.println("Name: " + name + ", Weather: " + weather + ", APOTemp: " + apoTemp);
            if (e instanceof IllegalArgumentException) {
                System.out.println("Invalid argument type");
                assertEquals("Invalid argument type", IllegalArgumentException.class, e.getClass());
            } else {
                fail("Unexpected exception thrown" + e.getMessage());

            }
        }
    }
}
