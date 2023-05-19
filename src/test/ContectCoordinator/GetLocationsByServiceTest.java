package ContectCoordinator;
import main.ContextCoordinator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import utils.CC_Utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

/*
    Run LocationServer before running this test class
 */
@RunWith(Parameterized.class)
public class GetLocationsByServiceTest {
    @Parameterized.Parameter
    public String service;

    @Parameterized.Parameter(1)
    public List<String> expectedLocations;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"cinema", Arrays.asList(new String[] {"Vivo City Shopping Centre", "Crescent Mall"})},
                {"restaurants", Arrays.asList(new String[] {"Vivo City Shopping Centre", "Crescent Mall", "Dam Sen Parklands", "Ho Chi Minh City, Downtown"})},
                {"shops", Arrays.asList(new String[] {"Vivo City Shopping Centre", "Crescent Mall", "Dam Sen Parklands", "Ho Chi Minh City, Downtown"})},
                {"pool", Arrays.asList(new String[] {"Vivo City Shopping Centre", "Dam Sen Parklands"})},
                {"bowling", Arrays.asList(new String[] {"Vivo City Shopping Centre", "Ho Chi Minh City, Downtown"})}
        });
    }

    @BeforeClass
    public static void setUp() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
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

