package ContectCoordinator;
import com.zeroc.Ice.Current;
import helper.User;
import main.ContextCoordinator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import utils.CC_Utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class AddUserTest {
    Field usersField;
    @Parameterized.Parameter
    public String username;

    @Parameterized.Parameter(1)
    public int expectedSize;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Jack", 1},
                {"Kelvin", 2},
                {"David", 3},
                {"Jack", 3},
                {"jack", 4},
                {"", 4},
                {null, 4}

        });
    }

    @Before
    public void setUp() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        CC_Utils.initCC_Communicator();
        CC_Utils.initCC_PreferenceWorker();
        CC_Utils.initCC_WeatherAlarmWorker();
        CC_Utils.initCC_LocationWorker();
        CC_Utils.initCC_CityInfo();
        Field currentWeather = ContextCoordinator.class.getDeclaredField("currentWeather");
        currentWeather.setAccessible(true);
        currentWeather.set(null, 1);
        usersField = CC_Utils.accessField("users");
        System.out.println(((LinkedHashMap<String, User>)usersField.get(null)).size());

    }

    @Test
    public void testAddUser() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, AssertionError, NoSuchFieldException {
        Method addUser = ContextCoordinator.ContextCoordinatorWorkerI.class.getDeclaredMethod("addUser", String.class, Current.class);
        addUser.invoke(new ContextCoordinator.ContextCoordinatorWorkerI(), username, new Current());
        LinkedHashMap<String, User> usersAfter = (LinkedHashMap<String, User>) usersField.get(null);
        assertEquals("wrong size:\n"+usersAfter.keySet(), expectedSize, usersAfter.size());
    }

}

