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
public class TestGetPreferenceWithEmptyWeatherAPOPreference {

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
        List<Preference> emptyWeatherAPOPreferences = List.of(
                new Preference("Jack", 2, Arrays.asList(
                        "when 20 suggest shops",
                        "when 30 suggest pool"
                )));

        return Arrays.asList(new Object[][]{
                // Test case 1: Empty weather and APO preferences
                // non-existed username
                {"Empty weather and APO pref list - non-existed username: Weather - Temp", "Nhung",
                        emptyWeatherAPOPreferences,
                        2,
                        "35",
                        null},
                {"Empty weather and APO pref list - non-existed username: Weather - APO", "Nhung",
                        emptyWeatherAPOPreferences,
                        2,
                        "APO",
                        null},
                {"Empty weather and APO pref list - non-existed username: Weather", "Nhung", emptyWeatherAPOPreferences
                        , 2,
                        "",
                        null},
                {"Empty weather and APO pref list - non-existed username: Temp", "Nhung", emptyWeatherAPOPreferences,
                        0,
                        "35",
                        null},
                {"Empty weather and APO pref list - non-existed username: APO", "Nhung", emptyWeatherAPOPreferences, 0,
                        "APO",
                        null},
                {"Empty weather and APO pref list - non-existed username: empty", "Nhung", emptyWeatherAPOPreferences
                        , 0,
                        "",
                        null},
                // Special-case username
                {"Empty weather and APO pref list - special-case username", "\n", emptyWeatherAPOPreferences, 2, "35",
                        null},
                {"Empty weather and APO pref list - special-case username", "_", emptyWeatherAPOPreferences, 2, "35",
                        null},
                {"Empty weather and APO pref list - special-case username", " ", emptyWeatherAPOPreferences, 2, "35",
                        null},
                {"Empty weather and APO pref list - special-case username", "", emptyWeatherAPOPreferences, 2, "35",
                        null},
                {"Empty weather and APO pref list - special-case username", null, emptyWeatherAPOPreferences, 2, "35",
                        null},
                // invalid weather warning
                // only weather
                {"Empty weather and APO pref list - invalid weather: Weather", "Jack", emptyWeatherAPOPreferences, -1,
                        "",
                        null},
                {"Empty weather and APO pref list - invalid weather: Weather", "Jack", emptyWeatherAPOPreferences, 4,
                        "",
                        null},
                {"Empty weather and APO pref list - invalid weather: Weather", "Jack", emptyWeatherAPOPreferences, 2.5,
                        "",
                        null},
                // weather and temp
                {"Empty weather and APO pref list - invalid weather: Weather - Temp", "Jack",
                        emptyWeatherAPOPreferences,
                        -1,
                        "35",
                        null},
                {"Empty weather and APO pref list - invalid weather: Weather - Temp", "Jack",
                        emptyWeatherAPOPreferences,
                        4,
                        "35",
                        null},
                {"Empty weather and APO pref list - invalid weather: Weather - Temp", "Jack",
                        emptyWeatherAPOPreferences,
                        2.5,
                        "35",
                        null},
                // weather and APO
                {"Empty weather and APO pref list - invalid weather: Weather - Temp", "Jack",
                        emptyWeatherAPOPreferences,
                        -1,
                        "APO",
                        null},
                {"Empty weather and APO pref list - invalid weather: Weather - Temp", "Jack",
                        emptyWeatherAPOPreferences,
                        4,
                        "APO",
                        null},
                {"Empty weather and APO pref list - invalid weather: Weather - Temp", "Jack",
                        emptyWeatherAPOPreferences,
                        2.5,
                        "APO",
                        null},
                // invalid temp or APO warning
                // only temp
                {"Empty weather and APO pref list - invalid temp or APO: Temp", "Jack", emptyWeatherAPOPreferences, 0,
                        "-1",
                        null},
                {"Empty weather and APO pref list - invalid temp or APO: Temp", "Jack", emptyWeatherAPOPreferences, 0,
                        "51",
                        null},
                {"Empty weather and APO pref list - invalid temp or APO: Temp", "Jack", emptyWeatherAPOPreferences, 0,
                        "5" +
                        ".1",
                        null},
                // only APO
                {"Empty weather and APO pref list - invalid temp or APO: APO", "Jack", emptyWeatherAPOPreferences, 0,
                        "apo",
                        null},
                {"Empty weather and APO pref list - invalid temp or APO: APO", "Jack", emptyWeatherAPOPreferences, 0,
                        "RMIT",
                        null},
                // weather and temp
                {"Empty weather and APO pref list - invalid temp or APO: Weather - Temp", "Jack",
                        emptyWeatherAPOPreferences,
                        2,
                        "-1",
                        null},
                {"Empty weather and APO pref list - invalid temp or APO: Weather - Temp", "Jack",
                        emptyWeatherAPOPreferences,
                        2,
                        "51",
                        null},
                {"Empty weather and APO pref list - invalid temp or APO: Weather - Temp", "Jack",
                        emptyWeatherAPOPreferences,
                        2,
                        "5" +
                        ".1",
                        null},
                // weather and APO
                {"Empty weather and APO pref list - invalid temp or APO: Weather - APO", "Jack",
                        emptyWeatherAPOPreferences,
                        2,
                        "apo",
                        null},
                {"Empty weather and APO pref list - invalid temp or APO: Weather - APO", "Jack",
                        emptyWeatherAPOPreferences,
                        2,
                        "RMIT",
                        null},
                // invalid weather and temp or APO warning
                // weather and temp
                {"Empty weather and APO pref list - invalid weather - temp", "Jack", emptyWeatherAPOPreferences, -1,
                        "-1",
                        null},
                {"Empty weather and APO pref list - invalid weather - temp", "Jack", emptyWeatherAPOPreferences, -1,
                        "51",
                        null},
                {"Empty weather and APO pref list - invalid weather - temp", "Jack", emptyWeatherAPOPreferences, -1,
                        "5" +
                        ".1",
                        null},
                {"Empty weather and APO pref list - invalid weather - temp", "Jack", emptyWeatherAPOPreferences, 4,
                        "-1",
                        null},
                {"Empty weather and APO pref list - invalid weather - temp", "Jack", emptyWeatherAPOPreferences, 4,
                        "51",
                        null},
                {"Empty weather and APO pref list - invalid weather - temp", "Jack", emptyWeatherAPOPreferences, 4,
                        "5" +
                        ".1",
                        null},
                {"Empty weather and APO pref list - invalid weather - temp", "Jack", emptyWeatherAPOPreferences, 2.5,
                        "-1",
                        null},
                {"Empty weather and APO pref list - invalid weather - temp", "Jack", emptyWeatherAPOPreferences, 2.5,
                        "51",
                        null},
                {"Empty weather and APO pref list - invalid weather - temp", "Jack", emptyWeatherAPOPreferences, 2.5,
                        "5" +
                        ".1",
                        null},
                // weather and APO
                {"Empty weather and APO pref list - invalid weather - APO", "Jack", emptyWeatherAPOPreferences, -1,
                        "apo",
                        null},
                {"Empty weather and APO pref list - invalid weather - APO", "Jack", emptyWeatherAPOPreferences, 4,
                        "apo",
                        null},
                {"Empty weather and APO pref list - invalid weather - APO", "Jack", emptyWeatherAPOPreferences, 2.5,
                        "apo",
                        null},
                {"Empty weather and APO pref list - invalid weather - APO", "Jack", emptyWeatherAPOPreferences, -1,
                        "RMIT",
                        null},
                {"Empty weather and APO pref list - invalid weather - APO", "Jack", emptyWeatherAPOPreferences, 4,
                        "RMIT",
                        null},
                {"Empty weather and APO pref list - invalid weather - APO", "Jack", emptyWeatherAPOPreferences, 2.5,
                        "RMIT",
                        null},
                // valid arguments
                // nothing
                {"Empty weather and APO pref list - valid: nothing", "Jack", emptyWeatherAPOPreferences, 0, "", null},
                // weather
                {"Empty weather and APO pref list - valid: Weather", "Jack", emptyWeatherAPOPreferences, 2, "", null},
                {"Empty weather and APO pref list - valid: Weather", "Jack", emptyWeatherAPOPreferences, 3, "", null},
                // Temp
                {"Empty weather and APO pref list - valid: Temp", "Jack", emptyWeatherAPOPreferences, 0, "19", null},
                {"Empty weather and APO pref list - valid: Temp", "Jack", emptyWeatherAPOPreferences, 0, "20", "shops"},
                {"Empty weather and APO pref list - valid: Temp", "Jack", emptyWeatherAPOPreferences, 0, "25", "shops"},
                {"Empty weather and APO pref list - valid: Temp", "Jack", emptyWeatherAPOPreferences, 0, "30", "pool"},
                {"Empty weather and APO pref list - valid: Temp", "Jack", emptyWeatherAPOPreferences, 0, "35", "pool"},
                {"Empty weather and APO pref list - valid: Temp", "Jack", emptyWeatherAPOPreferences, 0, "50", "pool"},
                // APO
                {"Empty weather and APO pref list - valid: APO", "Jack", emptyWeatherAPOPreferences, 0, "APO", null},
                // Weather + Temp
                {"Empty weather and APO pref list - valid: Weather + Temp", "Jack", emptyWeatherAPOPreferences, 2, "35",
                        null},
                {"Empty weather and APO pref list - valid: Weather + Temp", "Jack", emptyWeatherAPOPreferences, 3, "35",
                        null},
                {"Empty weather and APO pref list - valid: Weather + Temp", "Jack", emptyWeatherAPOPreferences, 2, "50",
                        null},
                {"Empty weather and APO pref list - valid: Weather + Temp", "Jack", emptyWeatherAPOPreferences, 3, "50",
                        null},
                // Weather + APO
                {"Empty weather and APO pref list - valid: Weather + APO", "Jack", emptyWeatherAPOPreferences, 2, "APO",
                        null},
                {"Empty weather and APO pref list - valid: Weather + APO", "Jack", emptyWeatherAPOPreferences, 3, "APO",
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
