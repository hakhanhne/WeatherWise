package PreferenceRepository;

import main.PreferenceRepository;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import support.Preference;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.runners.Parameterized.Parameter;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class TestGetWeatherSuggestion {

    // Test inputs for the parameterized test
    @Parameter(0)
    public static String testName;
    @Parameter(1)
    public static String name;
    @Parameter(2)
    public static List<Preference> testPreferences;
    @Parameter(3)
    public static Number weatherThreshold;
    @Parameter(4)
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
        List<Preference> emptyTempPreferences = new ArrayList<>(List.of(
                new Preference("Jack", 2, Arrays.asList(
                        "when 20 suggest shops",
                        "when 30 suggest pool",
                        "when APO suggest bowling"
                ))));

        return Arrays.asList(new Object[][] {
                // Test case 1: Empty preferences
                {"Empty pref list", "Jack", new ArrayList<>(),
                        2, null
                },
                {"Empty pref list", "David", new ArrayList<>(),
                        2, null
                },
                {"Empty pref list", "Jack", new ArrayList<>(),
                        0, null
                },
                {"Empty pref list", "Jack", new ArrayList<>(),
                        3, null
                },
                // invalid weather threshold
                {"Empty pref list - invalid weather alarm", "Jack", new ArrayList<>(),
                        -1, null
                },
                // invalid weather threshold
                {"Empty pref list - invalid weather alarm", "Jack", new ArrayList<>(),
                        4, null
                },
                {"Empty pref list - invalid weather alarm", "Jack", new ArrayList<>(),
                        2.1, null
                },
                // special character name
                {"Empty pref list - special-case name", "\n", new ArrayList<>(),
                        2, null
                },
                // special character name
                {"Empty pref list - special-case name", "_", new ArrayList<>(),
                        2, null
                },
                // special character name
                {"Empty pref list - special-case name", " ", new ArrayList<>(),
                        2, null
                },
                // name is empty
                {"Empty pref list - special-case name", "", new ArrayList<>(),
                        2, null
                },
                // name is null
                {"Empty pref list - special-case name", null, new ArrayList<>(),
                        2, null
                },
                // special character name + invalid weather alarm
                {"Empty pref list - special-case name, invalid weather alarm", "_",
                        new ArrayList<>(),
                        -1, null
                },
                {"Empty pref list - special-case name, invalid weather alarm", "_",
                        new ArrayList<>(),
                        2.1, null
                },
                // ------------------------------------------------
                // Test case 2: Multiple users
                // Jack
                {"Non-empty pref list - various weather alarm", "Jack", multiplePreferences,
                        0, null
                },
                // Jack
                {"Non-empty pref list - various weather alarm", "Jack", multiplePreferences,
                        1, "cinema"
                },
                // Jack
                {"Non-empty pref list - various weather alarm", "Jack", multiplePreferences,
                        3, "cinema"
                },
                // Jack
                {"Non-empty pref list - various weather alarm", "Jack", multiplePreferences,
                        2, "cinema"
                },
                // David:
                {"Non-empty pref list at the boundary value of weather alarm", "David", multiplePreferences,
                        2, "shops"
                },
                // David: 0
                {"Non-empty pref list - non-matched weather alarm", "David", multiplePreferences,
                        0, null
                },

                // Invalid weather alarm
                {"Non-empty pref list - invalid weather alarm", "Jack", multiplePreferences,
                        -1, null
                },
                {"Non-empty pref list - invalid weather alarm", "Jack", multiplePreferences,
                        2.1, null
                },
                {"Non-empty pref list - invalid temp threshold", "Jack", multiplePreferences,
                        4, null
                },
                // non-exist name
                {"Non-empty pref list but no match - non-exist name", "Nhung", multiplePreferences,
                        2, null
                },
                {"Non-empty pref list but no match - non-exist name", "Nhung", multiplePreferences,
                        0, null
                },
                {"Non-empty pref list but no match - non-exist name", "Nhung", multiplePreferences,
                        3, null
                },
                // special character name
                {"Non-empty pref list - special-case name", "\n", new ArrayList<>(),
                        2, null
                },
                {"Non-empty pref list - special-case name", "_", new ArrayList<>(),
                        2, null
                },
                // special character name
                {"Non-empty pref list - special-case name", " ", new ArrayList<>(),
                        2, null
                },
                // name is empty
                {"Non-empty pref list - special-case name", "", multiplePreferences,
                        2, null
                },
                // name is null
                {"Non-empty pref list - special-case name", null, multiplePreferences,
                        2, null
                },
                // special character name + invalid weather alarm
                {"Non-empty pref list - special-case name, invalid weather alarm", "_",
                        multiplePreferences,
                        -1, null
                },
                {"Non-empty pref list - special-case name, invalid weather alarm", "_",
                        multiplePreferences,
                        2.1, null
                },
                {"Non-empty pref list - special-case name, invalid weather alarm", "_",
                        multiplePreferences,
                        4, null
                },
                // Test case 4: Non-empty list - empty weather preferences
                {"Non-empty pref list - empty weather pref", "Jack", emptyTempPreferences,
                        2, null
                },
                {"Non-empty pref list - empty weather pref", "Jack", emptyTempPreferences,
                        0, null
                },
                {"Non-empty pref list - empty weather pref", "Jack", emptyTempPreferences,
                        3, null
                },
                // invalid weather alarm
                {"Non-empty pref list - empty weather pref, invalid weather alarm", "Jack",
                        emptyTempPreferences,
                        -1, null
                },
                {"Non-empty pref list - empty weather pref, invalid weather alarm", "Jack", emptyTempPreferences,
                        2.1, null
                },
                {"Non-empty pref list - empty weather pref, invalid weather alarm", "Jack", emptyTempPreferences,
                        4, null
                },
                // non-existed username
                {"Non-empty pref list - empty weather pref, non-exist name", "Nhung",
                        emptyTempPreferences,
                        2, null
                },
                {"Non-empty pref list - empty weather pref, non-exist name", "Nhung",
                        emptyTempPreferences,
                        0, null
                },
                {"Non-empty pref list - empty weather pref, non-exist name", "Nhung",
                        emptyTempPreferences,
                        3, null
                },
                // special character name
                {"Non-empty pref list - empty weather pref, special-case name", "\n",
                        emptyTempPreferences,
                        2, null
                },
                {"Non-empty pref list - empty weather pref, special-case name", "_",
                        emptyTempPreferences,
                        2, null
                },
                {"Non-empty pref list - empty weather pref, special-case name", " ",
                        emptyTempPreferences,
                        2, null
                },
                {"Non-empty pref list - empty weather pref, special-case name", "",
                        emptyTempPreferences,
                        2, null
                },
                {"Non-empty pref list - empty weather pref, special-case name", null,
                        emptyTempPreferences,
                        2, null
                },
                // special-case name + invalid weather alarm
                {"Non-empty pref list - empty weather pref, special-case name, invalid weather alarm", "_",
                        emptyTempPreferences,
                        -1, null
                },
                {"Non-empty pref list - empty weather pref, special-case name, invalid weather alarm", "_",
                        emptyTempPreferences,
                        2.1, null
                },
                {"Non-empty pref list - empty temp pref, special-case name, invalid temp threshold", "_",
                        emptyTempPreferences,
                        4, null
                }
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
    public void testGetSuggestionWeather() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        try {
            // Use reflection to access the private readPreference() method
            Method getSuggestionTempMethod = PreferenceRepository.class.getDeclaredMethod("getSuggestionWeather", String.class, Integer.class);
            getSuggestionTempMethod.setAccessible(true);

            if (!(weatherThreshold instanceof Integer))
                throw new IllegalArgumentException("Invalid argument type");

            // Invoke the readPreference() method and cast the result to List<Preference>
            // name, weather temporary
            @SuppressWarnings("unchecked")
            String suggestion = (String) getSuggestionTempMethod.invoke(preferenceRepository,
                                                                        name, (int)weatherThreshold);
            System.out.println("------------------------------------------------");
            System.out.println("Test case name: " + testName);
            System.out.println("Name: " + name + " - Weather: " + weatherThreshold);
            System.out.println("Expected: " + expectedSuggestion + " - Actual: " + suggestion);
            assertEquals("Weather Suggestion", expectedSuggestion, suggestion);
        } catch(Exception e) {
            if (e instanceof IllegalArgumentException) {
                System.out.println("------------------------------------------------");
                System.out.println("Name: " + name + " - Weather: " + weatherThreshold + " - Invalid argument type");
                assertEquals("Invalid argument type", IllegalArgumentException.class, e.getClass());
            } else {
                fail("Unexpected exception thrown" + e.getMessage());

            }
        }
    }
}
