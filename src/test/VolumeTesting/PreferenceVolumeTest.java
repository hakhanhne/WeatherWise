package VolumeTesting;

import main.PreferenceRepository;
import org.junit.Before;
import org.junit.BeforeClass;
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
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.runners.Parameterized.*;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class PreferenceVolumeTest {
    private static final String TEST_PREFERENCE_FILE = "Preference";
    private static final String BACKUP_FILE = "Preference.bak";

    @Parameter
    public String testCaseName;
    @Parameter(1)
    public static String preferenceTextFileContent;

    @Parameters
    public static Collection<Object[]> data() {
        // list of test cases input
        return Arrays.asList(new Object[][]{
                {"", ""},
                {"Only maximum 4 users", """
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
name: Matsuri
Medical Condition Type: 3
pref: when 16 suggest pool
pref: when APO suggest cinema
pref: when weather suggest shops
***
name: Nhung
Medical Condition Type: 3
pref: when 16 suggest pool
pref: when APO suggest cinema
pref: when weather suggest shops
***
"""},
                {"One user has 5 temp preferences", """
name: Jack
Medical Condition Type: 2
pref: when 11 suggest restaurants
pref: when 24 suggest bowling
pref: when 32 suggest pool
pref: when 39 suggest shops
pref: when 46 suggest cinema
pref: when APO suggest bowling
pref: when weather suggest cinema
***
name: David
Medical Condition Type: 3
pref: when 16 suggest pool
pref: when APO suggest cinema
pref: when weather suggest shops
***
name: Matsuri
Medical Condition Type: 3
pref: when 16 suggest pool
pref: when APO suggest cinema
pref: when weather suggest shops
***
name: Nhung
Medical Condition Type: 3
pref: when 16 suggest pool
pref: when APO suggest cinema
pref: when weather suggest shops
***
"""},
                {"All users has 5 temp preferences", """
name: Jack
Medical Condition Type: 2
pref: when 10 suggest restaurants
pref: when 14 suggest bowling
pref: when 22 suggest pool
pref: when 33 suggest shops
pref: when 45 suggest cinema
pref: when APO suggest bowling
pref: when weather suggest cinema
***
name: David
Medical Condition Type: 3
pref: when 12 suggest restaurants
pref: when 22 suggest bowling
pref: when 32 suggest pool
pref: when 42 suggest shops
pref: when 48 suggest cinema
pref: when APO suggest cinema
pref: when weather suggest shops
***
name: Matsuri
Medical Condition Type: 3
pref: when 10 suggest restaurants
pref: when 20 suggest bowling
pref: when 30 suggest pool
pref: when 40 suggest shops
pref: when 50 suggest cinema
pref: when APO suggest cinema
pref: when weather suggest shops
***
name: Nhung
Medical Condition Type: 3
pref: when 5 suggest restaurants
pref: when 15 suggest bowling
pref: when 20 suggest pool
pref: when 30 suggest shops
pref: when 42 suggest cinema
pref: when APO suggest cinema
pref: when weather suggest shops
***
"""},
                {"One user has 10 temp preferences", """
name: Jack
Medical Condition Type: 2
pref: when 3 suggest shops
pref: when 12 suggest restaurants
pref: when 16 suggest market
pref: when 23 suggest bowling
pref: when 27 suggest Ferris wheel
pref: when 34 suggest pool
pref: when 39 suggest restaurants
pref: when 42 suggest shops
pref: when 48 suggest pool
pref: when 50 suggest cinema
pref: when APO suggest bowling
pref: when weather suggest cinema
***
name: David
Medical Condition Type: 3
pref: when 11 suggest restaurants
pref: when 23 suggest bowling
pref: when 33 suggest pool
pref: when 44 suggest shops
pref: when 50 suggest cinema
pref: when APO suggest cinema
pref: when weather suggest shops
***
name: Matsuri
Medical Condition Type: 3
pref: when 6 suggest restaurants
pref: when 17 suggest bowling
pref: when 27 suggest pool
pref: when 43 suggest shops
pref: when 48 suggest cinema
pref: when APO suggest cinema
pref: when weather suggest shops
***
name: Nhung
Medical Condition Type: 3
pref: when 12 suggest restaurants
pref: when 22 suggest bowling
pref: when 32 suggest pool
pref: when 39 suggest shops
pref: when 48 suggest cinema
pref: when APO suggest cinema
pref: when weather suggest shops
***
"""},
                {"4 users have 10 temp preferences", """
name: Jack
Medical Condition Type: 2
pref: when 1 suggest shops
pref: when 9 suggest restaurants
pref: when 12 suggest market
pref: when 18 suggest bowling
pref: when 22 suggest Ferris wheel
pref: when 28 suggest pool
pref: when 32 suggest restaurants
pref: when 39 suggest shops
pref: when 42 suggest pool
pref: when 45 suggest cinema
pref: when APO suggest bowling
pref: when weather suggest cinema
***
name: David
Medical Condition Type: 3
pref: when 6 suggest shops
pref: when 11 suggest restaurants
pref: when 14 suggest market
pref: when 22 suggest bowling
pref: when 26 suggest Ferris wheel
pref: when 32 suggest pool
pref: when 36 suggest restaurants
pref: when 38 suggest shops
pref: when 43 suggest pool
pref: when 47 suggest cinema
pref: when APO suggest cinema
pref: when weather suggest shops
***
name: Matsuri
Medical Condition Type: 3
pref: when 4 suggest shops
pref: when 9 suggest restaurants
pref: when 13 suggest market
pref: when 17 suggest bowling
pref: when 24 suggest Ferris wheel
pref: when 29 suggest pool
pref: when 32 suggest restaurants
pref: when 39 suggest shops
pref: when 44 suggest pool
pref: when 48 suggest cinema
pref: when APO suggest cinema
pref: when weather suggest shops
***
name: Nhung
Medical Condition Type: 3
pref: when 6 suggest shops
pref: when 12 suggest restaurants
pref: when 16 suggest market
pref: when 19 suggest bowling
pref: when 22 suggest Ferris wheel
pref: when 26 suggest pool
pref: when 33 suggest restaurants
pref: when 42 suggest shops
pref: when 46 suggest pool
pref: when 50 suggest cinema
pref: when APO suggest cinema
pref: when weather suggest shops
***
"""},
        });
    }

    @Before
    public void setUp() throws IOException {
        Files.deleteIfExists(new File(BACKUP_FILE).toPath());
    }

    @Test
    public void PreferenceTestLoadingTime() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InterruptedException {
        // Make a backup copy of the original text file
        Files.copy(new File(TEST_PREFERENCE_FILE).toPath(), new File(BACKUP_FILE).toPath());

        // Overwrite the original text file with test data
        FileWriter writer = new FileWriter(TEST_PREFERENCE_FILE);
        writer.write(preferenceTextFileContent);
        writer.close();

        PreferenceRepository preferenceRepository = new PreferenceRepository();
        Method readPreference = preferenceRepository.getClass().getDeclaredMethod("readPreference");
        readPreference.setAccessible(true);

        //Calculate time
        Instant start = Instant.now();
        List<Preference> allPreferences = (List<Preference>) readPreference.invoke(preferenceRepository);
        Instant end = Instant.now();

        long totalTime = Duration.between(start, end).toMillis();

        System.out.println("---------------------------------------------");
        System.out.println(testCaseName);
        System.out.println("Total duration of Preference: " + totalTime);

        assertTrue("Total duration of Preference", totalTime < 1000);

        // Rename the backup copy of the original text file to its original name
        Files.move(new File(BACKUP_FILE).toPath(),
                   new File(TEST_PREFERENCE_FILE).toPath(),
                   StandardCopyOption.REPLACE_EXISTING);

        // Add a delay of 1 second between tests to prevent Java remembering the previous test
        Thread.sleep(1000);

    }
}
