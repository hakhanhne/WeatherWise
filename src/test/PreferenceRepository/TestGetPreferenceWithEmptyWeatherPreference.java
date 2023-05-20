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
public class TestGetPreferenceWithEmptyWeatherPreference {

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
        List<Preference> emptyWeatherPreferences = List.of(
                new Preference("Jack", 2, Arrays.asList(
                        "when 20 suggest shops",
                        "when 30 suggest pool",
                        "when APO suggest bowling"
                )));


        return Arrays.asList(new Object[][]{
                // Test case 1: Non-empty preferences
                // non-existed username
                {"Non-empty pref list - non-existed username: Weather - Temp", "Nhung", emptyWeatherPreferences, 2, "35",
                        null},
                {"Non-empty pref list - non-existed username: Weather - APO", "Nhung", emptyWeatherPreferences, 2, "APO",
                        null},
                {"Non-empty pref list - non-existed username: Weather", "Nhung", emptyWeatherPreferences, 2, "", null},
                {"Non-empty pref list - non-existed username: Temp", "Nhung", emptyWeatherPreferences, 0, "35", null},
                {"Non-empty pref list - non-existed username: APO", "Nhung", emptyWeatherPreferences, 0, "APO", null},
                {"Non-empty pref list - non-existed username: empty", "Nhung", emptyWeatherPreferences, 0, "", null},
                // Special-case username
                {"Non-empty pref list - special-case username", "\n", emptyWeatherPreferences, 2, "35", null},
                {"Non-empty pref list - special-case username", "_", emptyWeatherPreferences, 2, "35", null},
                {"Non-empty pref list - special-case username", " ", emptyWeatherPreferences, 2, "35", null},
                {"Non-empty pref list - special-case username", "", emptyWeatherPreferences, 2, "35", null},
                {"Non-empty pref list - special-case username", null, emptyWeatherPreferences, 2, "35", null},
                // invalid weather warning
                // only weather
                {"Non-empty pref list - invalid weather: Weather", "Jack", emptyWeatherPreferences, -1, "", null},
                {"Non-empty pref list - invalid weather: Weather", "Jack", emptyWeatherPreferences, 4, "", null},
                {"Non-empty pref list - invalid weather: Weather", "Jack", emptyWeatherPreferences, 2.5, "", null},
                // weather and temp
                {"Non-empty pref list - invalid weather: Weather - Temp", "Jack", emptyWeatherPreferences, -1, "35", null},
                {"Non-empty pref list - invalid weather: Weather - Temp", "Jack", emptyWeatherPreferences, 4, "35", null},
                {"Non-empty pref list - invalid weather: Weather - Temp", "Jack", emptyWeatherPreferences, 2.5, "35", null},
                // weather and APO
                {"Non-empty pref list - invalid weather: Weather - Temp", "Jack", emptyWeatherPreferences, -1, "APO", null},
                {"Non-empty pref list - invalid weather: Weather - Temp", "Jack", emptyWeatherPreferences, 4, "APO", null},
                {"Non-empty pref list - invalid weather: Weather - Temp", "Jack", emptyWeatherPreferences, 2.5, "APO",
                        null},
                // invalid temp or APO warning
                // only temp
                {"Non-empty pref list - invalid temp or APO: Temp", "Jack", emptyWeatherPreferences, 0, "-1", null},
                {"Non-empty pref list - invalid temp or APO: Temp", "Jack", emptyWeatherPreferences, 0, "51", null},
                {"Non-empty pref list - invalid temp or APO: Temp", "Jack", emptyWeatherPreferences, 0, "5.1", null},
                // only APO
                {"Non-empty pref list - invalid temp or APO: APO", "Jack", emptyWeatherPreferences, 0, "apo", null},
                {"Non-empty pref list - invalid temp or APO: APO", "Jack", emptyWeatherPreferences, 0, "RMIT", null},
                // weather and temp
                {"Non-empty pref list - invalid temp or APO: Weather - Temp", "Jack", emptyWeatherPreferences, 2, "-1",
                        null},
                {"Non-empty pref list - invalid temp or APO: Weather - Temp", "Jack", emptyWeatherPreferences, 2, "51",
                        null},
                {"Non-empty pref list - invalid temp or APO: Weather - Temp", "Jack", emptyWeatherPreferences, 2, "5.1",
                        null},
                // weather and APO
                {"Non-empty pref list - invalid temp or APO: Weather - APO", "Jack", emptyWeatherPreferences, 2, "apo",
                        null},
                {"Non-empty pref list - invalid temp or APO: Weather - APO", "Jack", emptyWeatherPreferences, 2, "RMIT",
                        null},
                // invalid weather and temp or APO warning
                // weather and temp
                {"Non-empty pref list - invalid weather - temp", "Jack", emptyWeatherPreferences, -1, "-1", null},
                {"Non-empty pref list - invalid weather - temp", "Jack", emptyWeatherPreferences, -1, "51", null},
                {"Non-empty pref list - invalid weather - temp", "Jack", emptyWeatherPreferences, -1, "5.1", null},
                {"Non-empty pref list - invalid weather - temp", "Jack", emptyWeatherPreferences, 4, "-1", null},
                {"Non-empty pref list - invalid weather - temp", "Jack", emptyWeatherPreferences, 4, "51", null},
                {"Non-empty pref list - invalid weather - temp", "Jack", emptyWeatherPreferences, 4, "5.1", null},
                {"Non-empty pref list - invalid weather - temp", "Jack", emptyWeatherPreferences, 2.5, "-1", null},
                {"Non-empty pref list - invalid weather - temp", "Jack", emptyWeatherPreferences, 2.5, "51", null},
                {"Non-empty pref list - invalid weather - temp", "Jack", emptyWeatherPreferences, 2.5, "5.1", null},
                // weather and APO
                {"Non-empty pref list - invalid weather - APO", "Jack", emptyWeatherPreferences, -1, "apo", null},
                {"Non-empty pref list - invalid weather - APO", "Jack", emptyWeatherPreferences, 4, "apo", null},
                {"Non-empty pref list - invalid weather - APO", "Jack", emptyWeatherPreferences, 2.5, "apo", null},
                {"Non-empty pref list - invalid weather - APO", "Jack", emptyWeatherPreferences, -1, "RMIT", null},
                {"Non-empty pref list - invalid weather - APO", "Jack", emptyWeatherPreferences, 4, "RMIT", null},
                {"Non-empty pref list - invalid weather - APO", "Jack", emptyWeatherPreferences, 2.5, "RMIT", null},
                // valid arguments
                // nothing
                {"Non-empty pref list - valid: nothing", "Jack", emptyWeatherPreferences, 0, "", null},
                // weather
                {"Non-empty pref list - valid: Weather", "Jack", emptyWeatherPreferences, 2, "", null},
                {"Non-empty pref list - valid: Weather", "Jack", emptyWeatherPreferences, 3, "", null},
                // Temp
                {"Non-empty pref list - valid: Temp", "Jack", emptyWeatherPreferences, 0, "19", null},
                {"Non-empty pref list - valid: Temp", "Jack", emptyWeatherPreferences, 0, "20", "shops"},
                {"Non-empty pref list - valid: Temp", "Jack", emptyWeatherPreferences, 0, "25", "shops"},
                {"Non-empty pref list - valid: Temp", "Jack", emptyWeatherPreferences, 0, "30", "pool"},
                {"Non-empty pref list - valid: Temp", "Jack", emptyWeatherPreferences, 0, "35", "pool"},
                {"Non-empty pref list - valid: Temp", "Jack", emptyWeatherPreferences, 0, "50", "pool"},
                // APO
                {"Non-empty pref list - valid: APO", "Jack", emptyWeatherPreferences, 0, "APO", "bowling"},
                // Weather + Temp
                {"Non-empty pref list - valid: Weather + Temp", "Jack", emptyWeatherPreferences, 2, "35", null},
                {"Non-empty pref list - valid: Weather + Temp", "Jack", emptyWeatherPreferences, 3, "35", null},
                {"Non-empty pref list - valid: Weather + Temp", "Jack", emptyWeatherPreferences, 2, "50", null},
                {"Non-empty pref list - valid: Weather + Temp", "Jack", emptyWeatherPreferences, 3, "50", null},
                // Weather + APO
                {"Non-empty pref list - valid: Weather + APO", "Jack", emptyWeatherPreferences, 2, "APO", null},
                {"Non-empty pref list - valid: Weather + APO", "Jack", emptyWeatherPreferences, 3, "APO", null},

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
            if (expectedSuggestion != null) {
                System.out.println("Expected: " + expectedSuggestion);
                fail("Unexpected exception thrown" + e.getClass());
            }
            if (e instanceof IllegalArgumentException) {
                System.out.println("Invalid argument type");
                assertEquals("Invalid argument type", IllegalArgumentException.class, e.getClass());
            } else {
                fail("Unexpected exception thrown" + e.getMessage());

            }
        }
    }
}
