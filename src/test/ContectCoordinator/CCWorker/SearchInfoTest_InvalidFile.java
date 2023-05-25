
package ContectCoordinator.CCWorker;

import com.zeroc.Ice.Current;
import helper.ContextCoordinatorWorker;
import main.ContextCoordinator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import support.LocationDetails;
import utils.CC_Utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class SearchInfoTest_InvalidFile {
    static ContextCoordinatorWorker ccW = new ContextCoordinator.ContextCoordinatorWorkerI();

    @Test
    public void testSearchInfo_InvalidFormatFile() throws IllegalArgumentException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        CC_Utils.initCC_CityInfo();
        String inputName = "Vivo City Shopping Centre";
        String expected = "Vivo City Shopping Centre is a major regional shopping centre in the southern suburb of Ho Chi Minh City, Vietnam. It is the second largest shopping centre in the southern suburbs of Ho Chi Minh City, by gross area, and contains the only H&M store in that region.";
        String actualInfo = ccW.searchInfo(inputName, null);
        assertEquals(expected, actualInfo);
    }



    @Test
    public void testSearchInfo_EmptyFile() throws IllegalArgumentException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        CC_Utils.initCC_CityInfo();
        String inputName = "Vivo City Shopping Centre";
        String actualInfo = ccW.searchInfo(inputName, null);
        assertEquals(null, actualInfo);
    }







    @Test
    public void testSearchInfo_NoFile() throws IllegalArgumentException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        CC_Utils.initCC_CityInfo();
        String inputName = "Vivo City Shopping Centre";
        String actualInfo = ccW.searchInfo(inputName, null);
        assertEquals(null, actualInfo);
    }
}




