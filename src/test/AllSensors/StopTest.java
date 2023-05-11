package AllSensors;

import static org.junit.Assert.assertEquals;

import main.AllSensors;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class StopTest {
    @Parameterized.Parameter(0)
    public String username;

    @Parameterized.Parameters(name = "{index}: username={0}")
    public static Collection<String> usernames() {
        return Arrays.asList("jack", "david");
    }
    @Test
    public void testUserStop() throws IllegalAccessException, NoSuchFieldException {
        AllSensors userSensors = new AllSensors(username);
        // Call stop() method
        userSensors.stop();
        // Use reflection to access signal field and verify its value is false
        Field signalField = AllSensors.class.getDeclaredField("signal");
        signalField.setAccessible(true);
        boolean userSignalValue = signalField.getBoolean(userSensors);
        assertEquals(false, userSignalValue);
    }
}
