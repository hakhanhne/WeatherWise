package ContectCoordinator.CCWorker;

import com.zeroc.Ice.Current;
import helper.ContextCoordinatorWorker;
import helper.SensorData;
import helper.User;
import main.ContextCoordinator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import support.LocationDetails;
import utils.CC_Utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class SearchItemsTest {
    static ContextCoordinatorWorker ccW = new ContextCoordinator.ContextCoordinatorWorkerI();

    @Parameterized.Parameter
    public String givenUsername;

    @Parameterized.Parameter(1)
    public String givenLocation;

    @Parameterized.Parameter(2)
    public String[] expectedItems;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {

        return Arrays.asList(new Object[][]{
                {"Jack", "A", new String[] {"Vivo City Shopping Centre"}},
                {"Jack", "B", new String[] {"Crescent Mall"}},
                {"David", "C", new String[] {"Dam Sen Parklands"}},
                {"David", "D", new String[] {"Ho Chi Minh City, Downtown"}},
                {"Jack", "E", new String[] {}},
                {"Jack", "", new String[] {}},
                {"Jack", null, new String[] {}},

        });
    }

    @BeforeClass
    public static void setUp() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        CC_Utils.initCC_CityInfo();
    }

    @Test
    public void testSearchItems() throws IllegalAccessException, IllegalArgumentException, NoSuchFieldException {
        User user = new User();
        user.sensorData.username = givenUsername;
        user.sensorData.location = givenLocation;
        LinkedHashMap<String, User> users = new LinkedHashMap<>();
        users.put(givenUsername, user);

        Field usersField = ContextCoordinator.class.getDeclaredField("users");
        usersField.setAccessible(true);
        usersField.set(null, users);

        String[] actualItems = ccW.searchItems(givenUsername, null);
        System.out.println("Total actual items: " + actualItems.length);
        assertEquals(expectedItems.length, actualItems.length);
    }
}
