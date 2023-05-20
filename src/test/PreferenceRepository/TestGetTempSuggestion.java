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
public class TestGetTempSuggestion {

    // Test inputs for the parameterized test
    @Parameter(0)
    public static String testName;
    @Parameter(1)
    public static String name;
    @Parameter(2)
    public static List<Preference> testPreferences;
    @Parameter(3)
    public static Number tempThreshold;
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
                        "when APO suggest bowling",
                        "when weather suggest cinema"
                ))));


        return Arrays.asList(new Object[][] {
                // Test case 1: Empty preferences
                {"Empty pref list", "Jack", new ArrayList<>(),
                        35, null
                },
                {"Empty pref list", "David", new ArrayList<>(),
                        35, null
                },
                {"Empty pref list", "Jack", new ArrayList<>(),
                        0, null
                },
                {"Empty pref list", "Jack", new ArrayList<>(),
                        50, null
                },
                // invalid temperature threshold
                {"Empty pref list - invalid temp threshold", "Jack", new ArrayList<>(),
                        -1, null
                },
                // invalid temperature threshold
                {"Empty pref list - invalid temp threshold", "Jack", new ArrayList<>(),
                        51, null
                },
                // invalid temperature threshold
                {"Empty pref list - invalid temp threshold", "Jack", new ArrayList<>(),
                        5.1, null
                },
                // special character name
                {"Empty pref list - special-case name", "\n", new ArrayList<>(),
                        35, null
                },
                // special character name
                {"Empty pref list - special-case name", "_", new ArrayList<>(),
                        35, null
                },
                // special character name
                {"Empty pref list - special-case name", " ", new ArrayList<>(),
                        35, null
                },
                // name is empty
                {"Empty pref list - special-case name", "", new ArrayList<>(),
                        35, null
                },
                // name is null
                {"Empty pref list - special-case name", null, new ArrayList<>(),
                        35, null
                },
                // special character name + invalid temp threshold
                {"Empty pref list - special-case name, invalid temp threshold", "_",
                        new ArrayList<>(),
                        -1, null
                },
                {"Empty pref list - special-case name, invalid temp threshold", "_",
                        new ArrayList<>(),
                        5.1, null
                },
                // ------------------------------------------------
                // Test case 2: Multiple users
                // Jack
                {"Non-empty pref list - various temp thresholds", "Jack", multiplePreferences,
                        0, null
                },
                // Jack
                {"Non-empty pref list - various temp thresholds", "Jack", multiplePreferences,
                        19, null
                },
                // Jack
                {"Non-empty pref list - various temp thresholds", "Jack", multiplePreferences,
                        20, "shops"
                },
                // Jack
                {"Non-empty pref list - various temp thresholds", "Jack", multiplePreferences,
                        21, "shops"
                },
                // Jack
                {"Non-empty pref list - various temp thresholds", "Jack", multiplePreferences,
                        29, "shops"
                },
                // Jack
                {"Non-empty pref list - various temp thresholds", "Jack", multiplePreferences,
                        30, "pool"
                },
                // Jack
                {"Non-empty pref list - various temp thresholds", "Jack", multiplePreferences,
                        31, "pool"
                },
                // Jack
                {"Non-empty pref list - various temp thresholds", "Jack", multiplePreferences,
                        50, "pool"
                },
                // David
                {"Non-empty pref list", "David", multiplePreferences,
                        35, "pool"
                },
                // David: 16
                {"Non-empty pref list at the boundary value of temp preference", "David", multiplePreferences,
                        16, "pool"
                },
                // David: 0
                {"Non-empty pref list - non-matched temperature threshold", "David", multiplePreferences,
                        0, null
                },
                // Invalid temp threshold
                {"Non-empty pref list - invalid temp threshold", "Jack", multiplePreferences,
                        -1, null
                },
                // Invalid temp threshold
                {"Non-empty pref list - invalid temp threshold", "Jack", multiplePreferences,
                        5.1, null
                },
                // Invalid temp threshold
                {"Non-empty pref list - invalid temp threshold", "Jack", multiplePreferences,
                        51, null
                },
                // non-exist name
                {"Non-empty pref list but no match - non-exist name", "Nhung", multiplePreferences,
                        35, null
                },
                {"Non-empty pref list but no match - non-exist name", "Nhung", multiplePreferences,
                        0, null
                },
                {"Non-empty pref list but no match - non-exist name", "Nhung", multiplePreferences,
                        50, null
                },
                // special character name
                {"Non-empty pref list - special-case name", "\n", new ArrayList<>(),
                        35, null
                },
                {"Non-empty pref list - special-case name", "_", new ArrayList<>(),
                        35, null
                },
                // special character name
                {"Non-empty pref list - special-case name", " ", new ArrayList<>(),
                        35, null
                },
                // name is empty
                {"Non-empty pref list - special-case name", "", multiplePreferences,
                        35, null
                },
                // name is null
                {"Non-empty pref list - special-case name", null, multiplePreferences,
                        35, null
                },
                // special character name + invalid temp threshold
                {"Non-empty pref list - special-case name, invalid temp threshold", "_",
                        multiplePreferences,
                        -1, null
                },
                {"Non-empty pref list - special-case name, invalid temp threshold", "_",
                        multiplePreferences,
                        5.1, null
                },
                {"Non-empty pref list - special-case name, invalid temp threshold", "_",
                        multiplePreferences,
                        51, null
                },
                // Test case 4: Non-empty list - empty temperature preferences
                {"Non-empty pref list - empty temp pref", "Jack", emptyTempPreferences,
                        35, null
                },
                {"Non-empty pref list - empty temp pref", "Jack", emptyTempPreferences,
                        0, null
                },
                {"Non-empty pref list - empty temp pref", "Jack", emptyTempPreferences,
                        50, null
                },
                // invalid temp threshold
                {"Non-empty pref list - empty temp pref, invalid temp threshold", "Jack",
                        emptyTempPreferences,
                        -1, null
                },
                {"Non-empty pref list - empty temp pref", "Jack", emptyTempPreferences,
                        5.1, null
                },
                {"Non-empty pref list - empty temp pref", "Jack", emptyTempPreferences,
                        51, null
                },
                // non-existed username
                {"Non-empty pref list - empty temp pref, non-exist name", "Nhung",
                        emptyTempPreferences,
                        35, null
                },
                {"Non-empty pref list - empty temp pref, non-exist name", "Nhung",
                        emptyTempPreferences,
                        0, null
                },
                {"Non-empty pref list - empty temp pref, non-exist name", "Nhung",
                        emptyTempPreferences,
                        50, null
                },
                // special character name
                {"Non-empty pref list - empty temp pref, special-case name", "\n",
                        emptyTempPreferences,
                        35, null
                },
                {"Non-empty pref list - empty temp pref, special-case name", "_",
                        emptyTempPreferences,
                        35, null
                },
                {"Non-empty pref list - empty temp pref, special-case name", " ",
                        emptyTempPreferences,
                        35, null
                },
                {"Non-empty pref list - empty temp pref, special-case name", "",
                        emptyTempPreferences,
                        35, null
                },
                {"Non-empty pref list - empty temp pref, special-case name", null,
                        emptyTempPreferences,
                        35, null
                },
                // special-case name + invalid temp threshold
                {"Non-empty pref list - empty temp pref, special-case name, invalid temp threshold", "_",
                        emptyTempPreferences,
                        -1, null
                },
                {"Non-empty pref list - empty temp pref, special-case name, invalid temp threshold", "_",
                        emptyTempPreferences,
                        5.1, null
                },
                {"Non-empty pref list - empty temp pref, special-case name, invalid temp threshold", "_",
                        emptyTempPreferences,
                        51, null
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
    public void testGetSuggestionTemp() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        try {
            // Use reflection to access the private getSuggestionTemp() method
            Method getSuggestionTempMethod = PreferenceRepository.class.getDeclaredMethod("getSuggestionTemp", String.class,
                                                                                          Integer.class);
            getSuggestionTempMethod.setAccessible(true);

            if (!(tempThreshold instanceof Integer))
                throw new IllegalArgumentException("Invalid argument type");

            // Invoke the readPreference() method and cast the result to List<Preference>
            @SuppressWarnings("unchecked")
            String suggestion = (String) getSuggestionTempMethod.invoke(preferenceRepository,
                                                                        name, (int)tempThreshold);
            System.out.println("------------------------------------------------");
            System.out.println("Name: " + name + " - Temp: " + tempThreshold);
            System.out.println("Expected: " + expectedSuggestion + " - Actual: " + suggestion);
            assertEquals("Temperature Suggestion", expectedSuggestion, suggestion);
        } catch(Exception e) {
            if (e instanceof IllegalArgumentException) {
                System.out.println("------------------------------------------------");
                System.out.println("Name: " + name + " - Temp: " + tempThreshold + " - Invalid argument type");
                assertEquals("Invalid argument type", IllegalArgumentException.class, e.getClass());
            } else {
                fail("Unexpected exception thrown" + e.getMessage());

            }
        }

    }
}
