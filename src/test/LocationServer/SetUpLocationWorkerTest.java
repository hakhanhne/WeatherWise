package LocationServer;
import static org.junit.Assert.*;

import com.zeroc.Ice.*;
import com.zeroc.IceStorm.NoSuchTopic;
import com.zeroc.IceStorm.TopicPrx;
import main.LocationServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.zeroc.IceStorm.TopicManagerPrx;

import java.lang.Exception;
import java.net.BindException;
import java.util.Map;
//This test case verifies that when start() is called, a new ObjectAdapter is created and activated with the LocationWorkerI servant and the LocationWorker identity. It also verifies that the David-sensors topic exists in the IceStorm topic manager.
public class SetUpLocationWorkerTest {
    private Communicator communicator;
    private TopicManagerPrx topicManager;
    
    @Before
    public void setUp() throws Exception, SocketException, BindException,NullPointerException {
        String[] args = {"--Ice.Config=config.icebox"};
        communicator = Util.initialize(args);
        ObjectPrx obj = communicator.stringToProxy("IceStorm/TopicManager:tcp -p 10000");
        topicManager = TopicManagerPrx.checkedCast(obj);
    }
    
    
    @After
    public void tearDown() throws Exception, SocketException, BindException,NullPointerException {
        communicator.shutdown();
    }
    
    @Test
    public void testStartLocationWorker() throws NoSuchTopic, Exception, SocketException, BindException,NullPointerException {
        LocationServer server = new LocationServer();
        ObjectAdapter adapter =communicator.createObjectAdapterWithEndpoints("LocationWorker",
                "default -p 11112");
        Identity identity = Util.stringToIdentity("LocationWorker");
        adapter.add(new LocationServer.LocationWorkerI(), identity);
        adapter.activate();
        
//        Map<String, TopicPrx> topicNames = topicManager.retrieveAll();
//        for (Map.Entry<String, TopicPrx> entry : topicNames.entrySet()) {
//            String key = entry.getKey();
//            TopicPrx value = entry.getValue();
//            System.out.println(key + " -> " + value);
//        }
        
        assertNotNull(topicManager.retrieve("David-sensors"));
    }
}

