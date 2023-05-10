package main;

import org.junit.BeforeClass;
import org.junit.Test;
import support.Preference;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PreferenceRepositoryTest {

    // init
    @BeforeClass
    public static void init() {
    }



    @Test
    public void testReadPreference() throws Exception {
        List<Preference> expectedPreferences = new ArrayList<>();
        expectedPreferences.add(new Preference("Jack", 2, Arrays.asList(
                "when 20 suggest shops",
                "when 30 suggest pool",
                "when APO suggest bowling",
                "when weather suggest cinema"
        )));
        expectedPreferences.add(new Preference("David", 3, Arrays.asList(
                "when 16 suggest pool",
                "when APO suggest cinema",
                "when weather suggest shops"
        )));

        // Create an instance of the class to test
        PreferenceRepository preferenceRepository = new PreferenceRepository();

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