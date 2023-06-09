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
import static org.junit.runners.Parameterized.Parameter;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class TestGetAPOSuggestion {

    // Test inputs for the parameterized test
    @Parameter(0)
    public static String testName;
    @Parameter(1)
    public static String name;
    @Parameter(2)
    public static List<Preference> testPreferences;
    @Parameter(3)
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
                        "when weather suggest cinema"
                ))));


        return Arrays.asList(new Object[][] {
                // Test case 1: Empty preferences
                {"Empty pref list", "Jack", new ArrayList<>(), null},
                {"Empty pref list", "David", new ArrayList<>(), null},
                // special character name
                {"Empty pref list - special-case name", "\n", new ArrayList<>(), null},
                // special character name
                {"Empty pref list - special-case name", "_", new ArrayList<>(), null},
                // special character name
                {"Empty pref list - special-case name", " ", new ArrayList<>(), null},
                // name is empty
                {"Empty pref list - special-case name", "", new ArrayList<>(), null},
                // name is null
                {"Empty pref list - special-case name", null, new ArrayList<>(), null},

                // ------------------------------------------------
                // Test case 2: Multiple users
                // Jack
                {"Non-empty pref list", "Jack", multiplePreferences, "bowling"},
                // David
                {"Non-empty pref list", "David", multiplePreferences, "cinema"},
                // non-exist name
                {"Non-empty pref list but no match - non-exist name", "Nhung", multiplePreferences, null},

                // special character name
                {"Non-empty pref list - special-case name", "\n", new ArrayList<>(), null},
                {"Non-empty pref list - special-case name", "_", new ArrayList<>(), null},
                // special character name
                {"Non-empty pref list - special-case name", " ", new ArrayList<>(), null},
                // name is empty
                {"Non-empty pref list - special-case name", "", multiplePreferences, null},
                // name is null
                {"Non-empty pref list - special-case name", null, multiplePreferences, null},

                // Test case 4: Non-empty list - empty APO preferences
                {"Non-empty pref list - empty APO pref", "Jack", emptyTempPreferences, null},
                {"Non-empty pref list - empty APO pref", "David", emptyTempPreferences, null},
                // non-existed username
                {"Non-empty pref list - empty temp pref, non-exist name", "Nhung", emptyTempPreferences, null},

                // special character name
                {"Non-empty pref list - empty temp pref, special-case name", "\n", emptyTempPreferences, null},
                {"Non-empty pref list - empty temp pref, special-case name", "_", emptyTempPreferences, null},
                {"Non-empty pref list - empty temp pref, special-case name", " ", emptyTempPreferences, null},
                {"Non-empty pref list - empty temp pref, special-case name", "", emptyTempPreferences, null},
                {"Non-empty pref list - empty temp pref, special-case name", null, emptyTempPreferences, null},
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
    public void testGetSuggestionAPO() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Use reflection to access the private getSuggestionAPO() method
        Method getSuggestionTempMethod = PreferenceRepository.class.getDeclaredMethod("getSuggestionAPO", String.class);
        getSuggestionTempMethod.setAccessible(true);

        // Invoke the getSuggestionAPO() method and cast the result to List<Preference>
        // name temporary
        @SuppressWarnings("unchecked")
        String suggestion = (String) getSuggestionTempMethod.invoke(preferenceRepository, name);
        System.out.println("-------------------------------------------------");
        System.out.println("Test case name: " + testName + " - " + name);
        System.out.println("Expected suggestion: " + expectedSuggestion + " - Actual suggestion" + suggestion);
        assertEquals("APO Suggestion", expectedSuggestion, suggestion);

    }
}
