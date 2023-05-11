package WeatherAlarms;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import main.WeatherAlarms;
import java.util.List;

import org.junit.Test;

public class WeatherAlarmsTest {

    @Test
    public void testGetWeather() throws IllegalAccessException, NoSuchFieldException {
        // given
        List<Integer> weatherConditions = Arrays.asList(0, 1, 2, 3);
        Iterator<Integer> iterator = weatherConditions.iterator();
        WeatherAlarms weatherAlarms = new WeatherAlarms();
        Field iteratorField = WeatherAlarms.class.getDeclaredField("iterator");
        iteratorField.setAccessible(true);
        iteratorField.set(weatherAlarms, iterator);

        int result1 = weatherAlarms.new WeatherAlarmWorkerI().getWeather(null);
        int result2 = weatherAlarms.new WeatherAlarmWorkerI().getWeather(null);
        int result3 = weatherAlarms.new WeatherAlarmWorkerI().getWeather(null);
        int result4 = weatherAlarms.new WeatherAlarmWorkerI().getWeather(null);

        assertEquals(0, result1);
        assertEquals(1, result2);
        assertEquals(2, result3);
        assertEquals(3, result4);
    }

    @Test
    public void testReadWeatherConditions() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        WeatherAlarms weatherAlarms = new WeatherAlarms();
        List<Integer> expected = Arrays.asList(0, 1, 2, 3);
        Method readWeatherConditionsMethod = WeatherAlarms.class.getDeclaredMethod("readWeatherConditions");
        readWeatherConditionsMethod.setAccessible(true);
        List<Integer> actual = (List<Integer>) readWeatherConditionsMethod.invoke(weatherAlarms);
        assertEquals(expected, actual);
    }
}
