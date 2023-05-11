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
public class TestGetWeatherSuggestion {

    // Test inputs for the parameterized test
    @Parameter(0)
    public static String testName;
    @Parameter(1)
    public static String name;
    @Parameter(2)
    public static List<Preference> testPreferences;
    @Parameter(3)
    public static int weatherThreshold;
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
                        1, null
                },
                // Test case 2: Multiple users
                // Jack
                {"Non-empty pref list", "Jack", multiplePreferences,
                        1, "cinema"
                },
                // David
                {"Non-empty pref list", "David", multiplePreferences,
                        1, "shops"
                },
                // Test case 3: Multiple users - no match
                {"Non-empty pref list but no match", "John", multiplePreferences,
                        1, null
                },
                // Test case 4: Non-empty list - empty weather preferences
                {"Non-empty pref list but empty temperature preferences", "Jack", new ArrayList<>(List.of(
                        new Preference("Jack", 2, Arrays.asList(
                                "when APO suggest bowling",
                                "when 30 suggest pool"
                        )))),
                        1, null
                },
                // Test case 5: Non-empty list - temp threshold with various values
                // at the lower bound
                {"Non-empty pref list and weather threshold at the lower bound", "Jack", multiplePreferences,
                        0, null
                },
                // at the upper bound
                {"Non-empty pref list and weather threshold at the upper bound", "Jack", multiplePreferences,
                        3, "cinema"
                },
                // above the upper bound
                {"Non-empty pref list and weather threshold above the upper bound", "Jack", multiplePreferences,
                        6, null
                },
                // below the lower bound
                {"Non-empty pref list and weather threshold below the lower bound", "Jack", multiplePreferences,
                        -1, null
                },
                // just above the upper bound
                {"Non-empty pref list and weather threshold just above the upper bound", "Jack", multiplePreferences,
                        3.0001, null
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
    public void testGetSuggestionWeather() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Use reflection to access the private readPreference() method
        Method getSuggestionTempMethod = PreferenceRepository.class.getDeclaredMethod("getSuggestionWeather", String.class, Integer.class);
        getSuggestionTempMethod.setAccessible(true);

        // Invoke the readPreference() method and cast the result to List<Preference>
        // name, weather temporary
        @SuppressWarnings("unchecked")
        String suggestion = (String) getSuggestionTempMethod.invoke(preferenceRepository,
                                                                    name, weatherThreshold);
        assertEquals("Weather Suggestion", expectedSuggestion, suggestion);
    }
}
