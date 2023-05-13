package ContectCoordinator;

import com.zeroc.Ice.Current;
import main.ContextCoordinator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import support.LocationDetails;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class SearchInfoTest {

    @Parameterized.Parameter
    public String inputName;

    @Parameterized.Parameter(1)
    public String expectedInfo;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Vivo City Shopping Centre", "Vivo City Shopping Centre is a major regional shopping centre in the southern suburb of Ho Chi Minh City, Vietnam. It is the second largest shopping centre in the southern suburbs of Ho Chi Minh City, by gross area, and contains the only H&M store in that region."},
                {"Crescent Mall", "Crescent Mall Shopping Centre is located 10km South of the Ho Chi Minh City central business district(CBD) and includes Banana Republic, Baskin Robins, CGV Cinema, Bobapop and over 130 specialty stores."},
                {"Dam Sen Parklands", "The Dam Sen Parklands area was created as part of the rejuvenation of the industrial upgrade undertaken for World Expo 1988. The Parklands area is spacious with plenty of green and spaces for all ages. A big lake promenade stretches the area of Dam Sen Parklands."},
                {"Ho Chi Minh City, Downtown", "The Ho Chi Minh City central business district (CBD), or 'the City' is located on a central point in district One. The point, known at its tip as Central Point, slopes upward to the north-west where 'the city' is bounded by parkland and the inner city suburb of District 3, District 4 and District 5."},
                {"", null},
                {null, null},
                {"Somewhere", null}
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
    public void testSearchInfo() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IllegalArgumentException, NoSuchFieldException {
        Method searchInfo = ContextCoordinator.ContextCoordinatorWorkerI.class.getDeclaredMethod("searchInfo", String.class, Current.class);
        String actualInfo = (String) searchInfo.invoke(new ContextCoordinator.ContextCoordinatorWorkerI(), inputName, new Current());
        assertEquals(expectedInfo, actualInfo);
    }
}
