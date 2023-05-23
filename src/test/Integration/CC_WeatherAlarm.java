package Integration;

import helper.WeatherAlarmWorkerPrx;
import main.WeatherAlarms;
import org.junit.Test;
import utils.CC_Utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CC_WeatherAlarm {
    List<Integer> weatherConditions = Arrays.asList(3, 2, 1, 4, 1);
    Iterator<Integer> iterator = weatherConditions.iterator();

    @Test
    public void test() throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        WeatherAlarms weatherAlarms = new WeatherAlarms();
        Field weatherConditionsField = WeatherAlarms.class.getDeclaredField("weatherConditions");
        weatherConditionsField.setAccessible(true);
        weatherConditionsField.set(weatherAlarms, weatherConditions);
        Field iteratorField = WeatherAlarms.class.getDeclaredField("iterator");
        iteratorField.setAccessible(true);
        iteratorField.set(weatherAlarms, iterator);

        WeatherAlarms.class.getDeclaredField("communicator").setAccessible(true);
        Method m = WeatherAlarms.class.getDeclaredMethod("setupWeatherAlarmWorker", String[].class);
        m.setAccessible(true);
        m.invoke(weatherAlarms, (Object) null);

        CC_Utils.initCC_Communicator();
        Field weatherAlarmWorker = CC_Utils.accessField("weatherAlarmWorker");
        CC_Utils.runMethod("iniWeatherAlarmWorker");
        WeatherAlarmWorkerPrx waprx = (WeatherAlarmWorkerPrx) weatherAlarmWorker.get(null);

        Integer weather1 = waprx.getWeather();
        Integer weather2 = waprx.getWeather();
        Integer weather3 = waprx.getWeather();
        Integer weather4 = waprx.getWeather();
        Integer weather5 = waprx.getWeather();

        assertEquals(weatherConditions.get(0), weather1);
        assertEquals(weatherConditions.get(1), weather2);
        assertEquals(weatherConditions.get(2), weather3);
        assertEquals(weatherConditions.get(3), weather4);
        assertEquals(weatherConditions.get(4), weather5);
    }
}
