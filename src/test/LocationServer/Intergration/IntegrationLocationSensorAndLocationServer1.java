package LocationServer.Intergration;

import helper.SensorData;
import main.AllSensors;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(Parameterized.class)
public class IntegrationLocationSensorAndLocationServer1 {
    
        @Parameterized.Parameter(0)
        public String username;
        
        @Parameterized.Parameter(1)
        public String expectedName;
        
        @Parameterized.Parameter(2)
        public String expectedLoc;
        
        @Parameterized.Parameter(3)
        public String expectedLocStatus;
        
        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(
                    new Object[][] {
                            {"Jack", "Jack", "A", "Outdoor"},
                            {"David", "David", "A", "Outdoor"}
                    });
        }
        
        @Test
        public void testLocationServerResponseInCorrectWithLocationStatus()
                throws NoSuchMethodException, InvocationTargetException, IllegalAccessException,
                NoSuchFieldException, ClassNotFoundException, InstantiationException {
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
            assertNotEquals(expectedLocStatus, result.get(data.location));
        }
    }

