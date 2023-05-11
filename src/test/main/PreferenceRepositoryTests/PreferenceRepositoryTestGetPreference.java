package main.PreferenceRepositoryTests;

import helper.PreferenceRequest;
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
// Them empty list ("David", "\n", "", null)
// Them non-empty list ("\n", "", null)

// Weather only
// APO only (weather = 0)
// Temp only (weather = 0)
// No warning
// Weather, APO (no weather pref, no APO pref)
// Weather, Temp (no weather pref, no temp pref)
// Invalid weather, invalid APO (lowercase apo) (and temp), invalid both weather and temp, invalid name and weather and
// temp
@RunWith(Parameterized.class)
public class PreferenceRepositoryTestGetPreference {

    // Test inputs for the parameterized test
    @Parameter(0)
    public static String testName;
    @Parameter(1)
    public static String name;
    @Parameter(2)
    public static List<Preference> testPreferences;
    @Parameter(3)
    public static int weather;
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

        return Arrays.asList(new Object[][] {
                // Test case 1: Empty preferences
                // valid weather and apoTemp
                {"Empty pref list - valid request", "Jack", new ArrayList<>(),
                        2, "35", null
                },
                // invalid weather
                {"Empty pref list - invalid weather request", "Jack", new ArrayList<>(),
                        -1, "35", null
                },
                // invalid apoTemp
                {"Empty pref list - invalid weather request", "Jack", new ArrayList<>(),
                        2, "-1", null
                },
                // special character name
                {"Empty pref list - special character-contain name", "\n", new ArrayList<>(),
                        2, "-1", null
                },
                // name is null
                {"Empty pref list - name is null", null, new ArrayList<>(),
                        2, "35", null
                },
                // name is empty
                {"Empty pref list - name is empty", "", new ArrayList<>(),
                        2, "35", null
                },
                // Test case 2: Multiple users
                // Jack
                {"Non-empty pref list", "Jack", multiplePreferences,
                        2, "35", "cinema"
                },
                // David
                {"Non-empty pref list", "David", multiplePreferences,
                        2, "35", "shops"
                },
                // Test case 3: Multiple users - no match
                // non-exist name
                {"Non-empty pref list but no match", "John", multiplePreferences,
                        2, "35", null
                },
                // name is null
                {"Non-empty pref list but no match - name is null", null, multiplePreferences,
                        2, "35", null
                },
                // name is empty
                {"Non-empty pref list but no match - empty name", "", multiplePreferences,
                        2, "35", null
                },
                // special character-contain name
                {"Non-empty pref list but no match - empty name", "\n", multiplePreferences,
                        2, "35", null
                },
                // Test case 4: Non-empty list - empty temp preferences
                // empty temperature preference, get APO and weather
                {"Non-empty pref list but empty temperature preferences - weather and APO request", "Jack",
                        new ArrayList<>(List.of(
                        new Preference("Jack", 2, Arrays.asList(
                                "when APO suggest bowling",
                                "when weather suggest cinema"
                        )))),
                        2, "APO", "cinema"
                },
                // empty temperature preference, get temp and weather
                {"Non-empty pref list but empty temperature preferences - temp and weather request", "Jack",
                        new ArrayList<>(List.of(
                        new Preference("Jack", 2, Arrays.asList(
                                "when APO suggest bowling",
                                "when weather suggest cinema"
                        )))),
                        2, "35", "cinema"
                },
                // empty temperature preference, get temp only
                {"Non-empty pref list but empty temperature preferences - only temp request", "Jack",
                        new ArrayList<>(List.of(
                        new Preference("Jack", 2, Arrays.asList(
                                "when APO suggest bowling",
                                "when weather suggest cinema"
                        )))),
                        0, "35", null
                },
                // Test case: Non-empty list - empty APO preferences
                // empty APO preference, get temp and weather
                {"Non-empty pref list but empty APO preferences - temp and weather request", "Jack",
                        new ArrayList<>(List.of(
                                new Preference("Jack", 2, Arrays.asList(
                                        "when 20 suggest shops",
                                        "when weather suggest cinema"
                                )))),
                        2, "35", "cinema"
                },
                // empty APO preference, get APO and weather
                {"Non-empty pref list but empty APO preferences - APO and weather request", "Jack",
                        new ArrayList<>(List.of(
                                new Preference("Jack", 2, Arrays.asList(
                                        "when weather suggest cinema",
                                        "when 20 suggest shops"
                                )))),
                        2, "APO", "cinema"
                },
                // empty APO preference, get APO only
                {"Non-empty pref list but empty APO preferences - only APO request", "Jack",
                        new ArrayList<>(List.of(
                                new Preference("Jack", 2, Arrays.asList(
                                        "when 20 suggest shops",
                                        "when weather suggest cinema"
                                )))),
                        0, "APO", null
                },
                // Test case: Non-empty list - empty weather preferences
                // empty weather preference, get temp or APO
                {"Non-empty pref list but empty weather preferences - temp or APO request", "Jack",
                        new ArrayList<>(List.of(
                                new Preference("Jack", 2, Arrays.asList(
                                        "when 20 suggest shops",
                                        "when APO suggest bowling"
                                )))),
                        0, "35", "shops"
                },
                // empty weather preference, get APO and weather
                {"Non-empty pref list but empty weather preferences - APO and weather request", "Jack",
                        new ArrayList<>(List.of(
                                new Preference("Jack", 2, Arrays.asList(
                                        "when APO suggest bowling",
                                        "when 20 suggest shops"
                                )))),
                        2, "APO", null
                },
                // empty weather preference, get weather only
                {"Non-empty pref list but empty weather preferences - only weather request", "Jack",
                        new ArrayList<>(List.of(
                                new Preference("Jack", 2, Arrays.asList(
                                        "when APO suggest bowling",
                                        "when 20 suggest shops"
                                )))),
                        2, "", null
                },
                // Test case: Non-empty list - empty 2 preferences (temp and APO)
                // empty temp and APO preference, get weather
                {"Non-empty pref list but empty temp and APO preferences - weather request", "Jack",
                        new ArrayList<>(List.of(
                                new Preference("Jack", 2, List.of(
                                        "when weather suggest cinema"
                                )))),
                        1, "", "cinema"
                },
                // empty temp and APO preference, get APO and weather
                {"Non-empty pref list but empty temp and APO preferences - APO and weather request", "Jack",
                        new ArrayList<>(List.of(
                                new Preference("Jack", 2, List.of(
                                        "when weather suggest cinema"
                                )))),
                        2, "APO", "cinema"
                },
                // empty temp and APO preference, get temp and weather
                {"Non-empty pref list but empty temp and APO preferences - temp and weather request", "Jack",
                        new ArrayList<>(List.of(
                                new Preference("Jack", 2, List.of(
                                        "when weather suggest cinema"
                                )))),
                        2, "35", "cinema"
                },
                // empty temp and APO preference, get temp only
                {"Non-empty pref list but empty temp and APO preferences - only temp request", "Jack",
                        new ArrayList<>(List.of(
                                new Preference("Jack", 2, List.of(
                                        "when weather suggest cinema"
                                )))),
                        0, "35", null
                },
                // empty temp and APO preference, get APO only
                {"Non-empty pref list but empty temp and APO preferences - only APO request", "Jack",
                        new ArrayList<>(List.of(
                                new Preference("Jack", 2, List.of(
                                        "when weather suggest cinema"
                                )))),
                        0, "APO", null
                },
                // Test case: Non-empty list - empty 2 preferences (weather and APO)
                // empty weather and APO preference, get temp
                {"Non-empty pref list but empty weather and APO preferences - temp request", "Jack",
                        new ArrayList<>(List.of(
                                new Preference("Jack", 2, List.of(
                                        "when 20 suggest shops"
                                )))),
                        0, "35", "shops"
                },
                // empty weather and APO preference, get APO and weather
                {"Non-empty pref list but empty weather and APO preferences - APO and weather request", "Jack",
                        new ArrayList<>(List.of(
                                new Preference("Jack", 2, List.of(
                                        "when 20 suggest shops"
                                )))),
                        2, "APO", null
                },
                // empty weather and APO preference, get temp and weather
                {"Non-empty pref list but empty weather and APO preferences - temp and weather request", "Jack",
                        new ArrayList<>(List.of(
                                new Preference("Jack", 2, List.of(
                                        "when 20 suggest shops"
                                )))),
                        2, "35", null
                },
                // empty weather and APO preference, get weather only
                {"Non-empty pref list but empty weather and APO preferences - only weather request", "Jack",
                        new ArrayList<>(List.of(
                                new Preference("Jack", 2, List.of(
                                        "when 20 suggest shops"
                                )))),
                        1, "", null
                },
                // empty weather and APO preference, get APO only
                {"Non-empty pref list but empty weather and APO preferences - only APO request", "Jack",
                        new ArrayList<>(List.of(
                                new Preference("Jack", 2, List.of(
                                        "when 20 suggest shops"
                                )))),
                        0, "APO", null
                },
                // Test case: Non-empty list - empty 2 preferences (weather and temp)
                // empty weather and temp preference, get APO
                {"Non-empty pref list but empty weather and temp preferences - APO request", "Jack",
                        new ArrayList<>(List.of(
                                new Preference("Jack", 2, List.of(
                                        "when APO suggest bowling"
                                )))),
                        0, "APO", "bowling"
                },
                // empty weather and temp preference, get APO and weather
                {"Non-empty pref list but empty weather and temp preferences - APO and weather request", "Jack",
                        new ArrayList<>(List.of(
                                new Preference("Jack", 2, List.of(
                                        "when APO suggest bowling"
                                )))),
                        2, "APO", null
                },
                // empty weather and temp preference, get temp and weather
                {"Non-empty pref list but empty weather and temp preferences - temp and weather request", "Jack",
                        new ArrayList<>(List.of(
                                new Preference("Jack", 2, List.of(
                                        "when APO suggest bowling"
                                )))),
                        2, "35", null
                },
                // empty weather and temp preference, get weather only
                {"Non-empty pref list but empty weather and temp preferences - only weather request", "Jack",
                        new ArrayList<>(List.of(
                                new Preference("Jack", 2, List.of(
                                        "when APO suggest bowling"
                                )))),
                        1, "", null
                },
                // empty weather and temp preference, get temp only
                {"Non-empty pref list but empty weather and temp preferences - only temp request", "Jack",
                        new ArrayList<>(List.of(
                                new Preference("Jack", 2, List.of(
                                        "when APO suggest bowling"
                                )))),
                        0, "35", null
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
                        35, "apo", "pool"
                },
                // Below Lower bound
                {"Non-empty pref list but 1 invalid over 2 temperature preferences", "Jack", new ArrayList<>(List.of(
                        new Preference("Jack", 2, Arrays.asList(
                                "when APO suggest bowling",
                                "when -1 suggest shops",
                                "when 30 suggest pool",
                                "when weather suggest cinema"
                        )))),
                        35, "apo", "pool"
                },
                // Invalid data type
                {"Non-empty pref list but 1 invalid over 2 temperature preferences", "Jack", new ArrayList<>(List.of(
                        new Preference("Jack", 2, Arrays.asList(
                                "when APO suggest bowling",
                                "when 2.0 suggest shops",
                                "when 30 suggest pool",
                                "when weather suggest cinema"
                        )))),
                        35, "apo", "pool"
                },
                // Test case 6: Non-empty list - temp threshold with various values
                // between 2 temp preferences
                {"Non-empty pref list and temp threshold between 2 temp preferences", "Jack", multiplePreferences,
                        25, "apo", "shops"
                },
                // below the first temp preference
                {"Non-empty pref list and temp threshold below the first temp preference", "Jack", multiplePreferences,
                        15, "apo", null
                },
                // above the final temp preference
                {"Non-empty pref list and temp threshold above the final temp preference", "Jack", multiplePreferences,
                        49, "apo", "pool"
                },
                // at the boundary value of the first temp preference
                {"Non-empty pref list and temp threshold at the boundary value of the first temp preference", "Jack", multiplePreferences,
                        20, "apo", "shops"
                },
                // at the lower bound
                {"Non-empty pref list and temp threshold at the lower bound", "Jack", multiplePreferences,
                        0, "apo", null
                },
                // at the upper bound
                {"Non-empty pref list and temp threshold at the upper bound", "Jack", multiplePreferences,
                        50, "apo", "pool"
                },
                // above the upper bound
                {"Non-empty pref list and temp threshold above the upper bound", "Jack", multiplePreferences,
                        51, "apo", null
                },
                // below the lower bound
                {"Non-empty pref list and temp threshold below the lower bound", "Jack", multiplePreferences,
                        -1, "apo", null
                },
                // just above the upper bound
                {"Non-empty pref list and temp threshold just above the upper bound", "Jack", multiplePreferences,
                        50.0001, "apo", null
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
    public void testGetPreference() {
        PreferenceRepository.PreferenceWorkerI worker = new PreferenceRepository.PreferenceWorkerI();
        PreferenceRequest request = new PreferenceRequest(name, weather, apoTemp);
        String suggestion = worker.getPreference(request, null);
        // Use JUnit to compare the actual and expected user info
        assertEquals("Preferences", expectedSuggestion, suggestion);
    }
}
