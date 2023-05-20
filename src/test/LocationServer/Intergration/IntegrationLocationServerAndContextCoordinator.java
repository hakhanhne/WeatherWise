package LocationServer.Intergration;

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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import helper.SensorData;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import main.AllSensors;
import java.lang.reflect.Field;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
@RunWith(Parameterized.class)
public class IntegrationLocationServerAndContextCoordinator {
    
    @Parameterized.Parameter(0) public String username;
    @Parameterized.Parameter(1) public String expectedName;
    @Parameterized.Parameter(2) public String expectedLoc;
    
    @Parameterized.Parameter(3) public String expectedLocStatus;
    
    @Parameterized.Parameter(4)
    public String[] expectedItems;
    
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        
        return Arrays.asList(new Object[][]{
                {"Jack", "Jack", "A","Indoor", new String[] {"Vivo City Shopping Centre"}},
                {"David", "David","A","Indoor", new String[] {"Vivo City Shopping Centre"}},
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
    public void testLSensorSendLServerSendCC() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IllegalArgumentException, NoSuchFieldException, ClassNotFoundException, InstantiationException {
        //        LocationServer
        Class<?> locationServerClass = Class.forName("main.LocationServer");
        Object locationServer = locationServerClass.newInstance();
    
        Method readConfig = locationServerClass.getDeclaredMethod("readConfig");
        readConfig.setAccessible(true);
    
        LinkedHashMap<String, String> result =
                (LinkedHashMap<String, String>) readConfig.invoke(locationServer);
    
        //        LocationSensor
        AllSensors userSensor = new AllSensors(username);
        Method getSensorData = AllSensors.class.getDeclaredMethod("getSensorData");
        getSensorData.setAccessible(true);
        SensorData data = (SensorData) getSensorData.invoke(userSensor);
        Field signal = AllSensors.class.getDeclaredField("signal");
        signal.setAccessible(true);
    
        assertEquals(expectedName, data.username);
        assertEquals(expectedLoc, data.location);
        assertEquals(expectedLocStatus, result.get(data.location));
    
    
        // Integration
        User user = new User();
        user.sensorData.username = data.username;
        user.sensorData.location = data.location;
        LinkedHashMap<String, User> users = new LinkedHashMap<>();
        users.put(data.username, user);
        
        Field usersField = ContextCoordinator.class.getDeclaredField("users");
        usersField.setAccessible(true);
        usersField.set(null, users);
        
        Method searchItems = ContextCoordinator.ContextCoordinatorWorkerI.class.getDeclaredMethod("searchItems", String.class, Current.class);
        String[] actualItems = (String[]) searchItems.invoke(new ContextCoordinator.ContextCoordinatorWorkerI(), data.username, new Current());
        assertEquals(expectedItems, actualItems);
    }
}
