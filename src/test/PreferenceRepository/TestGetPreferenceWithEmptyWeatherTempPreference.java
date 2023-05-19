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
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.runners.Parameterized.Parameter;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class TestGetPreferenceWithEmptyWeatherTempPreference {

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
        List<Preference> emptyWeatherTempPreferences = List.of(
                new Preference("Jack", 2, List.of(
                        "when APO suggest bowling"
                )));


        return Arrays.asList(new Object[][]{
                // Test case 1: Empty weather and temp preferences
                // non-existed username
                {"Empty weather and temp pref list - non-existed username: Weather - Temp", "Nhung",
                        emptyWeatherTempPreferences,
                        2,
                        "35",
                        null},
                {"Empty weather and temp pref list - non-existed username: Weather - APO", "Nhung",
                        emptyWeatherTempPreferences,
                        2,
                        "APO",
                        null},
                {"Empty weather and temp pref list - non-existed username: Weather", "Nhung",
                        emptyWeatherTempPreferences
                        , 2,
                        "",
                        null},
                {"Empty weather and temp pref list - non-existed username: Temp", "Nhung", emptyWeatherTempPreferences,
                        0,
                        "35",
                        null},
                {"Empty weather and temp pref list - non-existed username: APO", "Nhung", emptyWeatherTempPreferences,
                        0,
                        "APO",
                        null},
                {"Empty weather and temp pref list - non-existed username: empty", "Nhung", emptyWeatherTempPreferences
                        , 0,
                        "",
                        null},
                // Special-case username
                {"Empty weather and temp pref list - special-case username", "\n", emptyWeatherTempPreferences, 2, "35",
                        null},
                {"Empty weather and temp pref list - special-case username", "_", emptyWeatherTempPreferences, 2, "35",
                        null},
                {"Empty weather and temp pref list - special-case username", " ", emptyWeatherTempPreferences, 2, "35",
                        null},
                {"Empty weather and temp pref list - special-case username", "", emptyWeatherTempPreferences, 2, "35",
                        null},
                {"Empty weather and temp pref list - special-case username", null, emptyWeatherTempPreferences, 2, "35",
                        null},
                // invalid weather warning
                // only weather
                {"Empty weather and temp pref list - invalid weather: Weather", "Jack", emptyWeatherTempPreferences, -1,
                        "",
                        null},
                {"Empty weather and temp pref list - invalid weather: Weather", "Jack", emptyWeatherTempPreferences, 4,
                        "",
                        null},
                {"Empty weather and temp pref list - invalid weather: Weather", "Jack", emptyWeatherTempPreferences,
                        2.5,
                        "",
                        null},
                // weather and temp
                {"Empty weather and temp pref list - invalid weather: Weather - Temp", "Jack",
                        emptyWeatherTempPreferences,
                        -1,
                        "35",
                        null},
                {"Empty weather and temp pref list - invalid weather: Weather - Temp", "Jack",
                        emptyWeatherTempPreferences,
                        4,
                        "35",
                        null},
                {"Empty weather and temp pref list - invalid weather: Weather - Temp", "Jack",
                        emptyWeatherTempPreferences,
                        2.5,
                        "35",
                        null},
                // weather and APO
                {"Empty weather and temp pref list - invalid weather: Weather - Temp", "Jack",
                        emptyWeatherTempPreferences,
                        -1,
                        "APO",
                        null},
                {"Empty weather and temp pref list - invalid weather: Weather - Temp", "Jack",
                        emptyWeatherTempPreferences,
                        4,
                        "APO",
                        null},
                {"Empty weather and temp pref list - invalid weather: Weather - Temp", "Jack",
                        emptyWeatherTempPreferences,
                        2.5,
                        "APO",
                        null},
                // invalid temp or APO warning
                // only temp
                {"Empty weather and temp pref list - invalid temp or APO: Temp", "Jack", emptyWeatherTempPreferences,
                        0,
                        "-1",
                        null},
                {"Empty weather and temp pref list - invalid temp or APO: Temp", "Jack", emptyWeatherTempPreferences,
                        0,
                        "51",
                        null},
                {"Empty weather and temp pref list - invalid temp or APO: Temp", "Jack", emptyWeatherTempPreferences,
                        0,
                        "5" +
                        ".1",
                        null},
                // only APO
                {"Empty weather and temp pref list - invalid temp or APO: APO", "Jack", emptyWeatherTempPreferences, 0,
                        "apo",
                        null},
                {"Empty weather and temp pref list - invalid temp or APO: APO", "Jack", emptyWeatherTempPreferences, 0,
                        "RMIT",
                        null},
                // weather and temp
                {"Empty weather and temp pref list - invalid temp or APO: Weather - Temp", "Jack",
                        emptyWeatherTempPreferences,
                        2,
                        "-1",
                        null},
                {"Empty weather and temp pref list - invalid temp or APO: Weather - Temp", "Jack",
                        emptyWeatherTempPreferences,
                        2,
                        "51",
                        null},
                {"Empty weather and temp pref list - invalid temp or APO: Weather - Temp", "Jack",
                        emptyWeatherTempPreferences,
                        2,
                        "5" +
                        ".1",
                        null},
                // weather and APO
                {"Empty weather and temp pref list - invalid temp or APO: Weather - APO", "Jack",
                        emptyWeatherTempPreferences,
                        2,
                        "apo",
                        null},
                {"Empty weather and temp pref list - invalid temp or APO: Weather - APO", "Jack",
                        emptyWeatherTempPreferences,
                        2,
                        "RMIT",
                        null},
                // invalid weather and temp or APO warning
                // weather and temp
                {"Empty weather and temp pref list - invalid weather - temp", "Jack", emptyWeatherTempPreferences, -1,
                        "-1",
                        null},
                {"Empty weather and temp pref list - invalid weather - temp", "Jack", emptyWeatherTempPreferences, -1,
                        "51",
                        null},
                {"Empty weather and temp pref list - invalid weather - temp", "Jack", emptyWeatherTempPreferences, -1,
                        "5" +
                        ".1",
                        null},
                {"Empty weather and temp pref list - invalid weather - temp", "Jack", emptyWeatherTempPreferences, 4,
                        "-1",
                        null},
                {"Empty weather and temp pref list - invalid weather - temp", "Jack", emptyWeatherTempPreferences, 4,
                        "51",
                        null},
                {"Empty weather and temp pref list - invalid weather - temp", "Jack", emptyWeatherTempPreferences, 4,
                        "5" +
                        ".1",
                        null},
                {"Empty weather and temp pref list - invalid weather - temp", "Jack", emptyWeatherTempPreferences, 2.5,
                        "-1",
                        null},
                {"Empty weather and temp pref list - invalid weather - temp", "Jack", emptyWeatherTempPreferences, 2.5,
                        "51",
                        null},
                {"Empty weather and temp pref list - invalid weather - temp", "Jack", emptyWeatherTempPreferences, 2.5,
                        "5" +
                        ".1",
                        null},
                // weather and APO
                {"Empty weather and temp pref list - invalid weather - APO", "Jack", emptyWeatherTempPreferences, -1,
                        "apo",
                        null},
                {"Empty weather and temp pref list - invalid weather - APO", "Jack", emptyWeatherTempPreferences, 4,
                        "apo",
                        null},
                {"Empty weather and temp pref list - invalid weather - APO", "Jack", emptyWeatherTempPreferences, 2.5,
                        "apo",
                        null},
                {"Empty weather and temp pref list - invalid weather - APO", "Jack", emptyWeatherTempPreferences, -1,
                        "RMIT",
                        null},
                {"Empty weather and temp pref list - invalid weather - APO", "Jack", emptyWeatherTempPreferences, 4,
                        "RMIT",
                        null},
                {"Empty weather and temp pref list - invalid weather - APO", "Jack", emptyWeatherTempPreferences, 2.5,
                        "RMIT",
                        null},
                // valid arguments
                // nothing
                {"Empty weather and temp pref list - valid: nothing", "Jack", emptyWeatherTempPreferences, 0, "", null},
                // weather
                {"Empty weather and temp pref list - valid: Weather", "Jack", emptyWeatherTempPreferences, 2, "", null},
                {"Empty weather and temp pref list - valid: Weather", "Jack", emptyWeatherTempPreferences, 3, "", null},
                // Temp
                {"Empty weather and temp pref list - valid: Temp", "Jack", emptyWeatherTempPreferences, 0, "19", null},
                {"Empty weather and temp pref list - valid: Temp", "Jack", emptyWeatherTempPreferences, 0, "20",
                        null},
                {"Empty weather and temp pref list - valid: Temp", "Jack", emptyWeatherTempPreferences, 0, "25",
                        null},
                {"Empty weather and temp pref list - valid: Temp", "Jack", emptyWeatherTempPreferences, 0, "30",
                        null},
                {"Empty weather and temp pref list - valid: Temp", "Jack", emptyWeatherTempPreferences, 0, "35",
                        null},
                {"Empty weather and temp pref list - valid: Temp", "Jack", emptyWeatherTempPreferences, 0, "50",
                        null},
                // APO
                {"Empty weather and temp pref list - valid: APO", "Jack", emptyWeatherTempPreferences, 0, "APO",
                        "bowling"},
                // Weather + Temp
                {"Empty weather and temp pref list - valid: Weather + Temp", "Jack", emptyWeatherTempPreferences, 2,
                        "35",
                        null},
                {"Empty weather and temp pref list - valid: Weather + Temp", "Jack", emptyWeatherTempPreferences, 3,
                        "35",
                        null},
                {"Empty weather and temp pref list - valid: Weather + Temp", "Jack", emptyWeatherTempPreferences, 2,
                        "50",
                        null},
                {"Empty weather and temp pref list - valid: Weather + Temp", "Jack", emptyWeatherTempPreferences, 3,
                        "50",
                        null},
                // Weather + APO
                {"Empty weather and temp pref list - valid: Weather + APO", "Jack", emptyWeatherTempPreferences, 2,
                        "APO",
                        null},
                {"Empty weather and temp pref list - valid: Weather + APO", "Jack", emptyWeatherTempPreferences, 3,
                        "APO",
                        null},

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
