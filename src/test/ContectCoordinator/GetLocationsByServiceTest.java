package ContectCoordinator;
import main.ContextCoordinator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import utils.CC_Utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class GetLocationsByServiceTest {
    @Parameterized.Parameter
    public String service;

    @Parameterized.Parameter(1)
    public List<String> expectedLocations;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        List<ArrayList<String>> expectedLocations = new ArrayList<>();
        expectedLocations.add(new ArrayList<>() {{add("Vivo City Shopping Centre"); add("Crescent Mall");}});
        expectedLocations.add(new ArrayList<>() {{add("Vivo City Shopping Centre"); add("Crescent Mall"); add("Dam Sen Parklands"); add("Ho Chi Minh City, Downtown");}});
        expectedLocations.add(new ArrayList<>() {{add("Vivo City Shopping Centre"); add("Crescent Mall"); add("Dam Sen Parklands"); add("Ho Chi Minh City, Downtown");}});
        expectedLocations.add(new ArrayList<>() {{add("Vivo City Shopping Centre"); add("Dam Sen Parklands");}});
        expectedLocations.add(new ArrayList<>() {{add("Vivo City Shopping Centre"); add("Ho Chi Minh City, Downtown");}});

        return Arrays.asList(new Object[][]{
                {"cinema", expectedLocations.get(0)},
                {"restaurants", expectedLocations.get(1)},
                {"shops", expectedLocations.get(2)},
                {"pool", expectedLocations.get(3)},
                {"bowling", expectedLocations.get(4)}
        });
    }

    @Before
    public void setUp() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        CC_Utils.initCC_Communicator();
        CC_Utils.initCC_CityInfo();
        CC_Utils.initCC_LocationWorker();
    }

    @Test
    public void testGetLocationsByService() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, AssertionError {
        Method getLocationsByService = ContextCoordinator.class.getDeclaredMethod("getLocationsByService", String.class);
        getLocationsByService.setAccessible(true);

        List<String> actualLocations = (List<String>) getLocationsByService.invoke(null, service);
        assertEquals("wrong location for service: " + service, expectedLocations, actualLocations);

    }

}

