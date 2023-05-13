package PreferenceRepository;

import main.PreferenceRepository;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import support.Preference;

import static org.junit.runners.Parameterized.*;

import java.lang.reflect.Method;
import java.util.*;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TestReadPreference {

    // Test inputs for the parameterized test
    @Parameter
    public static List<Preference> expectedPreferences;

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
                {multiplePreferences}
        });
    }


    // ---------------------------------------------
//    @Test
//    public void testGetPreference() {
//        PreferenceRepository.PreferenceWorkerI worker = new PreferenceRepository.PreferenceWorkerI();
//        List<String> actualPreferences = worker.getPreference(name, null);
//        // Use JUnit to compare the actual and expected preferences
//        assertEquals("Length of the preference list", expectedUserInfo.preferences.size(), actualPreferences.size());
//        // assert equality of the preference element in the list
//        for (int i = 0; i < expectedUserInfo.preferences.size(); i++) {
//            String expected = expectedUserInfo.preferences.get(i);
//            String actual = actualPreferences.get(i);
//            assertEquals("Preference in element " + i, expected, actual);
//        }
//    }


    // ---------------------------------------------
    // only test 1 case because of focusing on the code, instead of text file
    // more tests will be in integration test between PreferenceRepository and Preference.txt
    @Test
    public void testReadPreference() throws Exception {

        // Use reflection to access the private readPreference() method
        Method readPreferenceMethod = PreferenceRepository.class.getDeclaredMethod("readPreference");
        readPreferenceMethod.setAccessible(true);

        // Invoke the readPreference() method and cast the result to List<Preference>
        @SuppressWarnings("unchecked")
        List<Preference> actualPreferences = (List<Preference>) readPreferenceMethod.invoke(preferenceRepository);
        // Use JUnit to compare the actual and expected preferences
        assertEquals("Length of the preference list", expectedPreferences.size(), actualPreferences.size());
        // assert equality of the preference element in the list
        for (int i = 0; i < expectedPreferences.size(); i++) {
            Preference expected = expectedPreferences.get(i);
            Preference actual = actualPreferences.get(i);
            assertEquals("Preference name in element " + i, expected.getName(), actual.getName());
            assertEquals("Preference medical condition in element " + i, expected.getMedicalCondition(), actual.getMedicalCondition());
            assertEquals("Preference suggestions in element " + i, expected.getSuggestions(), actual.getSuggestions());
        }
    }

}