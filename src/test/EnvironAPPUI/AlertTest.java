package EnvironAPPUI;

import com.zeroc.Ice.Current;
import helper.Alert;
import main.EnviroAPPUI;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class AlertTest {

    private Class<?> AlerterI;
    private Method alertMethod;
    private Object object;

    @Parameterized.Parameter
    public String alertType;
    @Parameterized.Parameter(1)
    public int alertValue;
    @Parameterized.Parameter(2)
    public String[] suggestion;
    @Parameterized.Parameter(3)
    public String expected;

    @Parameterized.Parameters
    public static Collection<Object[]> data(){
        return Arrays.asList(new Object[][]{
                {"Weather", 0, new String[] {"pool" , "restaurants" , "cinema"},
                        "*******************************************************************\n"
                                +"Context-aware UV Smart Application Main Menu\n"+
                                "Warning, extreme weather is detected, the current weather event is  null\n"+
                                "Suggestion - please go to: \n"+
                                "pool , restaurants , cinema , \n"+
                                "Please select an option\n"+
                                "1. Search for information on a specific item of interest\n"+
                                "2. Search for items of interest in current location\n"+
                                "E. Exit"},
                {"Weather", 1, new String[] {"pool" , "restaurants" , "cinema"},
                        "*******************************************************************\n"
                                +"Context-aware UV Smart Application Main Menu\n"+
                                "Warning, extreme weather is detected, the current weather event is  strong wind\n"+
                                "Suggestion - please go to: \n"+
                                "pool , restaurants , cinema , \n"+
                                "Please select an option\n"+
                                "1. Search for information on a specific item of interest\n"+
                                "2. Search for items of interest in current location\n"+
                                "E. Exit"},
                {"Weather", 2, new String[] {"pool", "restaurants" , "cinema"},
                        "*******************************************************************\n"
                                +"Context-aware UV Smart Application Main Menu\n"+
                                "Warning, extreme weather is detected, the current weather event is  hail storm\n"+
                                "Suggestion - please go to: \n"+
                                "pool , restaurants , cinema , \n"+
                                "Please select an option\n"+
                                "1. Search for information on a specific item of interest\n"+
                                "2. Search for items of interest in current location\n"+
                                "E. Exit"},
                {"Weather", 3, new String[] {"pool" , "restaurants" , "cinema" },
                        "*******************************************************************\n"
                                +"Context-aware UV Smart Application Main Menu\n"+
                                "Warning, extreme weather is detected, the current weather event is  heavy rain\n"+
                                "Suggestion - please go to: \n"+
                                "pool , restaurants , cinema , \n"+
                                "Please select an option\n"+
                                "1. Search for information on a specific item of interest\n"+
                                "2. Search for items of interest in current location\n"+
                                "E. Exit"},
                {"Temperature", -25, new String[] {"market" , "restaurants" , "shops" },
                        "*******************************************************************\n"
                                +"Context-aware UV Smart Application Main Menu\n"+
                                "Warning,Temperature is now: -25\n"+
                                "Suggestion - please go to: \n"+
                                "market , restaurants , shops , \n"+
                                "Please select an option\n"+
                                "1. Search for information on a specific item of interest\n"+
                                "2. Search for items of interest in current location\n"+
                                "E. Exit"},
                {"Temperature", 10, new String[] {"pool" , "restaurants" , "cinema" },
                        "*******************************************************************\n"
                                +"Context-aware UV Smart Application Main Menu\n"+
                                "Warning,Temperature is now: 10\n"+
                                "Suggestion - please go to: \n"+
                                "pool , restaurants , cinema , \n"+
                                "Please select an option\n"+
                                "1. Search for information on a specific item of interest\n"+
                                "2. Search for items of interest in current location\n"+
                                "E. Exit"},
                {"Temperature", 25, new String[] {"market","restaurants","shops"},
                        "*******************************************************************\n"
                                +"Context-aware UV Smart Application Main Menu\n"+
                                "Warning,Temperature is now: 25\n"+
                                "Suggestion - please go to: \n"+
                                "market , restaurants , shops , \n"+
                                "Please select an option\n"+
                                "1. Search for information on a specific item of interest\n"+
                                "2. Search for items of interest in current location\n"+
                                "E. Exit"},
                {"Temperature", 40, new String[] {"market" , "restaurants" , "shops" },
                        "*******************************************************************\n"
                                +"Context-aware UV Smart Application Main Menu\n"+
                                "Warning,Temperature is now: 40\n"+
                                "Suggestion - please go to: \n"+
                                "market , restaurants , shops , \n"+
                                "Please select an option\n"+
                                "1. Search for information on a specific item of interest\n"+
                                "2. Search for items of interest in current location\n"+
                                "E. Exit"},
                {"APO", 40, new String[] {"market" , "restaurants" , "shops" },
                        "*******************************************************************\n"
                                +"Context-aware UV Smart Application Main Menu\n"+
                                "Warning, significant air pollution level detected, the current AQI is 40\n"+
                                "Suggestion - please go to: \n"+
                                "market , restaurants , shops , \n"+
                                "Please select an option\n"+
                                "1. Search for information on a specific item of interest\n"+
                                "2. Search for items of interest in current location\n"+
                                "E. Exit"},
                {"APO", 65, new String[] {"market" , "restaurants" , "shops"},
                        "*******************************************************************\n"
                                +"Context-aware UV Smart Application Main Menu\n"+
                                "Warning, significant air pollution level detected, the current AQI is 65\n"+
                                "Suggestion - please go to: \n"+
                                "market , restaurants , shops , \n"+
                                "Please select an option\n"+
                                "1. Search for information on a specific item of interest\n"+
                                "2. Search for items of interest in current location\n"+
                                "E. Exit"},
                {"APO", 90, new String[] {"market", "restaurants", "shops"},
                        "*******************************************************************\n"
                                +"Context-aware UV Smart Application Main Menu\n"+
                                "Warning, significant air pollution level detected, the current AQI is 90\n"+
                                "Suggestion - please go to: \n"+
                                "market , restaurants , shops , \n"+
                                "Please select an option\n"+
                                "1. Search for information on a specific item of interest\n"+
                                "2. Search for items of interest in current location\n"+
                                "E. Exit"},
                {"APO", 200, new String[]{"market" , "restaurants" , "shops"},
                        "*******************************************************************\n"
                                +"Context-aware UV Smart Application Main Menu\n"+
                                "Warning, significant air pollution level detected, the current AQI is 200\n"+
                                "Suggestion - please go to: \n"+
                                "market , restaurants , shops , \n"+
                                "Please select an option\n"+
                                "1. Search for information on a specific item of interest\n"+
                                "2. Search for items of interest in current location\n"+
                                "E. Exit"},

        });
    }

    @Before
    public void setUp() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        AlerterI = EnviroAPPUI.class.getDeclaredClasses()[0];
        Constructor<?> constructor = AlerterI.getDeclaredConstructor();
        constructor.setAccessible(true);
        object = constructor.newInstance();
        alertMethod = AlerterI.getDeclaredMethod("alert", Alert.class, Current.class);
        alertMethod.setAccessible(true);
    }

    @Test
    public void testAlert() throws InvocationTargetException, IllegalAccessException {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Alert alert = new Alert(alertType, alertValue, suggestion);
        alertMethod.invoke(object, alert, new Current());

        assertEquals(expected, outContent.toString().trim().replace("\r", ""));
    }
}
