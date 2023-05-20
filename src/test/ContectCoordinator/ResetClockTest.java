package ContectCoordinator;

import helper.User;
import main.ContextCoordinator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ResetClockTest {
    @Parameterized.Parameter
    public String username;
    @Parameterized.Parameter(1)
    public int beforeReset;

    @Parameterized.Parameter(2)
    public int expectedAfterReset;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Jack", 13, 0},
                {"Jack", 20, 0},
                {"Jack", 0, 0},
                {"Jack", -1, 0},
        });
    }

    @Test
    public void testResetClock() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IllegalArgumentException, NoSuchFieldException {
        User user = new User();
        user.sensorData.username = username;
        user.clock = beforeReset;
        LinkedHashMap<String, User> users = new LinkedHashMap<>();
        users.put(username, user);

        Field usersField = ContextCoordinator.class.getDeclaredField("users");
        usersField.setAccessible(true);
        usersField.set(null, users);

        Method resetClock = ContextCoordinator.class.getDeclaredMethod("resetClock", String.class);
        resetClock.setAccessible(true);
        resetClock.invoke(null, username);

        LinkedHashMap<String, User> usersAfter = (LinkedHashMap<String, User>) usersField.get(null);

        assertEquals(expectedAfterReset, usersAfter.get(username).clock);

        System.out.println("Before reset: " + beforeReset);
        System.out.println("Expected after reset: " + expectedAfterReset);
        System.out.println("Actual after reset: " + usersAfter.get(username).clock + "\n");
    }

}
