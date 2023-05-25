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
    public Integer currentClock;

    @Parameterized.Parameter(1)
    public Integer apoThreshold;

    @Parameterized.Parameter(2)
    public boolean expectedResult;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {30, 30, true},
                {31, 30, true},
                {29, 30, false},
                {10, 10, true},
                {9, 10, false},
                {-1, 10, false},
                {null, null, false},
        });
    }

    @Test
    public void testCheckAPOReached() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException{
        User user = new User();
        try {
            user.clock = currentClock;
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            user.apoThreshhold = apoThreshold;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Method checkAPOReached = ContextCoordinator.class.getDeclaredMethod("checkapoReached", User.class);
        checkAPOReached.setAccessible(true);
        boolean actualResult = (boolean) checkAPOReached.invoke(null, user);
        assertEquals(expectedResult, actualResult );
    }
}
