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
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class DeleteUserTest {
    Field usersField;
    @Parameterized.Parameter
    public String username;

    @Parameterized.Parameter(1)
    public int expectedSize;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Jack", 1},
                {"jack", 2},
                {"Kelvin", 2},
                {null, 2},
                {"", 2},
                {"David", 1}
        });
    }

    @Before
    public void setUp() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        CC_Utils.initCC_Communicator();
        CC_Utils.initCC_PreferenceWorker();
        CC_Utils.initCC_WeatherAlarmWorker();
        CC_Utils.initCC_LocationWorker();
        CC_Utils.initCC_CityInfo();
        Field currentWeather = CC_Utils.accessField("currentWeather");
        currentWeather.set(null, 1);
        usersField = CC_Utils.accessField("users");
        Method addUser = ContextCoordinator.ContextCoordinatorWorkerI.class.getDeclaredMethod("addUser", String.class, Current.class);
        addUser.invoke(new ContextCoordinator.ContextCoordinatorWorkerI(), "Jack", new Current());
        addUser.invoke(new ContextCoordinator.ContextCoordinatorWorkerI(), "David", new Current());

    }


    @Test
    public void testDeleteUser() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, AssertionError {
        Method deleteUser = ContextCoordinator.ContextCoordinatorWorkerI.class.getDeclaredMethod("deleteUser", String.class, Current.class);
        deleteUser.invoke(new ContextCoordinator.ContextCoordinatorWorkerI(), username, new Current());
        LinkedHashMap<String, User> usersAfter = (LinkedHashMap<String, User>) usersField.get(null);
        assertEquals("wrong size:\n" + usersAfter, expectedSize, usersAfter.size());
    }
}

