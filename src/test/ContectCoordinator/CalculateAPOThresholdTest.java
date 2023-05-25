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
public class CalculateAPOThresholdTest {

    @Parameterized.Parameter
    public Integer medicalCondition;

    @Parameterized.Parameter(1)
    public Integer aqi;
    @Parameterized.Parameter(2)
    public Integer expectedAPOThreshold;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {1, 0, 20},
                {2, 1, 40},
                {4, 20, 80},
                {1, 21, 15},
                {3, 49, 45},
                {4, 50, 60},
                {1, 51, 10},
                {4, 75, 40},
                {1, 76, 5},
                {3, 99, 15},
                {4, 100, 20},
                {1, 101, null},
                {1, -1, null},
                {0, 20, null},
                {-1, 20, null},
                {5, 20, null},
                {null, null, null}
        });
    }


    @Test
    public void testCalculateAPOThreshold() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        User user = new User();
        try {
            user.medicalConditionType = medicalCondition;
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            user.sensorData.aqi = aqi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        Method calculateAPOThreshold = ContextCoordinator.class.getDeclaredMethod("calculateapoThreshhold", User.class);
        calculateAPOThreshold.setAccessible(true);
        Integer actualAPOThreshold = (Integer) calculateAPOThreshold.invoke(null, user);
        assertEquals(expectedAPOThreshold, actualAPOThreshold );
    }
}
