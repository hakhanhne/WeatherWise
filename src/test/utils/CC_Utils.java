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
        Field locationWorkerPrx = ContextCoordinator.class.getDeclaredField("locationWorker");
        locationWorkerPrx.setAccessible(true);
        Method iniLocationMapper = ContextCoordinator.class.getDeclaredMethod("iniLocationMapper");
        iniLocationMapper.setAccessible(true);
        iniLocationMapper.invoke(null);
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
