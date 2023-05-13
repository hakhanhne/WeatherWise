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
public class CheckTempReachedTest {

    @Parameterized.Parameter
    public int currentTemp;

    @Parameterized.Parameter(1)
    public int[] tempThresholds;

    @Parameterized.Parameter(2)
    public boolean expectedResult;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {41, new int[] {42}, false},
                {42, new int[] {42}, true},
                {43, new int[] {42}, true},
                {-1, new int[] {42}, false},
                {-1, new int[] {-1}, true},
                {0, new int[] {-1}, true},
                {11, new int[] {12, 40}, false},
                {12, new int[] {12, 40}, true},
                {13, new int[] {12, 40}, true},
                {40, new int[] {12, 40}, true},
                {41, new int[] {12, 40}, true}
        });
    }

    @Test
    public void testCheckTempReached() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        User user = new User();
        user.tempThreshholds = tempThresholds;
        user.sensorData.temperature = currentTemp;
        Method checkTempReached = ContextCoordinator.class.getDeclaredMethod("checkTempReached", User.class);
        checkTempReached.setAccessible(true);
        boolean actualResult = (boolean) checkTempReached.invoke(null, user);
        assertEquals(expectedResult, actualResult );
    }
}
