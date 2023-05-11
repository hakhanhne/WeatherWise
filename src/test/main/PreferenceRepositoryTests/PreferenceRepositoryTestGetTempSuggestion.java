package main.PreferenceRepositoryTests;

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
import static org.junit.runners.Parameterized.Parameter;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class PreferenceRepositoryTestGetTempSuggestion {

    // Test inputs for the parameterized test
    @Parameter(0)
    public static String testName;
    @Parameter(1)
    public static String name;
    @Parameter(2)
    public static List<Preference> testPreferences;
    @Parameter(3)
    public static int tempThreshold;
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

        return Arrays.asList(new Object[][] {
                // Test case 1: Empty preferences
                {"Empty pref list", "Jack", new ArrayList<>(),
                        35, null
                },
                // invalid temperature threshold
                {"Empty pref list - invalid weather request", "Jack", new ArrayList<>(),
                        -1, null
                },
                // special character name
                {"Empty pref list - special character-contain name", "\n", new ArrayList<>(),
                        2, null
                },
                // name is null
                {"Empty pref list - name is null", null, new ArrayList<>(),
                        2, null
                },
                // name is empty
                {"Empty pref list - name is empty", "", new ArrayList<>(),
                        2, null
                },
                // Test case 2: Multiple users
                // Jack
                {"Non-empty pref list", "Jack", multiplePreferences,
                        35, "pool"
                },
                // David
                {"Non-empty pref list", "David", multiplePreferences,
                        35, "pool"
                },
                // Test case 3: Multiple users - no match
                // non-exist name
                {"Non-empty pref list but no match - non-exist name", "John", multiplePreferences,
                        35, null
                },
                // name is null
                {"Non-empty pref list but no match - name is null", null, multiplePreferences,
                        35, null
                },
                // name is empty
                {"Non-empty pref list but no match - empty name", "", multiplePreferences,
                        35, null
                },
                // special character-contain name
                {"Non-empty pref list but no match - empty name", "\n", multiplePreferences,
                        35, null
                },
                // Test case 4: Non-empty list - empty temperature preferences
                {"Non-empty pref list but empty temperature preferences", "Jack", new ArrayList<>(List.of(
                        new Preference("Jack", 2, Arrays.asList(
                                "when APO suggest bowling",
                                "when weather suggest cinema"
                        )))),
                        35, null
                },
                // Test case 5: Non-empty list - 1 invalid over 2 temperature preferences
                // Above Upperbound
                {"Non-empty pref list but 1 invalid over 2 temperature preferences", "Jack", new ArrayList<>(List.of(
                        new Preference("Jack", 2, Arrays.asList(
                                "when APO suggest bowling",
                                "when 51 suggest shops",
                                "when 30 suggest pool",
                                "when weather suggest cinema"
                        )))),
                        35, "pool"
                },
                // Below Lower bound
                {"Non-empty pref list but 1 invalid over 2 temperature preferences", "Jack", new ArrayList<>(List.of(
                        new Preference("Jack", 2, Arrays.asList(
                                "when APO suggest bowling",
                                "when -1 suggest shops",
                                "when 30 suggest pool",
                                "when weather suggest cinema"
                        )))),
                        35, "pool"
                },
                // Invalid data type
                {"Non-empty pref list but 1 invalid over 2 temperature preferences", "Jack", new ArrayList<>(List.of(
                        new Preference("Jack", 2, Arrays.asList(
                                "when APO suggest bowling",
                                "when 2.0 suggest shops",
                                "when 30 suggest pool",
                                "when weather suggest cinema"
                        )))),
                        35, "pool"
                },
                // Test case 6: Non-empty list - temp threshold with various values
                // between 2 temp preferences
                {"Non-empty pref list and temp threshold between 2 temp preferences", "Jack", multiplePreferences,
                        25, "shops"
                },
                // below the first temp preference
                {"Non-empty pref list and temp threshold below the first temp preference", "Jack", multiplePreferences,
                        15, null
                },
                // above the final temp preference
                {"Non-empty pref list and temp threshold above the final temp preference", "Jack", multiplePreferences,
                        49, "pool"
                },
                // at the boundary value of the first temp preference
                {"Non-empty pref list and temp threshold at the boundary value of the first temp preference", "Jack", multiplePreferences,
                        20, "shops"
                },
                // at the lower bound
                {"Non-empty pref list and temp threshold at the lower bound", "Jack", multiplePreferences,
                        0, null
                },
                // at the upper bound
                {"Non-empty pref list and temp threshold at the upper bound", "Jack", multiplePreferences,
                        50, "pool"
                },
                // above the upper bound
                {"Non-empty pref list and temp threshold above the upper bound", "Jack", multiplePreferences,
                        51, null
                },
                // below the lower bound
                {"Non-empty pref list and temp threshold below the lower bound", "Jack", multiplePreferences,
                        -1, null
                },
                // just above the upper bound
                {"Non-empty pref list and temp threshold just above the upper bound", "Jack", multiplePreferences,
                        50.0001, null
                },
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
        // Use reflection to access the private getSuggestionTemp() method
        Method getSuggestionTempMethod = PreferenceRepository.class.getDeclaredMethod("getSuggestionTemp", String.class,
                                                                       Integer.class);
        getSuggestionTempMethod.setAccessible(true);

        // Invoke the readPreference() method and cast the result to List<Preference>
        // name, weather temporary
        @SuppressWarnings("unchecked")
        String suggestion = (String) getSuggestionTempMethod.invoke(preferenceRepository,
                                                                    name, tempThreshold);
        assertEquals("Temperature Suggestion", expectedSuggestion, suggestion);

    }
}
