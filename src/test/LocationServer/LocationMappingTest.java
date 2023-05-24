package LocationServer;

import static org.junit.Assert.assertEquals;

import main.LocationServer;
import com.zeroc.Ice.Current;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class LocationMappingTest {
    private LinkedHashMap<String, String> table;
    
    @Parameterized.Parameter(0) public String location;
    @Parameterized.Parameter(1) public String expectedLocationStatus;
    
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        
        return Arrays.asList(new Object[][]{
                {"A", "Indoor"},
                {"B", "Indoor"},
                {"C", "Outdoor"},
                {"D", "Outdoor"},
                {"",null},
                {"F",null}
        });
    }
    
    
    @Test
    public void testLocationMapping() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        LinkedHashMap<String, String> newTable = new LinkedHashMap<>();
        // Add test data to the table
        newTable.put("A", "Indoor");
        newTable.put("B", "Indoor");
        newTable.put("C", "Outdoor");
        newTable.put("D", "Outdoor");
    
        //        LocationServer
        Class<?> locationServerClass = Class.forName("main.LocationServer");
        Object locationServer = locationServerClass.newInstance();
    
        Field tableField = locationServerClass.getDeclaredField("table");
        tableField.setAccessible(true);
    
        tableField.set(locationServer, newTable);
        
        LocationServer.LocationWorkerI locationWorker = new LocationServer.LocationWorkerI();
        
        Current current = new Current();
        
        String actualResult = locationWorker.locationMapping(location, current);
        
        assertEquals(expectedLocationStatus, actualResult);
    }
    
}
