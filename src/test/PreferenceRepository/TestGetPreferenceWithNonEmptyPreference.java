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
public class TestGetPreferenceWithNonEmptyPreference {

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


        return Arrays.asList(new Object[][]{
                // Test case 1: Non-empty preferences
                // non-existed username
                {"Non-empty pref list - non-existed username: Weather - Temp", "Nhung", multiplePreferences, 2, "35",
                        null},
                {"Non-empty pref list - non-existed username: Weather - APO", "Nhung", multiplePreferences, 2, "APO",
                        null},
                {"Non-empty pref list - non-existed username: Weather", "Nhung", multiplePreferences, 2, "", null},
                {"Non-empty pref list - non-existed username: Temp", "Nhung", multiplePreferences, 0, "35", null},
                {"Non-empty pref list - non-existed username: APO", "Nhung", multiplePreferences, 0, "APO", null},
                {"Non-empty pref list - non-existed username: empty", "Nhung", multiplePreferences, 0, "", null},
                // Special-case username
                {"Non-empty pref list - special-case username", "\n", multiplePreferences, 2, "35", null},
                {"Non-empty pref list - special-case username", "_", multiplePreferences, 2, "35", null},
                {"Non-empty pref list - special-case username", " ", multiplePreferences, 2, "35", null},
                {"Non-empty pref list - special-case username", "", multiplePreferences, 2, "35", null},
                {"Non-empty pref list - special-case username", null, multiplePreferences, 2, "35", null},
                // invalid weather warning
                // only weather
                {"Non-empty pref list - invalid weather: Weather", "Jack", multiplePreferences, -1, "", null},
                {"Non-empty pref list - invalid weather: Weather", "Jack", multiplePreferences, 4, "", null},
                {"Non-empty pref list - invalid weather: Weather", "Jack", multiplePreferences, 2.5, "", null},
                // weather and temp
                {"Non-empty pref list - invalid weather: Weather - Temp", "Jack", multiplePreferences, -1, "35", null},
                {"Non-empty pref list - invalid weather: Weather - Temp", "Jack", multiplePreferences, 4, "35", null},
                {"Non-empty pref list - invalid weather: Weather - Temp", "Jack", multiplePreferences, 2.5, "35", null},
                // weather and APO
                {"Non-empty pref list - invalid weather: Weather - Temp", "Jack", multiplePreferences, -1, "APO", null},
                {"Non-empty pref list - invalid weather: Weather - Temp", "Jack", multiplePreferences, 4, "APO", null},
                {"Non-empty pref list - invalid weather: Weather - Temp", "Jack", multiplePreferences, 2.5, "APO",
                        null},
                // invalid temp or APO warning
                // only temp
                {"Non-empty pref list - invalid temp or APO: Temp", "Jack", multiplePreferences, 0, "-1", null},
                {"Non-empty pref list - invalid temp or APO: Temp", "Jack", multiplePreferences, 0, "51", null},
                {"Non-empty pref list - invalid temp or APO: Temp", "Jack", multiplePreferences, 0, "5.1", null},
                // only APO
                {"Non-empty pref list - invalid temp or APO: APO", "Jack", multiplePreferences, 0, "apo", null},
                {"Non-empty pref list - invalid temp or APO: APO", "Jack", multiplePreferences, 0, "RMIT", null},
                // weather and temp
                {"Non-empty pref list - invalid temp or APO: Weather - Temp", "Jack", multiplePreferences, 2, "-1",
                        "cinema"},
                {"Non-empty pref list - invalid temp or APO: Weather - Temp", "Jack", multiplePreferences, 2, "51",
                        "cinema"},
                {"Non-empty pref list - invalid temp or APO: Weather - Temp", "Jack", multiplePreferences, 2, "5.1",
                        "cinema"},
                // weather and APO
                {"Non-empty pref list - invalid temp or APO: Weather - APO", "Jack", multiplePreferences, 2, "apo",
                        "cinema"},
                {"Non-empty pref list - invalid temp or APO: Weather - APO", "Jack", multiplePreferences, 2, "RMIT",
                        "cinema"},
                // invalid weather and temp or APO warning
                // weather and temp
                {"Non-empty pref list - invalid weather - temp", "Jack", multiplePreferences, -1, "-1", null},
                {"Non-empty pref list - invalid weather - temp", "Jack", multiplePreferences, -1, "51", null},
                {"Non-empty pref list - invalid weather - temp", "Jack", multiplePreferences, -1, "5.1", null},
                {"Non-empty pref list - invalid weather - temp", "Jack", multiplePreferences, 4, "-1", null},
                {"Non-empty pref list - invalid weather - temp", "Jack", multiplePreferences, 4, "51", null},
                {"Non-empty pref list - invalid weather - temp", "Jack", multiplePreferences, 4, "5.1", null},
                {"Non-empty pref list - invalid weather - temp", "Jack", multiplePreferences, 2.5, "-1", null},
                {"Non-empty pref list - invalid weather - temp", "Jack", multiplePreferences, 2.5, "51", null},
                {"Non-empty pref list - invalid weather - temp", "Jack", multiplePreferences, 2.5, "5.1", null},
                // weather and APO
                {"Non-empty pref list - invalid weather - APO", "Jack", multiplePreferences, -1, "apo", null},
                {"Non-empty pref list - invalid weather - APO", "Jack", multiplePreferences, 4, "apo", null},
                {"Non-empty pref list - invalid weather - APO", "Jack", multiplePreferences, 2.5, "apo", null},
                {"Non-empty pref list - invalid weather - APO", "Jack", multiplePreferences, -1, "RMIT", null},
                {"Non-empty pref list - invalid weather - APO", "Jack", multiplePreferences, 4, "RMIT", null},
                {"Non-empty pref list - invalid weather - APO", "Jack", multiplePreferences, 2.5, "RMIT", null},
                // valid arguments
                // nothing
                {"Non-empty pref list - valid: nothing", "Jack", multiplePreferences, 0, "", null},
                {"Non-empty pref list - valid: nothing", "David", multiplePreferences, 0, "", null},
                // weather
                {"Non-empty pref list - valid: Weather", "Jack", multiplePreferences, 2, "", "cinema"},
                {"Non-empty pref list - valid: Weather", "Jack", multiplePreferences, 3, "", "cinema"},
                {"Non-empty pref list - valid: Weather", "David", multiplePreferences, 2, "", "shops"},
                // Temp
                {"Non-empty pref list - valid: Temp", "Jack", multiplePreferences, 0, "19", null},
                {"Non-empty pref list - valid: Temp", "Jack", multiplePreferences, 0, "20", "shops"},
                {"Non-empty pref list - valid: Temp", "Jack", multiplePreferences, 0, "25", "shops"},
                {"Non-empty pref list - valid: Temp", "Jack", multiplePreferences, 0, "30", "pool"},
                {"Non-empty pref list - valid: Temp", "Jack", multiplePreferences, 0, "35", "pool"},
                {"Non-empty pref list - valid: Temp", "Jack", multiplePreferences, 0, "50", "pool"},
                {"Non-empty pref list - valid: Temp", "David", multiplePreferences, 0, "15", null},
                {"Non-empty pref list - valid: Temp", "David", multiplePreferences, 0, "16", "pool"},
                {"Non-empty pref list - valid: Temp", "David", multiplePreferences, 0, "50", "pool"},
                // APO
                {"Non-empty pref list - valid: APO", "Jack", multiplePreferences, 0, "APO", "bowling"},
                {"Non-empty pref list - valid: APO", "David", multiplePreferences, 0, "APO", "cinema"},
                // Weather + Temp
                {"Non-empty pref list - valid: Weather + Temp", "Jack", multiplePreferences, 2, "35", "cinema"},
                {"Non-empty pref list - valid: Weather + Temp", "Jack", multiplePreferences, 3, "35", "cinema"},
                {"Non-empty pref list - valid: Weather + Temp", "Jack", multiplePreferences, 2, "50", "cinema"},
                {"Non-empty pref list - valid: Weather + Temp", "Jack", multiplePreferences, 3, "50", "cinema"},
                // Weather + APO
                {"Non-empty pref list - valid: Weather + APO", "Jack", multiplePreferences, 2, "APO", "cinema"},
                {"Non-empty pref list - valid: Weather + APO", "Jack", multiplePreferences, 3, "APO", "cinema"},

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
