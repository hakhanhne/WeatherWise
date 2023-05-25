package ContectCoordinator;

import main.ContextCoordinator;
import org.junit.BeforeClass;
import org.junit.Test;
import support.LocationDetails;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.assertEquals;

/*
    This test class execute negative test for the method readCityInfo() of ContextCoordinator.
    There are 3 test scenarios. Please run them individually.
    1. File exists but has invalid format. - please modify the content of the CityInfo file
    2. File exist but is empty. - please clear the content of the CityInfo file
    3. File doesn't exist. - please delete the CityInfo file
 */
public class ReadCityInfoTest_Negative {
    static Method readCityInfo;

    @BeforeClass
    public static void setUp() throws NoSuchMethodException {
        readCityInfo = ContextCoordinator.class.getDeclaredMethod("readCityInfo");
        readCityInfo.setAccessible(true);
    }

    @Test
    public void testReadCityInfo_1_InvalidFormat() throws InvocationTargetException, IllegalAccessException {
        List<LocationDetails> actualCityInfo =
                (List<LocationDetails>) readCityInfo.invoke(null);
        System.out.println("Total actual locations: " + actualCityInfo.size() + "\n");

        for (int i = 0; i < actualCityInfo.size(); i++) {
            System.out.println("name: " + actualCityInfo.get(i).getName());
            System.out.println("location: " + actualCityInfo.get(i).getLocation());
            System.out.println("info: " + actualCityInfo.get(i).getInfo());
            System.out.println("service: " + actualCityInfo.get(i).getServices() + "\n");
        }
    }

    @Test
    public void testReadCityInfo_2_EmptyFile() throws InvocationTargetException, IllegalAccessException {
        List<LocationDetails> actualCityInfo =
                (List<LocationDetails>) readCityInfo.invoke(null);
        assertEquals(0, actualCityInfo.size());
    }


    @Test
    public void testReadCityInfo_3_NoFile() throws InvocationTargetException, IllegalAccessException {
        List<LocationDetails> actualCityInfo = (List<LocationDetails>) readCityInfo.invoke(null);
        assertEquals(0, actualCityInfo.size());
    }

}

