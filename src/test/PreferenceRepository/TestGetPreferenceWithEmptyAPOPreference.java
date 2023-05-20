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
public class TestGetPreferenceWithEmptyAPOPreference {

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
        List<Preference> emptyAPOPreferences = List.of(
                new Preference("Jack", 2, Arrays.asList(
                        "when 20 suggest shops",
                        "when 30 suggest pool",
                        "when weather suggest cinema"
                )));


        return Arrays.asList(new Object[][]{
                // Test case 1: Empty APO preferences
                // non-existed username
                {"Empty APO pref list - non-existed username: Weather - Temp", "Nhung", emptyAPOPreferences, 2, "35",
                        null},
                {"Empty APO pref list - non-existed username: Weather - APO", "Nhung", emptyAPOPreferences, 2, "APO",
                        null},
                {"Empty APO pref list - non-existed username: Weather", "Nhung", emptyAPOPreferences, 2, "", null},
                {"Empty APO pref list - non-existed username: Temp", "Nhung", emptyAPOPreferences, 0, "35", null},
                {"Empty APO pref list - non-existed username: APO", "Nhung", emptyAPOPreferences, 0, "APO", null},
                {"Empty APO pref list - non-existed username: empty", "Nhung", emptyAPOPreferences, 0, "", null},
                // Special-case username
                {"Empty APO pref list - special-case username", "\n", emptyAPOPreferences, 2, "35", null},
                {"Empty APO pref list - special-case username", "_", emptyAPOPreferences, 2, "35", null},
                {"Empty APO pref list - special-case username", " ", emptyAPOPreferences, 2, "35", null},
                {"Empty APO pref list - special-case username", "", emptyAPOPreferences, 2, "35", null},
                {"Empty APO pref list - special-case username", null, emptyAPOPreferences, 2, "35", null},
                // invalid weather warning
                // only weather
                {"Empty APO pref list - invalid weather: Weather", "Jack", emptyAPOPreferences, -1, "", null},
                {"Empty APO pref list - invalid weather: Weather", "Jack", emptyAPOPreferences, 4, "", null},
                {"Empty APO pref list - invalid weather: Weather", "Jack", emptyAPOPreferences, 2.5, "", null},
                // weather and temp
                {"Empty APO pref list - invalid weather: Weather - Temp", "Jack", emptyAPOPreferences, -1, "35", null},
                {"Empty APO pref list - invalid weather: Weather - Temp", "Jack", emptyAPOPreferences, 4, "35", null},
                {"Empty APO pref list - invalid weather: Weather - Temp", "Jack", emptyAPOPreferences, 2.5, "35", null},
                // weather and APO
                {"Empty APO pref list - invalid weather: Weather - Temp", "Jack", emptyAPOPreferences, -1, "APO", null},
                {"Empty APO pref list - invalid weather: Weather - Temp", "Jack", emptyAPOPreferences, 4, "APO", null},
                {"Empty APO pref list - invalid weather: Weather - Temp", "Jack", emptyAPOPreferences, 2.5, "APO",
                        null},
                // invalid temp or APO warning
                // only temp
                {"Empty APO pref list - invalid temp or APO: Temp", "Jack", emptyAPOPreferences, 0, "-1", null},
                {"Empty APO pref list - invalid temp or APO: Temp", "Jack", emptyAPOPreferences, 0, "51", null},
                {"Empty APO pref list - invalid temp or APO: Temp", "Jack", emptyAPOPreferences, 0, "5.1", null},
                // only APO
                {"Empty APO pref list - invalid temp or APO: APO", "Jack", emptyAPOPreferences, 0, "apo", null},
                {"Empty APO pref list - invalid temp or APO: APO", "Jack", emptyAPOPreferences, 0, "RMIT", null},
                // weather and temp
                {"Empty APO pref list - invalid temp or APO: Weather - Temp", "Jack", emptyAPOPreferences, 2, "-1",
                        "cinema"},
                {"Empty APO pref list - invalid temp or APO: Weather - Temp", "Jack", emptyAPOPreferences, 2, "51",
                        "cinema"},
                {"Empty APO pref list - invalid temp or APO: Weather - Temp", "Jack", emptyAPOPreferences, 2, "5.1",
                        "cinema"},
                // weather and APO
                {"Empty APO pref list - invalid temp or APO: Weather - APO", "Jack", emptyAPOPreferences, 2, "apo",
                        "cinema"},
                {"Empty APO pref list - invalid temp or APO: Weather - APO", "Jack", emptyAPOPreferences, 2, "RMIT",
                        "cinema"},
                // invalid weather and temp or APO warning
                // weather and temp
                {"Empty APO pref list - invalid weather - temp", "Jack", emptyAPOPreferences, -1, "-1", null},
                {"Empty APO pref list - invalid weather - temp", "Jack", emptyAPOPreferences, -1, "51", null},
                {"Empty APO pref list - invalid weather - temp", "Jack", emptyAPOPreferences, -1, "5.1", null},
                {"Empty APO pref list - invalid weather - temp", "Jack", emptyAPOPreferences, 4, "-1", null},
                {"Empty APO pref list - invalid weather - temp", "Jack", emptyAPOPreferences, 4, "51", null},
                {"Empty APO pref list - invalid weather - temp", "Jack", emptyAPOPreferences, 4, "5.1", null},
                {"Empty APO pref list - invalid weather - temp", "Jack", emptyAPOPreferences, 2.5, "-1", null},
                {"Empty APO pref list - invalid weather - temp", "Jack", emptyAPOPreferences, 2.5, "51", null},
                {"Empty APO pref list - invalid weather - temp", "Jack", emptyAPOPreferences, 2.5, "5.1", null},
                // weather and APO
                {"Empty APO pref list - invalid weather - APO", "Jack", emptyAPOPreferences, -1, "apo", null},
                {"Empty APO pref list - invalid weather - APO", "Jack", emptyAPOPreferences, 4, "apo", null},
                {"Empty APO pref list - invalid weather - APO", "Jack", emptyAPOPreferences, 2.5, "apo", null},
                {"Empty APO pref list - invalid weather - APO", "Jack", emptyAPOPreferences, -1, "RMIT", null},
                {"Empty APO pref list - invalid weather - APO", "Jack", emptyAPOPreferences, 4, "RMIT", null},
                {"Empty APO pref list - invalid weather - APO", "Jack", emptyAPOPreferences, 2.5, "RMIT", null},
                // valid arguments
                // nothing
                {"Empty APO pref list - valid: nothing", "Jack", emptyAPOPreferences, 0, "", null},
                // weather
                {"Empty APO pref list - valid: Weather", "Jack", emptyAPOPreferences, 2, "", "cinema"},
                {"Empty APO pref list - valid: Weather", "Jack", emptyAPOPreferences, 3, "", "cinema"},
                // Temp
                {"Empty APO pref list - valid: Temp", "Jack", emptyAPOPreferences, 0, "19", null},
                {"Empty APO pref list - valid: Temp", "Jack", emptyAPOPreferences, 0, "20", "shops"},
                {"Empty APO pref list - valid: Temp", "Jack", emptyAPOPreferences, 0, "25", "shops"},
                {"Empty APO pref list - valid: Temp", "Jack", emptyAPOPreferences, 0, "30", "pool"},
                {"Empty APO pref list - valid: Temp", "Jack", emptyAPOPreferences, 0, "35", "pool"},
                {"Empty APO pref list - valid: Temp", "Jack", emptyAPOPreferences, 0, "50", "pool"},
                // APO
                {"Empty APO pref list - valid: APO", "Jack", emptyAPOPreferences, 0, "APO", null},
                // Weather + Temp
                {"Empty APO pref list - valid: Weather + Temp", "Jack", emptyAPOPreferences, 2, "35", "cinema"},
                {"Empty APO pref list - valid: Weather + Temp", "Jack", emptyAPOPreferences, 3, "35", "cinema"},
                {"Empty APO pref list - valid: Weather + Temp", "Jack", emptyAPOPreferences, 2, "50", "cinema"},
                {"Empty APO pref list - valid: Weather + Temp", "Jack", emptyAPOPreferences, 3, "50", "cinema"},
                // Weather + APO
                {"Empty APO pref list - valid: Weather + APO", "Jack", emptyAPOPreferences, 2, "APO", "cinema"},
                {"Empty APO pref list - valid: Weather + APO", "Jack", emptyAPOPreferences, 3, "APO", "cinema"},

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
