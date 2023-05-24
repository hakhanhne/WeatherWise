package Integration;

import main.PreferenceRepository;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import support.Preference;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

import static org.junit.Assert.*;
import static org.junit.runners.Parameterized.Parameter;
import static org.junit.runners.Parameterized.Parameters;

// InvocationTargetException is not real exception. It is just an exception in a reflection method if that method
// does not handle the exceptions thrown by the method it is invoking.
@RunWith(Parameterized.class)
public class TestPreferenceRepositoryAndPreferenceText {
    private static PreferenceRepository preferenceRepository;
    private static final String TEST_PREFERENCE_FILE = "Preference";
    private static final String BACKUP_FILE = "Preference.bak";

    @Parameter
    public static String testName;
    @Parameter(1)
    public static String preferenceTextFileContent;
    @Parameter(2)
    public static List<Preference> expectedPreferences;
    @Parameter(3)
    public Class<? extends Throwable> expectedException;


    @Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        Preference jackPreference = new Preference("Jack", 2, new ArrayList<>() {{
            add("when 20 suggest shops");
            add("when 30 suggest pool");
            add("when APO suggest bowling");
            add("when weather suggest cinema");
        }});
        Preference davidPreference = new Preference("David", 3, new ArrayList<>() {{
            add("when 16 suggest pool");
            add("when APO suggest cinema");
            add("when weather suggest shops");
        }});
        return Arrays.asList(new Object[][]{
                {"Positive Case with 2 users", """
name: Jack
Medical Condition Type: 2
pref: when 20 suggest shops
pref: when 30 suggest pool
pref: when APO suggest bowling
pref: when weather suggest cinema
***
name: David
Medical Condition Type: 3
pref: when 16 suggest pool
pref: when APO suggest cinema
pref: when weather suggest shops
***
""", new ArrayList<Preference>() {{
                    add(jackPreference);
                    add(davidPreference);
                }}, null},
                {"Positive Case with 1 user", """
name: Jack
Medical Condition Type: 2
pref: when 20 suggest shops
pref: when 30 suggest pool
pref: when APO suggest bowling
pref: when weather suggest cinema
***
""", new ArrayList<Preference>() {{
                    add(jackPreference);
                }}, null},
                {"Positive Case with max 4 users", """
name: Jack
Medical Condition Type: 2
pref: when 20 suggest shops
pref: when 30 suggest pool
pref: when APO suggest bowling
pref: when weather suggest cinema
***
name: Nhung
Medical Condition Type: 1
pref: when 20 suggest shops
pref: when APO suggest bowling
pref: when weather suggest cinema
***
name: Khanh
Medical Condition Type: 4
pref: when 10 suggest shops
pref: when APO suggest bowling
pref: when weather suggest cinema
***
name: David
Medical Condition Type: 3
pref: when 16 suggest pool
pref: when APO suggest cinema
pref: when weather suggest shops
***
""", new ArrayList<Preference>() {{
                    add(jackPreference);
                    add(new Preference("Nhung", 1, new ArrayList<>() {{
                        add("when 20 suggest shops");
                        add("when APO suggest bowling");
                        add("when weather suggest cinema");
                    }}));
                    add(new Preference("Khanh", 4, new ArrayList<>() {{
                        add("when 10 suggest shops");
                        add("when APO suggest bowling");
                        add("when weather suggest cinema");
                    }}));
                    add(davidPreference);
                }}, null},
                {"Has more than max number of users - 4", """
name: Jack
Medical Condition Type: 2
pref: when 20 suggest shops
pref: when 30 suggest pool
pref: when APO suggest bowling
pref: when weather suggest cinema
***
name: Nhung
Medical Condition Type: 1
pref: when 20 suggest shops
pref: when APO suggest bowling
pref: when weather suggest cinema
***
name: Khanh
Medical Condition Type: 4
pref: when 10 suggest shops
pref: when APO suggest bowling
pref: when weather suggest cinema
***
name: David
Medical Condition Type: 3
pref: when 16 suggest pool
pref: when APO suggest cinema
pref: when weather suggest shops
***
name: Error User
Medical Condition Type: 4
pref: when 10 suggest shops
pref: when APO suggest bowling
pref: when weather suggest cinema
***
""",
                        new ArrayList<Preference>() {{
                            add(jackPreference);
                            add(new Preference("Nhung", 1, new ArrayList<>() {{
                                add("when 20 suggest shops");
                                add("when APO suggest bowling");
                                add("when weather suggest cinema");
                            }}));
                            add(new Preference("Khanh", 4, new ArrayList<>() {{
                                add("when 10 suggest shops");
                                add("when APO suggest bowling");
                                add("when weather suggest cinema");
                            }}));
                            add(davidPreference);
                        }}, null},
                {"Empty List", "", Collections.emptyList(), null},
                {"Empty User", """
name:\s
Medical Condition Type:\s
pref:\s
***
""", Collections.emptyList(), null},
                {"Has 2 users but 1 among 2 is empty user", """
name: Jack
Medical Condition Type: 2
pref: when 20 suggest shops
pref: when 30 suggest pool
pref: when APO suggest bowling
pref: when weather suggest cinema
***
name:\s
Medical Condition Type:\s
pref:\s
***
""", new ArrayList<Preference>() {{
                    add(jackPreference);}}, null},
                {"Has 2 users: 1 valid and 1 invalid user with empty value", """
name: Jack
Medical Condition Type: 2
pref: when 20 suggest shops
pref: when 30 suggest pool
pref: when APO suggest bowling
pref: when weather suggest cinema
***
name: Jack
Medical Condition Type: 2
pref:\s
pref: when 30 suggest pool
pref: when APO suggest bowling
pref: when weather suggest cinema
""",
                        new ArrayList<Preference>() {{
                            add(jackPreference);}}, null},
                {"Has 2 users: 1 valid and 1 invalid user with invalid key", """
name: Jack
Medical Condition Type: 2
pref: when 20 suggest shops
pref: when 30 suggest pool
pref: when APO suggest bowling
pref: when weather suggest cinema
***
name: Jack
Medical: 2
pref: when 20 suggest shops
pref: when 30 suggest pool
pref: when APO suggest bowling
pref: when weather suggest cinema
""",
                        new ArrayList<Preference>() {{
                            add(jackPreference);}}, null},
                {"Empty Name", """
name:\s
Medical Condition Type: 2
pref: when 20 suggest shops
pref: when 30 suggest pool
pref: when APO suggest bowling
pref: when weather suggest cinema
***
""", Collections.emptyList(), null},
                {"Empty Medical Condition", """
name: Jack
Medical Condition Type:\s
pref: when 20 suggest shops
pref: when 30 suggest pool
pref: when APO suggest bowling
pref: when weather suggest cinema
***
""", Collections.emptyList(), null},
                {"Empty Preference", """
name: Jack
Medical Condition Type: 2
pref:\s
pref: when 30 suggest pool
pref: when APO suggest bowling
pref: when weather suggest cinema
***
""", Collections.emptyList(), null},
                {"Wrong key name", """
name: Jack
Medical: 2
pref: when 20 suggest shops
pref: when 30 suggest pool
pref: when APO suggest bowling
pref: when weather suggest cinema
***
""", Collections.emptyList(), IOException.class},
                {"Incorrect users separator", """
name: Jack
Medical Condition Type: 2
pref: when 20 suggest shops
pref: when 30 suggest pool
pref: when APO suggest bowling
pref: when weather suggest cinema
*
name: David
Medical Condition Type: 3
pref: when 16 suggest pool
pref: when APO suggest cinema
pref: when weather suggest shops
***
""", new ArrayList<Preference>() {{
                    add(jackPreference);}}, null},
        });
    }

    // init
    @BeforeClass
    public static void setUpTest() throws IOException {
        preferenceRepository = new PreferenceRepository();
        Files.copy(new File(TEST_PREFERENCE_FILE).toPath(), new File(BACKUP_FILE).toPath());
    }

    @Test
    public void testIntegration() {
        try {
            // Overwrite the original text file with test data
            FileWriter writer = new FileWriter(TEST_PREFERENCE_FILE);
            writer.write(preferenceTextFileContent);
            writer.close();
            // Use reflection to access the private readPreference() method
            Method readPreferenceMethod = PreferenceRepository.class.getDeclaredMethod("readPreference");
            readPreferenceMethod.setAccessible(true);

            // Invoke the readPreference() method and cast the result to List<Preference>
            List<Preference> actualPreferences = (List<Preference>) readPreferenceMethod.invoke(preferenceRepository);
            // read successfully
            if (expectedException != null) {
                fail("Expected " + expectedException.getName() + " was not thrown.");
            }
            System.out.println("Expected Preferences size: " + expectedPreferences.size() + " Actual Preferences " +
                                       "size: " + actualPreferences.size());
            assertEquals("Preference List Size", expectedPreferences.size(), actualPreferences.size());
            for (int i = 0; i < actualPreferences.size(); i++) {
                Preference expected = expectedPreferences.get(i);
                Preference actual = actualPreferences.get(i);
                assertEquals("Preference in element " + i, expected.toString(), actual.toString());
                System.out.println("Expected Preference: " + expected);
                System.out.println("Actual Preference: " + actual);
            }
        }
        catch (Exception e) {
            System.out.println(e.getCause());
            if (expectedException == null) {
                System.out.println("Expected no exception with " + expectedPreferences.size() + " preferences but got" +
                                           " " + e.getCause());
                fail("Unexpected exception: " + e.getMessage());
            } else {
                assertEquals(expectedException, e.getCause().getClass());
            }
        }
    }


    @AfterClass
    public static void tearDown() throws IOException {
        // Rename the backup copy of the original text file to its original name
        Files.move(new File(BACKUP_FILE).toPath(),
                   new File(TEST_PREFERENCE_FILE).toPath(),
                   StandardCopyOption.REPLACE_EXISTING);
    }
}
