package Integration;

import main.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

public class CC_UI {
    @Before
    public void setUp() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        EnviroAPPUI.username = "Jack";
        EnviroAPPUI.communicator = com.zeroc.Ice.Util.initialize();
        EnviroAPPUI.topicName = EnviroAPPUI.username + "-alerts";
        Method setupSubcriber = EnviroAPPUI.class.getDeclaredMethod("setupSubcriber");
        setupSubcriber.setAccessible(true);
        setupSubcriber.invoke(null);
        Method iniContextCoordinatorWorker = EnviroAPPUI.class.getDeclaredMethod("iniContextCoordinatorWorker", String[].class);
        iniContextCoordinatorWorker.setAccessible(true);
        iniContextCoordinatorWorker.invoke(null, (Object) null);
    }

    @Test
    public void testAdd() {
        try {
            EnviroAPPUI.ContextCoordinatorWorker.addUser(EnviroAPPUI.username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSearchInfo() {
        String info = EnviroAPPUI.ContextCoordinatorWorker.searchInfo("Crescent Mall");
        assertEquals("Crescent Mall Shopping Centre is located 10km South of the Ho Chi Minh City central business district(CBD) and includes Banana Republic, Baskin Robins, CGV Cinema, Bobapop and over 130 specialty stores.", info);
    }

    @Test
    public void testDelete() {
        try {
            EnviroAPPUI.ContextCoordinatorWorker.deleteUser(EnviroAPPUI.username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
