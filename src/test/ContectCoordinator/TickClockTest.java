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
public class TickClockTest {
    @Parameterized.Parameter
    public String username;
    @Parameterized.Parameter(1)
    public int beforeTick;

    @Parameterized.Parameter(2)
    public int expectedAfterTick;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Jack", 0, 1},
                {"Jack", 50, 51},
                {"Jack", -1, 0},
                {"Jack", 20, 21},
        });
    }

    @Test
    public void testTickClock() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IllegalArgumentException, NoSuchFieldException {
        User user = new User();
        user.sensorData.username = username;
        user.clock = beforeTick;
        LinkedHashMap<String, User> users = new LinkedHashMap<>();
        users.put(username, user);

        Field usersField = ContextCoordinator.class.getDeclaredField("users");
        usersField.setAccessible(true);
        usersField.set(null, users);

        Method tickClock = ContextCoordinator.class.getDeclaredMethod("tickClock", String.class);
        tickClock.setAccessible(true);
        tickClock.invoke(null, username);

        LinkedHashMap<String, User> usersAfter = (LinkedHashMap<String, User>) usersField.get(null);

        assertEquals(expectedAfterTick, usersAfter.get(username).clock);

        System.out.println("Before reset: " + beforeTick);
        System.out.println("Expected after reset: " + expectedAfterTick);
        System.out.println("Actual after reset: " + usersAfter.get(username).clock + "\n");
    }

}
