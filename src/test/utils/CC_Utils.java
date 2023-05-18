package utils;

import main.ContextCoordinator;
import support.LocationDetails;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class CC_Utils {
    public static void initCC_Communicator() throws NoSuchFieldException, IllegalAccessException {
        Field communicator = ContextCoordinator.class.getDeclaredField("communicator");
        communicator.setAccessible(true);
        communicator.set(null, com.zeroc.Ice.Util.initialize());
    }

    public static void initCC_LocationWorker() throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        initWorker("locationWorker", "iniLocationMapper");
    }

    public static void initCC_WeatherAlarmWorker() throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        initWorker("weatherAlarmWorker", "iniWeatherAlarmWorker");
    }

    public static void initCC_PreferenceWorker() throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        initWorker("preferenceWorker", "iniPreferenceWorker");
    }


    public static void initWorker(String worker, String iniMethod) throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Field f = ContextCoordinator.class.getDeclaredField(worker);
        f.setAccessible(true);
        Method m = ContextCoordinator.class.getDeclaredMethod(iniMethod);
        m.setAccessible(true);
        m.invoke(null);
    }


    public static void initCC_All() throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException  {
        initCC_Communicator();
        initCC_LocationWorker();
        initCC_PreferenceWorker();
        initCC_WeatherAlarmWorker();
        runMethod("runWeatherAlarm");
    }
    public static Field accessField(String field) throws NoSuchFieldException {
        Field f = ContextCoordinator.class.getDeclaredField(field);
        f.setAccessible(true);
        return f;
    }

    public static Method accessMethod(String method) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = ContextCoordinator.class.getDeclaredMethod(method);
        m.setAccessible(true);
        return m;
    }

    public static void runMethod(String method) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = accessMethod(method);
        m.invoke(null);
    }

    public static void initCC_CityInfo() throws NoSuchMethodException, IllegalAccessException, NoSuchFieldException, InvocationTargetException {
        Method readCityInfo = ContextCoordinator.class.getDeclaredMethod("readCityInfo");
        readCityInfo.setAccessible(true);
        List<LocationDetails> cityInfo = (List<LocationDetails>) readCityInfo.invoke(null);
        Field cityInfoField = ContextCoordinator.class.getDeclaredField("cityInfo");
        cityInfoField.setAccessible(true);
        cityInfoField.set(null, cityInfo);
    }

}
