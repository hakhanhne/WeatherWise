package Integration;

import main.PreferenceRepository;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import support.Preference;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PreferenceRepository_PreferenceText {
    private static PreferenceRepository preferenceRepository;
    private static final String TEST_PREFERENCE_FILE = "Preference";
    private static final String BACKUP_FILE = "Preference.bak";
    // init
    @BeforeClass
    public static void setUpTest() throws IOException {
        preferenceRepository = new PreferenceRepository();
        Files.copy(new File(TEST_PREFERENCE_FILE).toPath(), new File(BACKUP_FILE).toPath());
    }
    @Test
    public void testIntegration() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Overwrite the original text file with test data
        FileWriter writer = new FileWriter(TEST_PREFERENCE_FILE);
        writer.write("name: Nhung\n" +
                             "Medical Condition Type: 2\n" +
                             "pref: when 20 suggest shops\n" +
                             "pref: when 30 suggest pool\n" +
                             "pref: when APO suggest bowling\n" +
                             "pref: when weather suggest cinema\n" +
                             "***");
        writer.close();


        // Use reflection to access the private readPreference() method
        Method readPreferenceMethod = PreferenceRepository.class.getDeclaredMethod("readPreference");
        readPreferenceMethod.setAccessible(true);

        // Invoke the readPreference() method and cast the result to List<Preference>
        @SuppressWarnings("unchecked")
        List<Preference> actualPreferences = (List<Preference>) readPreferenceMethod.invoke(preferenceRepository);

        // Verify that the output file contains the expected data
        List<Preference> expectedPreferences = new ArrayList<>(Arrays.asList(
                new Preference("Nhung", 2, new ArrayList<String>() {{
                    add("when 20 suggest shops");
                    add("when 30 suggest pool");
                    add("when APO suggest bowling");
                    add("when weather suggest cinema");
                }})
        ));
        assertEquals("Preference List Size", expectedPreferences.size(), actualPreferences.size());
        for (int i = 0; i < expectedPreferences.size(); i++) {
            Preference expected = expectedPreferences.get(i);
            Preference actual = actualPreferences.get(i);
            assertEquals("Preference in element " + i, expected.toString(), actual.toString());
        }
    }

    @AfterClass
    public static void tearDown() throws IOException {
        // Rename the backup copy of the original text file to its original name
        Files.move(new File(BACKUP_FILE).toPath(), new File(TEST_PREFERENCE_FILE).toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
}
