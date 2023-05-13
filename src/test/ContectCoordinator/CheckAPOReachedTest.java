package ContectCoordinator;

import helper.User;
import main.ContextCoordinator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CheckAPOReachedTest {

    @Parameterized.Parameter
    public int currentClock;

    @Parameterized.Parameter(1)
    public int apoThreshold;

    @Parameterized.Parameter(2)
    public boolean expectedResult;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {30, 30, true},
                {31, 30, false},
                {28, 30, false},
                {10, 10, true},
                {9, 10, false},
                {-1, 10, false}
        });
    }

    @Test
    public void testCheckAPOReached() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException{
        User user = new User();
        user.clock = currentClock;
        user.apoThreshhold = apoThreshold;
        Method checkAPOReached = ContextCoordinator.class.getDeclaredMethod("checkapoReached", User.class);
        checkAPOReached.setAccessible(true);
        boolean actualResult = (boolean) checkAPOReached.invoke(null, user);
        assertEquals(expectedResult, actualResult );
    }
}
