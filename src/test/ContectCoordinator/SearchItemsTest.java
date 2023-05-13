package ContectCoordinator;

import com.zeroc.Ice.Current;
import helper.SensorData;
import helper.User;
import main.ContextCoordinator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import support.LocationDetails;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class SearchItemsTest {

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
                {"Jack", "C", new String[] {"Dam Sen Parklands"}},
                {"Jack", "D", new String[] {"Ho Chi Minh City, Downtown"}},
                {"Jack", "", new String[] {}},
                {"Jack", null, new String[] {}},

        });
    }

    @Before
    public void setUp() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Method readCityInfo = ContextCoordinator.class.getDeclaredMethod("readCityInfo");
        readCityInfo.setAccessible(true);
        List<LocationDetails> cityInfo = (List<LocationDetails>) readCityInfo.invoke(null);
        Field cityInfoField = ContextCoordinator.class.getDeclaredField("cityInfo");
        cityInfoField.setAccessible(true);
        cityInfoField.set(null, cityInfo);
    }

    @Test
    public void testSearchItems() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IllegalArgumentException, NoSuchFieldException {
        User user = new User();
        user.sensorData.username = givenUsername;
        user.sensorData.location = givenLocation;
        LinkedHashMap<String, User> users = new LinkedHashMap<>();
        users.put(givenUsername, user);

        Field usersField = ContextCoordinator.class.getDeclaredField("users");
        usersField.setAccessible(true);
        usersField.set(null, users);

        Method searchItems = ContextCoordinator.ContextCoordinatorWorkerI.class.getDeclaredMethod("searchItems", String.class, Current.class);
        String[] actualItems = (String[]) searchItems.invoke(new ContextCoordinator.ContextCoordinatorWorkerI(), givenUsername, new Current());
        assertEquals(expectedItems, actualItems);
    }
}
