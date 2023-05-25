package ContectCoordinator;

import main.ContextCoordinator;
import org.junit.BeforeClass;
import org.junit.Test;
import support.LocationDetails;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/*
    This test class tests the method readCityInfo() of ContextCoordinator.
    For negative test cases (e.g., invalid format, empty file, file not found), please use ReadCityInfoTest_Negative.
 */
public class ReadCityInfoTest {
    static Method readCityInfo;

    @BeforeClass
    public static void setUp() throws NoSuchMethodException {
        readCityInfo = ContextCoordinator.class.getDeclaredMethod("readCityInfo");
        readCityInfo.setAccessible(true);
    }

    @Test
    public void testReadCityInfo() throws IllegalAccessException, InvocationTargetException {
        int expectedSize = 4;
        String[] expectedName = new String[]{"Vivo City Shopping Centre", "Crescent Mall",
                "Dam Sen Parklands", "Ho Chi Minh City, Downtown"};
        String[] expectedLocation = new String[]{"A", "B", "C", "D"};;
        String[] expectedInfo = new String[]{
                "Vivo City Shopping Centre is a major regional shopping centre in the southern suburb of Ho Chi Minh City, Vietnam. It is the second largest shopping centre in the southern suburbs of Ho Chi Minh City, by gross area, and contains the only H&M store in that region.",
                "Crescent Mall Shopping Centre is located 10km South of the Ho Chi Minh City central business district(CBD) and includes Banana Republic, Baskin Robins, CGV Cinema, Bobapop and over 130 specialty stores.",
                "The Dam Sen Parklands area was created as part of the rejuvenation of the industrial upgrade undertaken for World Expo 1988. The Parklands area is spacious with plenty of green and spaces for all ages. A big lake promenade stretches the area of Dam Sen Parklands.",
                "The Ho Chi Minh City central business district (CBD), or 'the City' is located on a central point in district One. The point, known at its tip as Central Point, slopes upward to the north-west where 'the city' is bounded by parkland and the inner city suburb of District 3, District 4 and District 5."
        };;
        List<List<String>> expectedServices = Arrays.asList(
                Arrays.asList("cinema, restaurants, pool, shops, bowling".split(", ")),
                Arrays.asList("cinema, restaurants, shops".split(", ")),
                Arrays.asList("restaurants, pool, shops, Ferris wheel".split(", ")),
                Arrays.asList("restaurants, shops, market, bowling".split(", "))
        );

        List<LocationDetails> actualCityInfo = (List<LocationDetails>) readCityInfo.invoke(null);
        assertEquals(expectedSize, actualCityInfo.size());

        for (int i = 0; i < expectedName.length; i++) {
            assertEquals(expectedName[i], actualCityInfo.get(i).getName());
            assertEquals(expectedLocation[i], actualCityInfo.get(i).getLocation());
            assertEquals(expectedInfo[i], actualCityInfo.get(i).getInfo());
            assertEquals(expectedServices.get(i), actualCityInfo.get(i).getServices());
        }
    }
}

