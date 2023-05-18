package LocationServer.Intergration;


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
public class IntegrationLocationSensorAndLocationServer {
    
    @Parameterized.Parameter(0) public String username;
    @Parameterized.Parameter(1) public String expectedName;
    @Parameterized.Parameter(2) public String expectedLoc;
    
    @Parameterized.Parameter(3) public String expectedLocStatus;
    
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Jack", "Jack", "A","Indoor"},
                {"David", "David","A","Indoor"}
        });
    }
    
    @Test
    public void testLocationServerResponseCorrectWithLocationStatus() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException, InstantiationException {
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
        
//        Assert
        assertEquals(expectedName, data.username);
        assertEquals(expectedLoc, data.location);
        assertEquals(expectedLocStatus, result.get(data.location));

    }
}
